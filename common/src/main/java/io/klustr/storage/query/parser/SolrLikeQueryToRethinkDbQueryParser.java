package io.klustr.storage.query.parser;

import com.rethinkdb.gen.ast.ReqlExpr;
import com.rethinkdb.gen.ast.ReqlFunction1;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rethinkdb.RethinkDB.r;

/**
 * A comprehensive parser for translating field:value syntax into RethinkDB predicate functions (ReqlFunction1).
 * It RETURNS ONLY a predicate (lambda: row -> <boolean-expr>) and NEVER references r.table(...).
 * <p>
 * Supports:
 * - Basic field:value queries
 * - Regex patterns (field:foo.*)
 * - Deep nesting (field.child:value)
 * - Array contains (field.children[].tags[]:cat)
 * - Range queries (field:[* TO NOW], field:[100 TO 200], field:[NOW TO *])
 */
public class SolrLikeQueryToRethinkDbQueryParser {


    // Pattern to match the basic query structure
    private static final Pattern QUERY_PATTERN = Pattern.compile(
            "^([a-zA-Z_][a-zA-Z0-9_\\.\\[\\]]*):(.+)$"
    );

    // Pattern to detect regex (contains .* or other regex metacharacters)
    private static final Pattern REGEX_PATTERN = Pattern.compile(
            ".*[.*+?^${}()|\\\\].*"
    );

    // Pattern to detect range queries [start TO end]
    private static final Pattern RANGE_PATTERN = Pattern.compile(
            "^\\[\\s*(.+?)\\s+TO\\s+(.+?)\\s*\\]$", Pattern.CASE_INSENSITIVE
    );

    // Pattern to detect date strings (ISO format or date math expressions)
    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?Z?|NOW(/[A-Z]+)?([+-]\\d+[A-Z]+)?)$",
            Pattern.CASE_INSENSITIVE
    );


    // -----------------------------
    // Parsed query model
    // -----------------------------

    private static class ParsedQuery {
        private final String fieldPath;
        private final String value;
        private final boolean isRegex;
        private final boolean isRange;
        private final RangeQuery rangeQuery;
        private final List<PathSegment> pathSegments;

        public ParsedQuery(String fieldPath, String value, boolean isRegex, boolean isRange,
                           RangeQuery rangeQuery, List<PathSegment> pathSegments) {
            this.fieldPath = fieldPath;
            this.value = value;
            this.isRegex = isRegex;
            this.isRange = isRange;
            this.rangeQuery = rangeQuery;
            this.pathSegments = pathSegments;
        }

        public String getFieldPath() {
            return fieldPath;
        }

        public String getValue() {
            return value;
        }

        public boolean isRegex() {
            return isRegex;
        }

        public boolean isRange() {
            return isRange;
        }

        public RangeQuery getRangeQuery() {
            return rangeQuery;
        }

        public List<PathSegment> getPathSegments() {
            return pathSegments;
        }
    }

    private static class RangeQuery {
        private final String start;
        private final String end;
        private final boolean startIsWildcard;
        private final boolean endIsWildcard;
        private final boolean isDateRange;

        public RangeQuery(String start, String end, boolean startIsWildcard,
                          boolean endIsWildcard, boolean isDateRange) {
            this.start = start;
            this.end = end;
            this.startIsWildcard = startIsWildcard;
            this.endIsWildcard = endIsWildcard;
            this.isDateRange = isDateRange;
        }

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }

        public boolean isStartWildcard() {
            return startIsWildcard;
        }

        public boolean isEndWildcard() {
            return endIsWildcard;
        }

        public boolean isDateRange() {
            return isDateRange;
        }

        @Override
        public String toString() {
            return String.format("[%s TO %s] (date: %s)",
                    startIsWildcard ? "*" : start,
                    endIsWildcard ? "*" : end,
                    isDateRange);
        }
    }

    private static class PathSegment {
        private final String field;
        private final boolean isArray;
        private final boolean isArrayContains; // [] at the end means contains

        public PathSegment(String field, boolean isArray, boolean isArrayContains) {
            this.field = field;
            this.isArray = isArray;
            this.isArrayContains = isArrayContains;
        }

        public String getField() {
            return field;
        }

        public boolean isArray() {
            return isArray;
        }

        public boolean isArrayContains() {
            return isArrayContains;
        }

        @Override
        public String toString() {
            return field + (isArrayContains ? "[]" : isArray ? "[i]" : "");
        }
    }

    // -----------------------------
    // Public API
    // -----------------------------

    /**
     * Preferred: parse a "field:value" string and return a ReqlFunction1 predicate.
     */
    public static ReqlFunction1 toReSQL(String queryString) {
        ParsedQuery parsed = parse(queryString);
        return buildPredicate(parsed);
    }

    /**
     * Build a ReQL predicate lambda (row -> boolean-expr) from a pre-parsed query.
     */
    private static ReqlFunction1 buildPredicate(ParsedQuery parsedQuery) {
        return row -> buildFilterExpression(row, parsedQuery);
    }

    // -----------------------------
    // Parser
    // -----------------------------

    private static ParsedQuery parse(String query) throws IllegalArgumentException {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }

        Matcher matcher = QUERY_PATTERN.matcher(query.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid query format. Expected 'field:value'");
        }

        String fieldPath = matcher.group(1);
        String value = matcher.group(2);

        Matcher rangeMatcher = RANGE_PATTERN.matcher(value);
        if (rangeMatcher.matches()) {
            String start = rangeMatcher.group(1).trim();
            String end = rangeMatcher.group(2).trim();
            RangeQuery rangeQuery = parseRangeQuery(start, end);
            List<PathSegment> pathSegments = parseFieldPath(fieldPath);
            return new ParsedQuery(fieldPath, value, false, true, rangeQuery, pathSegments);
        }

        boolean isRegex = !value.startsWith("[") && REGEX_PATTERN.matcher(value).matches();
        List<PathSegment> pathSegments = parseFieldPath(fieldPath);

        return new ParsedQuery(fieldPath, value, isRegex, false, null, pathSegments);
    }

    private static RangeQuery parseRangeQuery(String start, String end) {
        boolean startIsWildcard = "*".equals(start);
        boolean endIsWildcard = "*".equals(end);

        boolean isDateRange = (!startIsWildcard && isDateString(start)) ||
                (!endIsWildcard && isDateString(end));

        return new RangeQuery(start, end, startIsWildcard, endIsWildcard, isDateRange);
    }

    private static boolean isDateString(String value) {
        return DATE_PATTERN.matcher(value).matches();
    }

    private static List<PathSegment> parseFieldPath(String fieldPath) {
        List<PathSegment> segments = new ArrayList<>();
        String[] parts = fieldPath.split("\\.");

        for (String part : parts) {
            if (part.isEmpty()) continue;

            boolean isArrayContains = part.endsWith("[]");
            boolean isArray = isArrayContains || part.contains("[");

            String fieldName;
            if (isArrayContains) {
                fieldName = part.substring(0, part.length() - 2);
            } else if (isArray) {
                int idx = part.indexOf('[');
                fieldName = idx >= 0 ? part.substring(0, idx) : part;
            } else {
                fieldName = part;
            }

            segments.add(new PathSegment(fieldName, isArray, isArrayContains));
        }

        return segments;
    }

    // -----------------------------
    // Predicate builders
    // -----------------------------

    private static ReqlExpr buildFilterExpression(ReqlExpr row, ParsedQuery parsedQuery) {
        ReqlExpr reqlExpr = null;
        List<PathSegment> segments = parsedQuery.getPathSegments();

        if (parsedQuery.isRange()) {
            reqlExpr = buildRangePredicate(row, segments, parsedQuery.getRangeQuery());
            return reqlExpr;
        }

        String value = parsedQuery.getValue();
        boolean isRegex = parsedQuery.isRegex();

        if (segments.size() == 1) {
            reqlExpr = buildSimplePredicate(row, segments.get(0), value, isRegex);
            return reqlExpr;
        }

        reqlExpr = buildNestedPredicate(row, segments, value, isRegex, 0);
        return reqlExpr;
    }

    private static ReqlExpr buildSimplePredicate(ReqlExpr row, PathSegment segment, String value, boolean isRegex) {
        ReqlExpr field = row.g(segment.getField());

        if (segment.isArrayContains()) {
            if (isRegex) {
                return field.contains(elem -> elem.match(value));
            } else {
                return field.contains(parseValue(value));
            }
        } else if (isRegex) {
            return field.match(value);
        } else {
            return field.eq(parseValue(value));
        }
    }

    private static ReqlExpr buildRangePredicate(ReqlExpr row, List<PathSegment> segments, RangeQuery rangeQuery) {
        ReqlExpr field = buildFieldAccess(row, segments);

        if (rangeQuery.isStartWildcard() && rangeQuery.isEndWildcard()) {
            return field.ne(r.expr(true));
        } else if (rangeQuery.isStartWildcard()) {
            ReqlExpr endValue = formatRangeValue(rangeQuery.getEnd(), rangeQuery.isDateRange());
            return field.le(endValue);
        } else if (rangeQuery.isEndWildcard()) {
            ReqlExpr startValue = formatRangeValue(rangeQuery.getStart(), rangeQuery.isDateRange());
            return field.ge(startValue);
        } else {
            ReqlExpr startValue = formatRangeValue(rangeQuery.getStart(), rangeQuery.isDateRange());
            ReqlExpr endValue = formatRangeValue(rangeQuery.getEnd(), rangeQuery.isDateRange());
            return field.ge(startValue).and(field.le(endValue));
        }
    }

    private static ReqlExpr buildNestedPredicate(ReqlExpr row, List<PathSegment> segments, String value,
                                                 boolean isRegex, int index) {
        PathSegment currentSegment = segments.get(index);
        boolean isLast = (index == segments.size() - 1);

        ReqlExpr field = row.g(currentSegment.getField());

        if (isLast) {
            if (currentSegment.isArrayContains()) {
                if (isRegex) {
                    return field.contains(elem -> elem.match(value));
                } else {
                    return field.contains(parseValue(value));
                }
            } else if (isRegex) {
                return field.match(value);
            } else {
                return field.eq(parseValue(value));
            }
        } else {
            if (currentSegment.isArrayContains()) {
                final int nextIndex = index + 1;
                return field.contains(elem -> buildNestedPredicate(elem, segments, value, isRegex, nextIndex));
            } else {
                return buildNestedPredicate(field, segments, value, isRegex, index + 1);
            }
        }
    }

    private static ReqlExpr buildFieldAccess(ReqlExpr row, List<PathSegment> segments) {
        ReqlExpr current = row;
        for (PathSegment segment : segments) {
            current = current.g(segment.getField());
        }
        return current;
    }

    // -----------------------------
    // Value formatting
    // -----------------------------

    private static ReqlExpr formatRangeValue(String value, boolean isDateRange) {
        if (isDateRange) {
            if (isDateString(value)) {
                try {
                    DateTime dt = io.klustr.utils.DateMathParser.parse(value);
                    return parseValue(dt.toString());
                } catch (Exception ignore) {
                    // ignore
                }
            }
        }
        return parseValue(value);
    }

    private static ReqlExpr parseValue(String value) {
        if (value == null) {
            return r.expr((Object) null);
        }

        // Trim to avoid whitespace issues
        String trimmed = value.trim();

        // Try integer
        try {
            long asLong = Long.parseLong(trimmed);
            return r.expr(asLong);
        } catch (NumberFormatException ignore) {
        }

        // Try decimal
        try {
            double asDouble = Double.parseDouble(trimmed);
            return r.expr(asDouble);
        } catch (NumberFormatException ignore) {
        }

        // Fallback: treat as string
        return r.expr(trimmed);
    }
}
