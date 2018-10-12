package com.yevhenii.monads.tryMonad;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Try <T> {

    <R> Try<R> flatMap(Function<T, Try<R>> mapper);

    <R> Try<R> map(TryFunction<T, R> mapper);

    Optional<T> toOptional();

    Try<T> recover(Function<Exception, T> recovery);

    Try<T> recoverWith(Function<Exception, Try<T>> recovery);

    static <T> Try<T> ofFailable(TrySupplier<T> func) {
        try {
            T result = func.get();
            return new Success<>(result);
        } catch (Exception e) {
            return null;
        }
    }

    static <T> Try<T> of(Supplier<T> func) {
        return new Success<>(func.get());
    }

    static <T> Try<T> empty() {
        return new Failed<>();
    }
}
