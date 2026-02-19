package io.klustr.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * A Simple Utility class for parsing "math" like strings relating to Dates.
 *
 * <p>
 * The basic syntax support addition, subtraction and rounding at various levels of granularity (or
 * "units"). Commands can be chained together and are parsed from left to right. '+' and '-' denote
 * addition and subtraction, while '/' denotes "round". Round requires only a unit, while
 * addition/subtraction require an integer value and a unit. Command strings must not include white
 * space, but the "No-Op" command (empty string) is allowed....
 * </p>
 *
 * <pre>
 *   /HOUR
 *      ... Round to the start of the current hour
 *   /DAY
 *      ... Round to the start of the current day
 *   +2YEARS
 *      ... Exactly two years in the future from now
 *   -1DAY
 *      ... Exactly 1 day prior to now
 *   /DAY+6MONTHS+3DAYS
 *      ... 6 months and 3 days in the future from the start of
 *          the current day
 *   +6MONTHS+3DAYS/DAY
 *      ... 6 months and 3 days in the future from now, rounded
 *          down to nearest day
 * </pre>
 *
 * <p>
 * (Multiple aliases exist for the various units of time (ie: <code>MINUTE</code> and
 * <code>MINUTES</code>; <code>MILLI</code>, <code>MILLIS</code>, <code>MILLISECOND</code>, and
 * <code>MILLISECONDS</code>.) The complete list can be found by inspecting the keySet of
 * {@link #CALENDAR_UNITS})
 * </p>
 *
 * <p>
 * All commands are relative to a "now" which is fixed in an instance of DateMathParser such that
 * <code>p.parseMath("+0MILLISECOND").equals(p.parseMath("+0MILLISECOND"))</code> no matter how many
 * wall clock milliseconds elapse between the two distinct calls to parse (Assuming no other thread
 * calls "<code>setNow</code>" in the interim). The default value of 'now' is the time at the moment
 * the <code>DateMathParser</code> instance is constructed, unless overridden by the
 * {@link CommonParams#NOW NOW} request param.
 * </p>
 *
 * <p>
 * All commands are also affected to the rules of a specified {@link TimeZone} (including the
 * start/end of DST if any) which determine when each arbitrary day starts. This not only impacts
 * rounding/adding of DAYs, but also cascades to rounding of HOUR, MIN, MONTH, YEAR as well. The
 * default <code>TimeZone</code> used is <code>UTC</code> unless overridden by the
 * {@link CommonParams#TZ TZ} request param.
 * </p>
 *
 * @see SolrRequestInfo#getClientTimeZone
 * @see SolrRequestInfo#getNOW
 */
public class DateMathParser {

    private static final String NOW = "NOW";

    private static final String Z = "Z";
    private static Pattern splitter = Pattern.compile("\\b|(?<=\\d)(?=\\D)");
    private DateTime _dt;

    /**
     * Default constructor that assumes UTC should be used for rounding unless otherwise specified
     * in the SolrRequestInfo
     *
     * @see SolrRequestInfo#getClientTimeZone
     * @see #DEFAULT_MATH_LOCALE
     */
    private DateMathParser() {

    }

    public static String toIsoFormat(DateTime dt) {
        return dt.toString(ISODateTimeFormat.dateTimeNoMillis());
    }

    public static DateTime fromIsoFormat(String dt) {
        return DateTime.parse(dt);
    }

    public static DateTime parse(String value) throws IllegalArgumentException {
        return parse(null, value);
    }

    public static boolean tryParse(String value) {
        try {
            parse(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static DateTime parse(DateTime now, String val) throws IllegalArgumentException {
        String math = null;
        final DateMathParser p = new DateMathParser();

        if (null != now) {
            p.setNow(now);
        }

        if (val.startsWith(NOW)) {
            math = val.substring(NOW.length());
        } else {
            final int zz = val.indexOf(Z);
            if (0 < zz) {
                math = val.substring(zz + 1);
                String x = val.substring(0, zz + 1);
                DateTime dt = DateTime.parse(x);
                p.setNow(dt);

            } else {
                throw new IllegalArgumentException("Invalid Date String:'" + val + '\'');
            }
        }

        if (null == math || math.equals("")) {
            return p.getNow();
        }

        try {
            return p.parseMath(math);
        } catch (DateParseException e) {
            throw new IllegalArgumentException("Invalid Date Math String:'" + val + '\'', e);
        }
    }

    /**
     * Modifies the specified Calendar by "adding" the specified value of units
     *
     * @throws IllegalArgumentException if unit isn't recognized.
     * @see #CALENDAR_UNITS
     */
    public static DateTime add(DateTime c, int val, String unit) {
        if (unit.equalsIgnoreCase("YEAR") || unit.equalsIgnoreCase("YEARS")) {
            return c.plusYears(val);
        }
        if (unit.equalsIgnoreCase("MONTH") || unit.equalsIgnoreCase("MONTHS")) {
            return c.plusMonths(val);
        }
        if (unit.equalsIgnoreCase("DAY") || unit.equalsIgnoreCase("DAYS")) {
            return c.plusDays(val);
        }
        if (unit.equalsIgnoreCase("HOUR") || unit.equalsIgnoreCase("HOURS")) {
            return c.plusHours(val);
        }
        if (unit.equalsIgnoreCase("MINUTE") || unit.equalsIgnoreCase("MINUTES")) {
            return c.plusMinutes(val);
        }
        if (unit.equalsIgnoreCase("SECOND") || unit.equalsIgnoreCase("SECONDS")) {
            return c.plusSeconds(val);
        }
        if (unit.equalsIgnoreCase("WEEK") || unit.equalsIgnoreCase("WEEKS")) {
            return c.plusWeeks(val);
        }
        throw new IllegalArgumentException("Adding Unit not recognized: " + unit);
    }

    /**
     * Modifies the specified Calendar by "rounding" down to the specified unit
     *
     * @throws IllegalArgumentException if unit isn't recognized.
     * @see #CALENDAR_UNITS
     */
    public static DateTime round(DateTime c, String unit) {
        if (unit.equalsIgnoreCase("YEAR") || unit.equalsIgnoreCase("YEARS")) {
            return c.withTimeAtStartOfDay().withDayOfYear(1).withMonthOfYear(1).withTimeAtStartOfDay();
        }
        if (unit.equalsIgnoreCase("MONTH") || unit.equalsIgnoreCase("MONTHS")) {
            return c.withTimeAtStartOfDay().withDayOfMonth(1).withTimeAtStartOfDay();
        }
        if (unit.equalsIgnoreCase("DAY") || unit.equalsIgnoreCase("DAYS")) {
            return c.withTimeAtStartOfDay();
        }
        if (unit.equalsIgnoreCase("HOUR") || unit.equalsIgnoreCase("HOURS")) {
            return c.withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        }
        if (unit.equalsIgnoreCase("MINUTE") || unit.equalsIgnoreCase("MINUTES")) {
            return c.withSecondOfMinute(0).withMillisOfSecond(0);
        }
        if (unit.equalsIgnoreCase("SECOND") || unit.equalsIgnoreCase("SECONDS")) {
            return c.withMillisOfSecond(0);
        }
        if (unit.equalsIgnoreCase("WEEK") || unit.equalsIgnoreCase("WEEKS")) {
            return c.withDayOfWeek(1).withTimeAtStartOfDay();
        }
        if (unit.equalsIgnoreCase("SUNDAY")) {
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.SUNDAY);
        }
        if (unit.equalsIgnoreCase("SATURDAY")) {
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.SATURDAY);
        }
        if (unit.equalsIgnoreCase("FRIDAY")) {
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.FRIDAY);
        }
        if (unit.equalsIgnoreCase("THURSDAY")) {
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.THURSDAY);
        }
        if (unit.equalsIgnoreCase("WEDNESDAY")) {
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.WEDNESDAY);
        }
        if (unit.equalsIgnoreCase("TUESDAY")) {
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.TUESDAY);
        }
        if (unit.equalsIgnoreCase("MONDAY")) {
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.MONDAY);
        }
        if (unit.equalsIgnoreCase("NEXT_SUNDAY")) {
            if (c.withTimeAtStartOfDay().getDayOfWeek() < DateTimeConstants.MONDAY) {
                return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.MONDAY);
            }
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.SUNDAY).plusDays(7);
        }
        if (unit.equalsIgnoreCase("NEXT_SATURDAY")) {
            if (c.withTimeAtStartOfDay().getDayOfWeek() < DateTimeConstants.SATURDAY) {
                return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.SATURDAY);
            }
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.SATURDAY).plusDays(7);
        }
        if (unit.equalsIgnoreCase("NEXT_FRIDAY")) {
            if (c.withTimeAtStartOfDay().getDayOfWeek() < DateTimeConstants.FRIDAY) {
                return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.FRIDAY);
            }
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.FRIDAY).plusDays(7);
        }
        if (unit.equalsIgnoreCase("NEXT_THURSDAY")) {
            if (c.withTimeAtStartOfDay().getDayOfWeek() < DateTimeConstants.THURSDAY) {
                return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.THURSDAY);
            }
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.THURSDAY).plusDays(7);
        }
        if (unit.equalsIgnoreCase("NEXT_WEDNESDAY")) {
            if (c.withTimeAtStartOfDay().getDayOfWeek() < DateTimeConstants.WEDNESDAY) {
                return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.WEDNESDAY);
            }
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.WEDNESDAY).plusDays(7);
        }
        if (unit.equalsIgnoreCase("NEXT_TUESDAY")) {
            if (c.withTimeAtStartOfDay().getDayOfWeek() < DateTimeConstants.TUESDAY) {
                return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.TUESDAY);
            }
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.TUESDAY).plusDays(7);
        }
        if (unit.equalsIgnoreCase("NEXT_MONDAY")) {
            if (c.withTimeAtStartOfDay().getDayOfWeek() < DateTimeConstants.MONDAY) {
                return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.MONDAY);
            }
            return c.withTimeAtStartOfDay().withDayOfWeek(DateTimeConstants.MONDAY).plusDays(7);
        }
        throw new IllegalArgumentException("Rounding Unit not recognized: " + unit);

    }

    /**
     * Returns a cloned of this instance's concept of "now".
     * <p>
     * If setNow was never called (or if null was specified) then this method first defines 'now' as
     * the value dictated by the SolrRequestInfo if it exists -- otherwise it uses a new Date
     * instance at the moment getNow() is first called.
     *
     * @see #setNow
     * @see SolrRequestInfo#getNOW
     */
    private DateTime getNow() {
        if (_dt == null) {
            return new DateTime(DateTimeZone.UTC);
        }
        return new DateTime(_dt, DateTimeZone.UTC);
    }

    /**
     * Defines this instance's concept of "now".
     *
     * @see #getNow
     */
    private DateMathParser setNow(DateTime n) {
        _dt = n;
        return this;
    }

    /**
     * Parses a string of commands relative "now" are returns the resulting Date.
     *
     * @throws ParseException positions in ParseExceptions are token positions, not character positions.
     */
    private DateTime parseMath(String math) throws DateParseException {

        DateTime dt = getNow();

        /* check for No-Op */
        if (0 == math.length()) {
            return dt;
        }

        String[] ops = splitter.split(math);
        int pos = 0;
        while (pos < ops.length) {

            if (1 != ops[pos].length()) {
                throw new DateParseException("Multi character command found: \"" + ops[pos] + "\"", pos);
            }
            char command = ops[pos++].charAt(0);

            switch (command) {
                case '/':
                    if (ops.length < pos + 1) {
                        throw new DateParseException("Need a unit after command: \"" + command + "\"", pos);
                    }
                    try {
                        dt = round(dt, ops[pos++]);
                    } catch (IllegalArgumentException e) {
                        throw new DateParseException("Unit not recognized: \"" + ops[pos - 1] + "\"", pos - 1);
                    }
                    break;
                case '+': /* fall through */
                case '-':
                    if (ops.length < pos + 2) {
                        throw new DateParseException("Need a value and unit for command: \"" + command + "\"", pos);
                    }
                    int val = 0;
                    try {
                        val = Integer.valueOf(ops[pos++]);
                    } catch (NumberFormatException e) {
                        throw new DateParseException("Not a Number: \"" + ops[pos - 1] + "\"", pos - 1);
                    }
                    if ('-' == command) {
                        val = 0 - val;
                    }
                    try {
                        String unit = ops[pos++];
                        dt = add(dt, val, unit);
                    } catch (IllegalArgumentException e) {
                        throw new DateParseException("Unit not recognized: \"" + ops[pos - 1] + "\"", pos - 1);
                    }
                    break;
                default:
                    throw new DateParseException("Unrecognized command: \"" + command + "\"", pos - 1);
            }
        }

        return dt;
    }

    public static final class DateParseException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public DateParseException(String message, int postition) {
            super(message + " @ " + postition);
        }
    }

}