package io.klustr.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Given a specified string will ALWAYS map to the same value (provided the backing list of inputs is the same).
 */
public class DeterministicUsernameGenerator {

    public static String generate(String someOtherId) {
        byte[] hash = sha256(someOtherId);


        List<String> adj = new RandomNameGenerator().getAdjectives();
        int adjIndex = toPositive(hash[0]) % adj.size();
        List<String> animals = new RandomNameGenerator().getAnimals();
        int animalIndex = toPositive(hash[1]) % animals.size();

        // Combine more bytes for a 2-digit stable code
        int code = (toPositive(hash[2]) + toPositive(hash[3])) % 90 + 10; // 10–99

        return adj.get(adjIndex) + "-" + animals.get(animalIndex) + "-" + code;
    }

    private static int toPositive(byte b) {
        return b & 0xFF;
    }

    private static byte[] sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}