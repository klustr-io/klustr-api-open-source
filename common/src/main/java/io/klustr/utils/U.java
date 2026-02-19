package io.klustr.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.CharMatcher;
import com.google.gson.*;
import io.klustr.SemanticVersion;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.DigestUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mimics underscore.js to make Java a little more tastey. See <a
 * href="http://underscorejs.org/#">underscore.js</a>
 *
 * @author Terrance A. Snyder
 */
public class U {

    private static final Random r = new SecureRandom();

    /**
     * Takes the specified datetime and converts to seconds since epoch to downcast the requirement of using
     * `long` and instead use `int` with some loss of precision (milliseconds).
     *
     * @param dt The datetime to convert to seconds since epoch.
     * @return The seconds since epoch for the specified date.
     */
    public static int toEpochSeconds(DateTime dt) {
        return (int) (dt.toDate().getTime() / 1000L);
    }

    public static String toAscii(String input) {
        if (input == null) return null;

        return Normalizer.normalize(input, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "")           // remove all combining marks
                .replaceAll("[^\\p{ASCII}]", "")    // remove non-ASCII chars
                .replaceAll("[^A-Za-z0-9_\\s]", "") // optionally drop punctuation except underscore
                .trim()
                .replaceAll("\\s+", " ");
    }

    /**
     * Endode the value as a MD5 digest hex string.
     * @param value
     * @return
     */
    public static String md5(String value) {
        return DigestUtils.md5DigestAsHex(value.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Encodes the passed String as UTF-8 using an algorithm that's compatible
     * with JavaScript's <code>encodeURIComponent</code> function. Returns
     * <code>null</code> if the String is <code>null</code>.
     *
     * @param s The String to be encoded
     * @return the encoded String
     */
    public static String encodeURIComponent(String s) {
        String result = null;

        try {
            // keep line breaks
            String txt = s.replace("\n", "<br/>");
            // remove all other control characters
            txt = CharMatcher.javaIsoControl().removeFrom(txt);
            // encode back in new lines
            txt = txt.replace("<br/>", "\n");

            result = URLEncoder.encode(txt, "UTF-8").replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!").replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(").replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

    public static String decodeURIComponent(String s) {
        try {
            return java.net.URLDecoder.decode(s, "UTF-8");
        } catch (Exception ex) {
            return null;
        }
    }

    public static String waitForDnsOrExit(String hostname, int waitMilliseconds) {
        // Backup original DNS cache settings
        String ttlBefore = java.security.Security.getProperty("networkaddress.cache.ttl");
        String negBefore = java.security.Security.getProperty("networkaddress.cache.negative.ttl");

        long deadline = System.currentTimeMillis() + waitMilliseconds;

        try {
            // Disable JVM DNS caching
            java.security.Security.setProperty("networkaddress.cache.ttl", "0");
            java.security.Security.setProperty("networkaddress.cache.negative.ttl", "0");

            String ip = null;
            while (System.currentTimeMillis() < deadline) {
                try {
                    InetAddress inet = InetAddress.getByName(hostname);
                    ip = inet.getHostAddress();
                    if (ip != null) return ip;
                } catch (UnknownHostException e) {
                    // DNS not yet propagated — retry
                }

                try {
                    Thread.sleep(1000); // wait 1 second between polls
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            return null; // timed out
        } finally {
            // Restore original cache settings
            if (ttlBefore != null)
                java.security.Security.setProperty("networkaddress.cache.ttl", ttlBefore);
            else
                java.security.Security.setProperty("networkaddress.cache.ttl", "-1");
            if (negBefore != null)
                java.security.Security.setProperty("networkaddress.cache.negative.ttl", negBefore);
            else
                java.security.Security.setProperty("networkaddress.cache.negative.ttl", "10");
        }
    }

    public static String getResourceAsString(String file, Object container) {
        try {
            return IOUtils.toString(container.getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            try {
                return IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream(file));  // fallback
            } catch (Exception ex) {
                // ignore
            }
            throw new RuntimeException("Unable to access file " + file, e);
        }
    }

    public static String toJson(final Object obj) {
        return Json.toJson(obj);
    }

    public static <T> T fromJson(final String json, final Class<T> type) {
        return Json.parse(json, type);
    }

    public static String toJsonPrettyFormat(final Object obj) {
        return Json.toJsonPrettyFormat(obj);
    }

    public static InputStream getResourceAsStream(String file, Object container) {
        try {
            return container.getClass().getClassLoader().getResourceAsStream(file);
        } catch (Exception e) {
            throw new RuntimeException("Unable to access file " + file, e);
        }
    }

    public static int random(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return r.nextInt((max - min) + 1) + min;
    }


    public static SemanticVersion version(String version) {
        return new SemanticVersion(version);
    }
}
