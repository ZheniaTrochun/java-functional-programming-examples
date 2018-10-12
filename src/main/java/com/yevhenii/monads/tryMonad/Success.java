package com.yevhenii.monads.tryMonad;

import java.util.Optional;
import java.util.function.Function;

public class Success <T> implements Try<T> {

    private final T state;

    protected Success(T state) {
        this.state = state;
    }

    @Override
    public <R> Try<R> flatMap(Function<T, Try<R>> mapper) {
        return mapper.apply(state);
    }

    @Override
    public <R> Try<R> map(TryFunction<T, R> mapper) {
        return Try.ofFailable(() -> mapper.apply(state));
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.ofNullable(state);
    }
}
