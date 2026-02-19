package io.klustr.documents.utils;

import io.klustr.utils.U;

public class CollectionNamingConvention {

    public static String getDatabase(String org_id, String project_id) {
        String sanitizedDbName = "_" + org_id.replace(".", "").replace("_", "").replace("-", "");
        return sanitizedDbName + "_" + U.md5(project_id).toLowerCase().substring(0, 3);
    }

    public static boolean isValidName(String n) {
        if (n == null) {
            return false;
        }
        String pattern = "^(?![_-])(?!.*[_-]$)(?!.*__)(?!.*--)[a-z0-9_-]+$";
        return n.matches(pattern);
    }
}
