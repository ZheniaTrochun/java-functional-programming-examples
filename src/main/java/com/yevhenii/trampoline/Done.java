package com.yevhenii.trampoline;

import java.util.function.Supplier;

public class Done<T> implements Trampoline<T> {

    private final T result;

    public Done(T result) {
        this.result = result;
    }

    @Override
    public T getResult() {
        return result;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public Supplier<Trampoline<T>> call() {
        throw new IllegalStateException("Computation is finished");
    }

    public static <T> Done<T> of(T result) {
        return new Done<>(result);
    }
}
