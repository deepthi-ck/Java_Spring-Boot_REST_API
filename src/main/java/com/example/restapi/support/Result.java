package com.example.restapi.support;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class Result<T> {
    private final T value;
    private final String error;
    private final boolean success;

    private Result(T value, String error, boolean success) {
        this.value = value;
        this.error = error;
        this.success = success;
    }

    public static <T> Result<T> ok(T value) {
        return new Result<>(Objects.requireNonNull(value), null, true);
    }

    public static <T> Result<T> fail(String error) {
        return new Result<>(null, error == null ? "error" : error, false);
    }

    public boolean isSuccess() { return success; }

    public Optional<T> value() { return Optional.ofNullable(value); }

    public Optional<String> error() { return Optional.ofNullable(error); }

    public T orElseThrow() {
        if (!success) {
            throw new IllegalStateException(error);
        }
        return value;
    }

    public <R> Result<R> map(Function<T, R> mapper) {
        if (!success) {
            return Result.fail(error);
        }
        return Result.ok(mapper.apply(value));
    }

    @Override
    public String toString() {
        return success ? "Result.ok(" + value + ")" : "Result.fail(" + error + ")";
    }
}
