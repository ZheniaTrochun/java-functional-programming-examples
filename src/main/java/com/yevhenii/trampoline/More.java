package com.yevhenii.trampoline;

import java.util.function.Supplier;

public class More<T> implements Trampoline<T> {

    private final Supplier<Trampoline<T>> next;

    public More(Supplier<Trampoline<T>> next) {
        this.next = next;
    }

    @Override
    public Supplier<Trampoline<T>> call() {
        return next;
    }

    public static <T> More<T> of(Supplier<Trampoline<T>> next) {
        return new More<>(next);
    }
}
