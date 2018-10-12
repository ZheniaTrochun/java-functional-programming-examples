package com.yevhenii.benchmark;

import com.yevhenii.utils.Pair;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Benchmark {

    public static <T> T profile(Supplier<T> func) {
        long start = System.currentTimeMillis();
        T result = func.get();
        long end = System.currentTimeMillis();

        System.out.println(String.format("Function finished in %d ms", end - start));

        return result;
    }

    public static <T> Pair<Long, T> bench(Supplier<T> func) {
        long start = System.currentTimeMillis();
        T result = func.get();
        long end = System.currentTimeMillis();

        return Pair.of(end - start, result);
    }

    public static long times(int n, Supplier<?> func) {
        long total = 0;

        for (int i = 0; i < n; i++) {
            long start = System.currentTimeMillis();
            func.get();
            long end = System.currentTimeMillis();

            total += end - start;
        }

//        long total = IntStream.range(0, n)
//                .mapToLong(i -> {
//                    long start = System.currentTimeMillis();
//                    func.get();
//                    long end = System.currentTimeMillis();
//
//                    return end - start;
//                })
//                .sum();

        return total / n;
    }
}
