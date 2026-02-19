package io.klustr.utils;
import java.security.SecureRandom;

public final class PasswordGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    // Safe for PostgreSQL, MongoDB, SSH, Kafka configs, env vars, URLs, YAML
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "@#%_-+=!";  // Safe symbols

    private static final String ALL = UPPER + LOWER + DIGITS + SYMBOLS;

    private PasswordGenerator() {} // prevent instantiation

    public static String generate() {
        return generate(16);
    }

    public static String generate(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Minimum password length is 8");
        }

        StringBuilder password = new StringBuilder(length);

        // Guarantee at least one from each category
        password.append(randomChar(UPPER));
        password.append(randomChar(LOWER));
        password.append(randomChar(DIGITS));
        password.append(randomChar(SYMBOLS));

        // Fill remaining slots with full character pool
        for (int i = 4; i < length; i++) {
            password.append(randomChar(ALL));
        }

        // Shuffle to avoid predictable placement
        return shuffle(password.toString());
    }

    private static char randomChar(String chars) {
        return chars.charAt(RANDOM.nextInt(chars.length()));
    }

    private static String shuffle(String input) {
        char[] array = input.toCharArray();
        for (int i = array.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
        return new String(array);
    }

    public static void main(String[] args) {
        System.out.println(generate());
    }
}