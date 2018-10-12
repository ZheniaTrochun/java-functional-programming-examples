package com.yevhenii.traverse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class FutureTraverse {

    static <T> CompletableFuture<List<T>> allOfTraverse(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> completedAll =
                CompletableFuture.allOf(futures.toArray((CompletableFuture<T>[]) (new Object[futures.size()])));

        return completedAll.thenApplyAsync(v ->
           futures.stream()
                   .map(future -> {
                       try {
                           return future.get();
                       } catch (InterruptedException | ExecutionException e) {
                           e.printStackTrace();
                           throw new IllegalStateException("Something gone wrong in future");
                       }
                   })
                   .collect(Collectors.toList())
        );
    }


    static <T> CompletableFuture<List<T>> traverse(List<CompletableFuture<T>> futures) {

        return futures.stream()
                .collect(
                        () -> CompletableFuture.completedFuture(new LinkedList<>()),
                        (acc, future) -> acc.thenComposeAsync(list -> future.thenApplyAsync(list::add)),
                        (acc1, acc2) -> acc1.thenComposeAsync(list1 -> acc2.thenApplyAsync(list1::addAll))
                );
    }

    static <T> CompletableFuture<List<T>> loopTraverse(List<CompletableFuture<T>> futures) {
        CompletableFuture<List<T>> futureList = CompletableFuture.completedFuture(new ArrayList<>(futures.size()));

        for (CompletableFuture<T> future : futures) {
            futureList.thenComposeAsync(acc -> future.thenApply(acc::add));
        }

        return futureList;
    }
}
