package io.klustr.integrations.kong.models;

import java.util.List;

public class KongGenericPagedResult<T> {
    public List<T> data;
    public String next;
}
