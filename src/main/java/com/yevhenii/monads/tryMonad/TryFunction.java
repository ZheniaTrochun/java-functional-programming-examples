package com.yevhenii.monads.tryMonad;

@FunctionalInterface
public interface TryFunction <T, R> {

    R apply(T arg) throws Exception;
}
