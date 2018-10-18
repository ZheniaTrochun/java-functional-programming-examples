package com.yevhenii.monads.either;


import java.util.Objects;

public class Left<L, R> implements Either<L, R> {

    private final L value;

    Left(L value) {
        this.value = value;
    }

    public static <L, R> Either<L, R> of(L value) {
        return new Left<>(value);
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public L left() {
        return value;
    }

    @Override
    public R right() {
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Left<?, ?> left = (Left<?, ?>) o;
        return Objects.equals(value, left.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Left{" +
                "value=" + value +
                '}';
    }
}
