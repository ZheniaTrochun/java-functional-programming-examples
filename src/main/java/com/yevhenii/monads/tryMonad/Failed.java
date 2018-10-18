package com.yevhenii.monads.tryMonad;


import java.util.Objects;

public class Failed<T> implements Try<T> {

    private final Throwable exception;

    private Failed(Throwable exception) {
        this.exception = exception;
    }

    public static <T> Try<T> of(Throwable exception) {
        return new Failed<>(exception);
    }

    @Override
    public T get() {
        throw new TryException(exception);
    }

    @Override
    public Throwable reason() {
        return exception;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isFailed() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Failed<?> failed = (Failed<?>) o;
        return Objects.equals(exception, failed.exception);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exception);
    }

    @Override
    public String toString() {
        return "Failed{" +
                "exception=" + exception +
                '}';
    }
}
