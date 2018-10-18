package com.yevhenii.monads.tryMonad;

public class TryException extends RuntimeException {
    public TryException(Throwable ex) {
        super("Unsafe .get() call on failed try", ex);
    }
}
