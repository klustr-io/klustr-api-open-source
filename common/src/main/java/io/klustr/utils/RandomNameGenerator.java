package io.klustr.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomNameGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public RandomNameGenerator() {

    }

    // Method to generate a random username
    public static String generateUsername(String firstName, String lastName, int maxNumberLength) {
        Random random = new Random();

        // Handle optional first name and last name
        String truncatedFirstName = "";
        String truncatedLastName = "";

        if (firstName != null && !firstName.isEmpty()) {
            firstName = firstName.toLowerCase();
            int firstNameLength = random.nextInt(firstName.length()) + 1; // Ensure at least 1 character
            truncatedFirstName = firstName.substring(0, firstNameLength);
        }

        if (lastName != null && !lastName.isEmpty()) {
            lastName = lastName.toLowerCase();
            int lastNameLength = random.nextInt(lastName.length()) + 1; // Ensure at least 1 character
            truncatedLastName = lastName.substring(0, lastNameLength);
        }

        // Generate a random number of specified length
        int number = random.nextInt((int) Math.pow(10, maxNumberLength)); // Ensure the number fits the length

        // Combine the truncated first name, last name, and number
        return truncatedFirstName + truncatedLastName + number;
    }

    public static String generateAlphaNumericCode(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

    public static String randomHexString(int length) {
        return RandomString.randomHexString(length);
    }

    public Set<String> randomAnimal(int limit) {
        Dictionary animals = new Dictionary(U.getResourceAsStream("animals.csv", this));

        Set<String> results = Sets.newConcurrentHashSet();
        for (var i = 0; i < limit; i++) {
            String animal = animals.terms.get(U.random(0, animals.terms().size() - 1));
            results.add(animal);
        }
        return results;
    }

    private static List<String> _animals;
    private static List<String> _adjectives;

    public List<String> getAnimals() {
        if (_animals != null) return _animals;
        Dictionary animals = new Dictionary(U.getResourceAsStream("animals.csv",  this));
        _animals = animals.terms();
        return _animals;
    }

    public List<String> getAdjectives() {
        if (_adjectives != null) return _adjectives;
        Dictionary adjectives = new Dictionary(U.getResourceAsStream("adjectives.csv", this));
        _adjectives = adjectives.terms();
        return _adjectives;
    }

    public List<String> randomAnimalsAndAdjectives(int limit) {

        List<String> animals = getAnimals();
        List<String> adjectives = getAnimals();

        List<String> results = Lists.newArrayList();
        for (var i = 0; i < limit; i++) {
            String animal = animals.get(U.random(0, animals.size() - 1));
            String adjective = adjectives.get(U.random(0, adjectives.size() - 1));
            String hex = RandomString.randomHexString(6);
            results.add(Joiner.on("-").join(adjective, animal, hex));
        }
        return results;
    }

    private static class RandomString {
        public static String randomHexString(int length) {
            // Define the characters allowed in the random string
            String characters = "0123456789abcdef";
            StringBuilder sb = new StringBuilder();

            // Generate random characters until the desired length is reached
            for (int i = 0; i < length; i++) {
                // Append a random character from the characters string
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }

            // Return the generated random string
            return sb.toString();
        }
    }

    public static class Dictionary {
        private final List<String> terms = Lists.newArrayList();

        public Dictionary(InputStream stream) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while (reader.ready()) {
                    String term = reader.readLine().trim().toLowerCase().replaceAll(" ", "-");
                    if (!terms.contains(term)) {
                        terms.add(term);
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        public List<String> terms() {
            return terms;
        }
    }
}
