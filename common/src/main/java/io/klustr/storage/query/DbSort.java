package io.klustr.storage.query;

public class DbSort {

    private final String field;
    private boolean asc = true;

    private DbSort(String field, Boolean asc) {
        this.field = field;
        this.asc = asc;
    }

    public boolean asc() {
        return this.asc;
    }

    public String getField() {
        return this.field;
    }

    public static DbSort asc(String field) {
        return new DbSort(field, true);
    }

    public static DbSort dsc(String field) {
        return new DbSort(field, false);
    }

    public String toSolrQuery() {
        if (this.asc) {
            return this.field + " asc";
        } else {
            return this.field + " dsc";
        }
    }
}
