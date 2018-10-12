package com.yevhenii.trampoline.examples;

import com.yevhenii.benchmark.Benchmark;
import com.yevhenii.trampoline.Done;
import com.yevhenii.trampoline.More;
import com.yevhenii.trampoline.Trampoline;

import java.math.BigInteger;
import java.util.function.Supplier;

public class TrampolineFactorialExample {

    public static void main(String[] args) {
        try {
            System.out.println(recursiveFactorial(100000));
        } catch (StackOverflowError e) {
            System.out.println("Ooops!");
        }

//        for GC warm up
        factorial2(100000);
        factorialWithLoop(100000);

//        difference in performance around 8-10% (loop a little faster)
        System.out.println(Benchmark.times(5, () -> factorialWithLoop(100000)));
        System.out.println(Benchmark.times(5, () -> factorial2(100000)));
    }

    private static BigInteger factorialWithLoop(int n) {
        BigInteger res = BigInteger.ONE;

        for (int i = 1; i < n; i++) {
            res = res.multiply(BigInteger.valueOf(i));
        }

        return res;
    }

    private static BigInteger recursiveFactorial(int n) {
        if (n <= 1)
            return BigInteger.ONE;
        else
            return recursiveFactorial(n - 1).multiply(BigInteger.valueOf(n));
    }

    private static BigInteger factorial(int n) {
        return Trampoline.execute(trampolined(BigInteger.ONE, n));
    }

    private static Supplier<Trampoline<BigInteger>> trampolined(BigInteger acc, int n) {
        return () -> {
            if (n <= 1)
                return Done.of(acc);
            else
                return More.of(
                        trampolined(acc.multiply(BigInteger.valueOf(n)), n - 1)
                );
        };
    }

    private static BigInteger factorial2(int n) {
        return trampolined2(BigInteger.ONE, n).execute();
    }

    private static Trampoline<BigInteger> trampolined2(BigInteger acc, int n) {
        if (n <= 1)
            return Done.of(acc);
        else
            return More.of(() ->
                    trampolined2(acc.multiply(BigInteger.valueOf(n)), n - 1)
            );
    }

}
