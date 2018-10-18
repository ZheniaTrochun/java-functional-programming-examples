package com.yevhenii.monads.either;


import java.util.Objects;

public class Right<L, R> implements Either<L, R> {

    private final R value;

    Right(R value) {
        this.value = value;
    }

    public static <L, R> Either<L, R> of(R value) {
        return new Right<>(value);
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    public L left() {
        throw new IllegalStateException();
    }

    @Override
    public R right() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Right<?, ?> right = (Right<?, ?>) o;
        return Objects.equals(value, right.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Right{" +
                "value=" + value +
                '}';
    }
}
