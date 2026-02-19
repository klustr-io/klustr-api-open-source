package io.klustr.permissions;

public class PairwiseIdentifier {
    public static boolean matches(String subject_id) {
        return subject_id.matches("^[a-fA-F0-9]{64}$");
    }
}
