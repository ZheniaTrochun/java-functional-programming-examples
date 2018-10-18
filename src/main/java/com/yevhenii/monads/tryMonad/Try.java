package com.yevhenii.monads.tryMonad;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Try <T> {

    T get();
    Throwable reason();

    boolean isSuccess();

    boolean isFailed();


    default T getOrElse(Supplier<T> other) {
        return isSuccess() ?
                get() :
                other.get();
    }

    default <R> Try<R> flatMap(Function<T, Try<R>> mapper) {
        return isSuccess() ?
                mapper.apply(get()) :
                Failed.of(reason());
    }

    default <R> Try<R> map(TryFunction<T, R> mapper) {
        return isSuccess() ?
                Try.ofFailable(() -> mapper.apply(get())) :
                Failed.of(reason());
    }

    default <R> Try<R> pureMap(Function<T, R> mapper) {
        return isSuccess() ?
                Success.of(mapper.apply(get())) :
                Failed.of(reason());
    }

    default Optional<T> toOptional() {
        return isSuccess() ?
                Optional.of(get()) :
                Optional.empty();
    }

    default Try<T> recover(Function<Throwable, T> recovery) {
        return isSuccess() ?
                this :
                Success.of(
                        recovery.apply(reason())
                );
    }

    default Try<T> recoverWith(Function<Throwable, Try<T>> recovery) {
        return isSuccess() ?
                this :
                recovery.apply(reason());
    }

    default Try<T> failWith(Throwable ex) {
        return Failed.of(ex);
    }

    default T fold(Function<Throwable, T> recovery, Function<T, T> mapper) {
        return isSuccess() ?
                mapper.apply(get()) :
                recovery.apply(reason());
    }



    static <T> Try<T> failed(Throwable ex) {
        return Failed.of(ex);
    }

    static <T> Try<T> successful(T value) {
        return Success.of(value);
    }

    static <T> Try<T> ofFailable(TrySupplier<T> func) {
        try {
            T result = func.get();
            return Success.of(result);
        } catch (Throwable e) {
            return Failed.of(e);
        }
    }

    static <T> Try<T> of(Supplier<T> func) {
        return Success.of(func.get());
    }

    static <T> Try<T> of(Throwable ex) {
        return Failed.of(ex);
    }
}
