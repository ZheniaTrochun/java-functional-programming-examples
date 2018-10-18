package com.yevhenii.monads.either;

import java.util.Optional;
import java.util.function.Function;

public interface Either <L, R> {

    boolean isLeft();
    boolean isRight();

    L left();
    R right();


    default <T> Either<L, T> flatMap(Function<R, Either<L, T>> func) {
        return isLeft() ?
                Left.of(left()) :
                func.apply(right());
    }

    default <T> Either<L, T> map(Function<R, T> func) {
        return isLeft() ?
                Left.of(left()) :
                Right.of(func.apply(right()));
    }

    default Either<R, L> swap() {
        return isLeft() ?
                Right.of(left()) :
                Left.of(right());
    }

    default Optional<R> toOptional() {
        return isLeft() ?
                Optional.empty() :
                Optional.of(right());
    }

    default Either<L, R> recover(Function<L, R> func) {
        return isLeft() ?
                Right.of(func.apply(left())) :
                this;
    }

    default Either<L, R> recoverWith(Function<L, Either<L, R>> func) {
        return isLeft() ?
                func.apply(left()) :
                this;
    }

    default Optional<L> leftOpt() {
        return swap().toOptional();
    }

    default Optional<R> rightOpt() {
        return toOptional();
    }

    default <T> T fold(Function<L, T> leftMapper, Function<R, T> rightMapper) {
        return isLeft() ?
                leftMapper.apply(left()) :
                rightMapper.apply(right());
    }
}
