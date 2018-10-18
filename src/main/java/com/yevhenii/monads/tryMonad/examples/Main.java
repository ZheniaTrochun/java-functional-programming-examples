package com.yevhenii.monads.tryMonad.examples;

import com.yevhenii.monads.tryMonad.Try;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        List<Try<String>> tries = IntStream.range(0, 10).boxed()
                .map(i ->
                        Try.ofFailable(() -> failable(i))
                )
                .collect(Collectors.toList());

        tries.forEach(System.out::println);

        tries.stream()
                .map(t -> t.map(str -> Integer.valueOf(str.split(" ")[1])))
                .map(t -> t.recover(e -> -1))
                .forEachOrdered(System.out::println);
    }

    private static String failable(int i) {
        if (i % 2 == 0)
            throw new NullPointerException();
        else
            return "Some " + i;
    }
}
