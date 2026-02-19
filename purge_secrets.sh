#!/usr/bin/env bash
set -euo pipefail

REPORT="${1:-gitleaks.json}"
IGNORE_RULE_IDS_JSON="${IGNORE_RULE_IDS_JSON:-[]}"   # default: ignore nothing
VERIFY="${VERIFY:-true}"

command -v jq >/dev/null || { echo "jq is required"; exit 1; }
command -v git-filter-repo >/dev/null || { echo "git-filter-repo is required"; exit 1; }
command -v git >/dev/null || { echo "git is required"; exit 1; }

# Safety: refuse to run if working tree is dirty
if [[ -n "$(git status --porcelain)" ]]; then
  echo "Working tree not clean. Commit or stash first."
  exit 1
fi

# Generate report if missing
if [[ ! -f "$REPORT" ]]; then
  command -v gitleaks >/dev/null || { echo "gitleaks required to generate report"; exit 1; }
  echo "Generating gitleaks report..."
  gitleaks detect --report-format json --report-path "$REPORT"
fi

REPL_FILE=".git/filter-repo-replacements.txt"
PATHS_FILE=".git/filter-repo-delete-paths.txt"
VERIFY_REPORT=".git/gitleaks.verify.json"

BACKUP_ROOT=".git/env_local_backup"

: > "$REPL_FILE"
: > "$PATHS_FILE"

# jq helper: ignore configured rule IDs
JQ_FILTER='
  def ignored($rid):
    ($ignore_ids | index($rid)) != null;
  .[]?
  | select(.RuleID? | (ignored(.) | not))
'

echo "=== WARN: .env.local findings (from report) ==="
jq -r '
  .[]?
  | select(.File? | type=="string")
  | select(.File | test("(^|/)\\.env\\.local$"; "i"))
  | (.File + " @ " + (.Commit // "(no-commit)") + " : " + (.RuleID // "(no-rule)"))
' "$REPORT" | LC_ALL=C sort -u || true
echo

# ---- 1) Backup ALL local .env.local files (nested) ----
mkdir -p "$BACKUP_ROOT"

FOUND_LOCAL_ENV="false"
while IFS= read -r -d '' f; do
  FOUND_LOCAL_ENV="true"
  # normalize: remove leading ./ for cleaner restore path
  rel="${f#./}"
  dest="$BACKUP_ROOT/$rel"
  mkdir -p "$(dirname "$dest")"
  cp "$f" "$dest"
done < <(find . -type f -name '.env.local' -print0)

if [[ "$FOUND_LOCAL_ENV" == "true" ]]; then
  echo "Backed up local **/.env.local files into $BACKUP_ROOT/"
else
  echo "No local **/.env.local files found to back up (ok)."
fi
echo

# ---- 2) Build replacement list (replace ALL secrets, except ignored rule IDs) ----
echo "Building replacement list from gitleaks report..."
jq -r --argjson ignore_ids "$IGNORE_RULE_IDS_JSON" "$JQ_FILTER
  | .Secret?
  | select(type==\"string\" and length>0)
" "$REPORT" \
| LC_ALL=C sort -u \
| awk '{ print $0 "==>***REMOVED***" }' \
> "$REPL_FILE"

touch ".git/info/sensitive_strings.txt"
cat ".git/info/sensitive_strings.txt" >> "$REPL_FILE"

COUNT_REPL=$(wc -l < "$REPL_FILE" | tr -d ' ')
echo "Unique secrets to replace in history: $COUNT_REPL"
echo

# ---- 3) Build delete-path list for history purge (NOT string replacement friendly) ----
# Your policy:
# - Purge .env.local FILES from history (but restore local copies afterwards)
# - Also purge private key files and gitleaks artifacts from history
#
# Add exact .env.local paths seen in the gitleaks report
jq -r '
  .[]?
  | .File?
  | select(type=="string")
  | select(test("(^|/)\\.env\\.local$"; "i"))
' "$REPORT" \
| LC_ALL=C sort -u \
>> "$PATHS_FILE"

# Also purge any currently present nested .env.local paths (even if not in report)
while IFS= read -r -d '' f; do
  rel="${f#./}"
  echo "$rel" >> "$PATHS_FILE"
done < <(find . -type f -name '.env.local' -print0)

# Purge gitleaks output files from history (they contain secrets)
echo "gitleaks.json" >> "$PATHS_FILE"

# Purge any file flagged as private-key by gitleaks
jq -r --argjson ignore_ids "$IGNORE_RULE_IDS_JSON" "$JQ_FILTER
  | select(.RuleID? == \"private-key\")
  | .File?
  | select(type==\"string\" and length>0)
" "$REPORT" \
| LC_ALL=C sort -u \
>> "$PATHS_FILE"

# Deduplicate
LC_ALL=C sort -u -o "$PATHS_FILE" "$PATHS_FILE"
COUNT_PATHS=$(wc -l < "$PATHS_FILE" | tr -d ' ')

echo "Paths to delete from history: $COUNT_PATHS"
sed 's/^/ - /' "$PATHS_FILE"
echo

if [[ "$COUNT_REPL" -eq 0 && "$COUNT_PATHS" -eq 0 ]]; then
  echo "Nothing to do."
  exit 0
fi

# ---- 4) Rewrite history (delete paths + replace secret strings) ----
# IMPORTANT: git-filter-repo does NOT support --args <file>.
# Use --paths-from-file to delete many paths at once.
echo "Rewriting git history..."
if [[ "$COUNT_PATHS" -gt 0 && "$COUNT_REPL" -gt 0 ]]; then
  git filter-repo --force \
    --paths-from-file "$PATHS_FILE" --invert-paths \
    --replace-text "$REPL_FILE"
elif [[ "$COUNT_PATHS" -gt 0 ]]; then
  git filter-repo --force \
    --paths-from-file "$PATHS_FILE" --invert-paths
else
  git filter-repo --force \
    --replace-text "$REPL_FILE"
fi
echo

# ---- 5) Ensure .env.local won't be re-added; restore local copies ----
GITIGNORE=".gitignore"
touch "$GITIGNORE"

add_ignore() {
  local pat="$1"
  if ! grep -qxF "$pat" "$GITIGNORE"; then
    echo "$pat" >> "$GITIGNORE"
  fi
}

add_ignore ".env.local"
add_ignore "**/.env.local"
add_ignore "gitleaks.json"
add_ignore "gitleaks*.json"

git add "$GITIGNORE" || true
if ! git diff --cached --quiet; then
  git commit -m "chore: ignore local env and scan artifacts"
fi

# Restore backed up env files
if [[ -d "$BACKUP_ROOT" ]]; then
  RESTORED="false"
  while IFS= read -r -d '' f; do
    RESTORED="true"
    rel="${f#"$BACKUP_ROOT"/}"
    mkdir -p "$(dirname "$rel")"
    cp "$f" "$rel"
  done < <(find "$BACKUP_ROOT" -type f -name '.env.local' -print0)

  if [[ "$RESTORED" == "true" ]]; then
    echo "Restored local **/.env.local files from $BACKUP_ROOT (untracked due to .gitignore)."
  fi
fi

# Defensive: ensure none of the .env.local are tracked after rewrite
TRACKED_ENV=$(git ls-files | grep -iE '(^|/)\.env\.local$' || true)
if [[ -n "$TRACKED_ENV" ]]; then
  echo "Removing tracked .env.local from index (keeping files on disk):"
  echo "$TRACKED_ENV" | sed 's/^/ - /'
  while IFS= read -r p; do
    [[ -z "$p" ]] && continue
    git rm --cached -f "$p"
  done <<< "$TRACKED_ENV"
  git commit -m "chore: untrack .env.local files"
fi

echo
echo "=== Done rewriting history locally ==="
echo "Next (rewrite remote):"
echo "  git push --force --all"
echo "  git push --force --tags"
echo
echo "Everyone else must re-clone or hard-reset to the rewritten history."
echo

# ---- 6) Optional verification scan ----
if [[ "$VERIFY" == "true" ]] && command -v gitleaks >/dev/null; then
  echo "Running verification scan..."
  gitleaks detect --report-format json --report-path "$VERIFY_REPORT" || true
  echo "Verification report saved to $VERIFY_REPORT"
fi
