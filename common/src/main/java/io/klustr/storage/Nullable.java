package io.klustr.storage;

import java.util.Optional;

public class Nullable<T> {

    private T obj;

    public Nullable(Optional<T> obj) {
        this.obj = obj.orElse(null);
    }

    public Nullable() {

    }

    public Optional<T> toOptional() {
        if (this.obj == null) return Optional.empty();
        return Optional.of(this.obj);
    }
}
