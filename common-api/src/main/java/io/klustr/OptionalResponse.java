package io.klustr;

import java.util.Optional;

public class OptionalResponse<T> {
    public boolean exists = false;
    public T obj;

    public OptionalResponse() {

    }

    public OptionalResponse(T obj) {
        this.obj = obj;
        this.exists = true;
    }

    public static <T> OptionalResponse<T> of(Optional<T> arg) {
        if (arg.isEmpty()) {
            return new OptionalResponse<>();
        } else {
            return new OptionalResponse<>(arg.get());
        }
    }

    public static <T> OptionalResponse<T> of(T obj) {
        return new OptionalResponse<>();
    }

    public static <T> OptionalResponse<T> empty() {
        return new OptionalResponse<>();
    }
}
