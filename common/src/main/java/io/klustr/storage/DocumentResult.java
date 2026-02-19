package io.klustr.storage;

import com.google.common.collect.Lists;

import java.util.List;

public class DocumentResult<T> {
    public Long count;
    public Integer limit;
    public Integer skip;
    public List<T> docs = Lists.newArrayList();
}

