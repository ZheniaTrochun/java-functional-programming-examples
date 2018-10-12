package com.yevhenii.monads.tryMonad;

@FunctionalInterface
public interface TrySupplier <T> {

    T get() throws Exception;
}
