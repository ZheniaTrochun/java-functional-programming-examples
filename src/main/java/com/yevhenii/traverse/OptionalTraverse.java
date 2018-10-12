package com.yevhenii.traverse;

import java.util.*;

public class OptionalTraverse {

    /**
     * traversion of collection with Optionals to Optional collection of results with single loop
     *
     * @param optionals
     * @param <T>
     * @return Optional collection of results
     *          Empty optional if `optionals` collection contains at least one empty optional
     */
    public static <T> Optional<List<T>> loopTraverse(Collection<Optional<T>> optionals) {
        Optional<List<T>> accumulator = Optional.of(new LinkedList<>());

        for (Optional<T> elem : optionals) {
            accumulator.flatMap(acc -> elem.map(acc::add));
        }

        return accumulator;
    }


    /**
     * traversion of collection with Optionals to Optional collection of results with streams
     *
     * @param optionals
     * @param <T>
     * @return Optional collection of results
     *          Empty optional if `optionals` collection contains at least one empty optional
     */
    public static <T> Optional<List<T>> traverse(Collection<Optional<T>> optionals) {

        return optionals.stream()
                .collect(
                        () -> Optional.of(new LinkedList<>()),
                        (acc, elem) -> acc.flatMap(col -> elem.map(col::add)),
                        (acc1, acc2) -> acc1.flatMap(a1 -> acc2.map(a1::addAll))
                );
    }
}
