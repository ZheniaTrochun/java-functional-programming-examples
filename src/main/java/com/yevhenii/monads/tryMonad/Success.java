package com.yevhenii.monads.tryMonad;


import java.util.Objects;

public class Success <T> implements Try<T> {

    private final T state;

    private Success(T state) {
        this.state = state;
    }

    public static <T> Try<T> of(T value) {
        return new Success<>(value);
    }


    @Override
    public T get() {
        return state;
    }

    @Override
    public Throwable reason() {
        throw new IllegalStateException();
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isFailed() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Success<?> success = (Success<?>) o;
        return Objects.equals(state, success.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

    @Override
    public String toString() {
        return "Success{" +
                "state=" + state +
                '}';
    }
}
