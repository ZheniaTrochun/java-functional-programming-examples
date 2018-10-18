package com.yevhenii.monads.either.examples;

import com.yevhenii.monads.either.Either;
import com.yevhenii.monads.either.Left;
import com.yevhenii.monads.either.Right;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        List<Either<String, Integer>> eithers = IntStream.range(0, 10).boxed()
                .map(Main::toEither)
                .collect(Collectors.toList());

        eithers.forEach(System.out::println);

        eithers.stream()
                .map(e -> e.map(i -> i / 100.0))
                .forEachOrdered(System.out::println);
    }

    private static Either<String, Integer> toEither(Integer i) {
        return i % 2 == 0 ?
                Left.of("String: " + i) :
                Right.of((int) Math.pow(i, 2));
    }
}
