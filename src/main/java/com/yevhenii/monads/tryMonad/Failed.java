package com.yevhenii.monads.tryMonad;

import java.util.Optional;
import java.util.function.Function;

public class Failed<T> implements Try<T> {

    private Exception exception;

    protected Failed(Exception exception) {
        this.exception = exception;
    }

    protected Failed() {
    }

    @Override
    public <R> Try<R> flatMap(Function<T, Try<R>> mapper) {
        return new Failed<>(exception);
    }

    @Override
    public <R> Try<R> map(TryFunction<T, R> mapper) {
        return new Failed<>(exception);
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
    }

    @Override
    public Try<T> recover(Function<Exception, T> recovery) {
        return Try.of(() -> recovery.apply(exception));
    }

    @Override
    public Try<T> recoverWith(Function<Exception, Try<T>> recovery) {
        return recovery.apply(exception);
    }
}
