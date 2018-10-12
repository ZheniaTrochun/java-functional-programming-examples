package com.yevhenii.trampoline;

import java.util.function.Supplier;

@FunctionalInterface
public interface Trampoline<T> {

    default T getResult() {
        throw new IllegalStateException("Computation is not finished yet!");
    }

    default boolean isFinished() {
        return false;
    }

    Supplier<Trampoline<T>> call();


    default T execute() {
        Trampoline<T> stage = this;

        while (!stage.isFinished()) {
            stage = stage.call().get();
        }

        return stage.getResult();
    }

    static <T> T execute(Supplier<Trampoline<T>> func) {
        Trampoline<T> stage = func.get();

        while (!stage.isFinished()) {
            stage = stage.call().get();
        }

        return stage.getResult();
    }

    static <T> T execute(Trampoline<T> func) {
        Trampoline<T> stage = func;

        while (!stage.isFinished()) {
            stage = stage.call().get();
        }

        return stage.getResult();
    }

}
