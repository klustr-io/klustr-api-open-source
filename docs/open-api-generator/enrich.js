// #!/usr/bin/env node
import fs from "fs-extra";
import { glob } from "glob";
import chalk from "chalk";
import ora from "ora";
import OpenAI from "openai";
import dotenv from "dotenv";
import path from "path";

// Load .env.local (prefer local; don't override already-set env vars)
dotenv.config({ path: path.resolve(process.cwd(), ".env.local"), override: false })

const apiKey = process.env.OPEN_API_KEY || process.env.OPENAI_API_KEY;

if (!apiKey) {
  console.error(
    chalk.red(
      "Missing API key. Set OPEN_API_KEY in .env.local (or OPENAI_API_KEY in env)."
    )
  );
  process.exit(1);
}

const client = new OpenAI({ apiKey: apiKey });
const MODEL = "gpt-4o-mini";

/** Find balanced @Operation(...) blocks (multiline-safe). */
function findOperationBlocks(text) {
  const blocks = [];
  const re = /@Operation\s*\(/g;
  let m;
  while ((m = re.exec(text))) {
    let depth = 1;
    let i = m.index + m[0].length;
    while (i < text.length && depth > 0) {
      const ch = text[i++];
      if (ch === "(") depth++;
      else if (ch === ")") depth--;
    }
    if (depth === 0) blocks.push([m.index, i]);
  }
  return blocks;
}

/** Grab nearby JavaDoc above and method signature below the annotation. */
function extractContext(text, start, end) {
  const before = text.slice(Math.max(0, start - 1000), start);
  const after = text.slice(end, Math.min(text.length, end + 1000));
  const javadoc = (before.match(/\/\*\*[\s\S]*?\*\/\s*$/) || [""])[0];
  const signature = (after.match(/(public|protected|private)\s+[^{;]+?\)\s*\{/) || [""])[0];
  return `${javadoc}\n${signature}`.trim();
}

async function withTimeout(promise, ms) {
  const timeout = new Promise((_, reject) =>
    setTimeout(() => reject(new Error("TIMEOUT")), ms)
  );
  return Promise.race([promise, timeout]);
}

function extractClassContext(text) {
  const tag = text.match(/@Tag\s*\(\s*name\s*=\s*"([^"]+)"\s*,\s*description\s*=\s*"([^"]+)"\s*\)/);
  const mapping = text.match(/@RequestMapping\s*\(\s*"([^"]+)"\s*\)/);
  const controller = text.match(/class\s+([A-Za-z0-9_]+)/);
  const javadoc = (text.match(/\/\*\*[\s\S]*?\*\/\s*(?=@(RestController|Component|RequestMapping|Tag))/) || [""])[0];

  const tagName = tag?.[1] || "";
  const tagDesc = tag?.[2] || "";
  const path = mapping?.[1] || "";
  const className = controller?.[1] || "";

  let summary = "";

  if (tagName || tagDesc)
    summary += `${tagName}: ${tagDesc}. `;
  if (path)
    summary += `Request base path is "${path}". `;
  if (className)
    summary += `Controller class: ${className}. `;
  if (javadoc)
    summary += `Class documentation: ${javadoc.replace(/\*+/g, "").trim()}.`;

  return summary.trim();
}

function extractRouteContext(text, methodStart, methodEnd) {
  const segment = text.slice(methodStart - 500, methodEnd + 500);

  const pathMatch = segment.match(/@(GetMapping|PostMapping|PutMapping|DeleteMapping)\s*\(\s*"([^"]+)"/);
  const preAuthMatch = segment.match(/@PreAuthorize\("([^"]+)"\)/);
  const securityMatch = segment.match(/@SecurityRequirement\s*\(\s*name\s*=\s*([^)]+)\)/);

  const path = pathMatch ? pathMatch[2] : "";
  const auth = preAuthMatch ? preAuthMatch[1] : "";
  const sec = securityMatch ? securityMatch[1] : "";

  let inferredRole = "";
  if (path.includes("/admin")) inferredRole = "administrative";
  else if (path.includes("/me")) inferredRole = "personal (current user)";
  else if (path.includes("/public")) inferredRole = "public-facing";
  else inferredRole = "general or internal";

  return `Route path: "${path}" (${inferredRole} endpoint). Required authority: "${auth}". Security requirement: ${sec}.`;
}



/** Ask the LLM for improved operationId/summary/description. */
async function proposeRewrite(opBody, context, fileText, methodStart, methodEnd, spinner) {

  const classContext = extractClassContext(fileText);
  const routeContext = extractRouteContext(fileText, methodStart, methodEnd);


  const system = "You improve Spring @Operation annotations. Return STRICT JSON only.";
  const user = `
Overall controller context:
${classContext}

Local method context:
${context}

And this @Operation body (inside the parentheses only):
${opBody}

Your goal:
- Generate a UNIQUE operationId (camelCase).
- Disambiguate endpoints that differ by path (e.g. /me/, /public/, /admin/).
- operationId should reflect scope (e.g. getMyProfile, getPublicUserProfile).
- operationId should indicate if admin based on the route including /admin and so the operationId should prefix with admin (e.g. adminListProfiles, adminDeleteOrganization)
- opreationId should indicate if it is for the current user when it has /me/ in the route. (e.g listMyLocations, getMyProfile, deleteMyLocation)
- summary should clearly indicate if it's self, public, or admin scope.
- should the route or context be "channel" or "notification" assume that the context is around notifications for sending messages to users (sms, firebase, android, email, etc)
- description should emphasize the visibility level and purpose and be meaningful and helpful for a user and also for exposing and usage by LLM and text embeddings to discover and answer questions
- The overall end goal is to be able to leverage this context with fastmcp to be able to build a MCP tooling around these apis

Return JSON with:
- operationId: camelCase verbObject (e.g., getApiCatalog)
- summary: one concise line (<= 12 words)
- description: 2–4 lines, plain text (no markdown)
Do NOT invent non-existent parameters. Keep semantics faithful to the method.
`;

  let elapsed = 0;
  let originalText = spinner.text;
  spinner.text = originalText + ` `;
  const timer = setInterval(() => {
    elapsed++;
    if (timer == 1) {
        spinner.text = originalText + ` .`;
    }
    if (timer == 2) {
        spinner.text = originalText + ` ..`;
    }
    if (timer == 3) {
        spinner.text = originalText + ` ...`;
    }
    if (timer > 3) {
        spinner.text = originalText + ` ... ${elapsed}s`;
    }
  }, 1000);

  try {

      const res = await withTimeout(client.chat.completions.create({
        model: MODEL,
        store: false,
        temperature: 0.3,
        response_format: { type: "json_object" },
        messages: [
          { role: "system", content: system },
          { role: "user", content: user },
        ]
      }), 10 * 1000);

      const payload = JSON.parse(res.choices[0].message.content);
      return {
        operationId: payload.operationId || "",
        summary: payload.summary || "",
        description: payload.description || "",
      };
  } finally {
    clearInterval(timer);
    spinner.text = originalText;
  }
}

/** Replace existing key or insert it, using Java text blocks for description. */
function replaceOrInsert(opBody, key, val, multiline = false) {
  if (!val) return opBody;
  const tb = new RegExp(`${key}\\s*=\\s*""".[\\s\\S]*?"""`, "s");
  const qt = new RegExp(`${key}\\s*=\\s*".*?"`, "s");
  const repl = multiline ? `${key} = """\n${val}\n"""` : `${key} = "${val}"`;

  if (tb.test(opBody)) return opBody.replace(tb, repl);
  if (qt.test(opBody)) return opBody.replace(qt, repl);

  const trimmed = opBody.trim();
  return trimmed.length ? `${repl}, ${trimmed}` : repl;
}

/** Build a new @Operation(...) block with updated fields, preserving all else. */
function rebuildOperationBlock(blockText, vals) {
  const innerMatch = blockText.match(/@Operation\s*\(([\s\S]*?)\)\s*$/);
  if (!innerMatch) return blockText;
  let body = innerMatch[1].trim();

  body = replaceOrInsert(body, "operationId", vals.operationId);
  body = replaceOrInsert(body, "summary", vals.summary);
  body = replaceOrInsert(body, "description", vals.description, true);

  return `@Operation(\n${body}\n)`;
}

/** Process one Java file. */
async function processFile(filePath, dryRun, spinner) {
  const code = await fs.readFile(filePath, "utf8");
  const spans = findOperationBlocks(code);
  if (!spans.length) {
    return 0;
  }

  let newCode = code;
  // Process from end to start so earlier indices remain valid
  for (const [start, end] of [...spans].reverse()) {
    const block = code.slice(start, end);
    const inner = block.match(/@Operation\s*\(([\s\S]*?)\)\s*$/);
    if (!inner) continue;

    const context = extractContext(code, start, end);
    let suggestion;
    try {
      suggestion = await proposeRewrite(inner[1], context, code, start, end, spinner);
      if (!suggestion.operationId) continue;
    } catch (e) {
      console.error(chalk.red(`LLM error on ${filePath}:`), e.message);
      continue;
    }

    const updated = rebuildOperationBlock(block, suggestion);
    if (!dryRun) {
      newCode = newCode.slice(0, start) + updated + newCode.slice(end);
    }
  }

  if (!dryRun && newCode !== code) {
    await fs.copyFile(filePath, `${filePath}.bak`);
    await fs.writeFile(filePath, newCode, "utf8");
  }
  return spans.length;
}

/** Main */
(async function main() {
  const root = process.argv[2] || "../../";
  const dryRun = process.argv.includes("--dry-run");

  // Get absolute file list with glob (ESM-friendly API)
  const files = await glob("**/*.java", { cwd: root, nodir: true, absolute: true });

  const spinner = ora(`Scanning ${files.length} Java files...`).start();
  let total = 0;

  for (const file of files) {
    spinner.text = `Processing ${file}`;
    total += await processFile(file, dryRun, spinner);
  }

  spinner.stop();
  console.log(
    chalk.green(`✅ Done. Detected ${total} @Operation blocks.`),
    dryRun ? chalk.yellow("Dry run: no files modified.") : chalk.cyan("Backups: *.bak")
  );
})().catch(err => {
  console.error(chalk.red("Fatal error:"), err);
  process.exit(1);
});