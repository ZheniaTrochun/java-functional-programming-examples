package com.yevhenii.trampoline.examples;

import com.yevhenii.trampoline.Done;
import com.yevhenii.trampoline.More;
import com.yevhenii.trampoline.Trampoline;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComposedTrampolineExample {

    public static void main(String[] args) {
        List<Integer> list = IntStream.range(0, 100000)
                .boxed()
                .collect(Collectors.toList());

        try {
            even(list);
        } catch (StackOverflowError e) {
            System.out.println("Ooops!");
        }

//        Trampoline.execute(() -> evenT(list));
        System.out.println(evenT(list).execute());
        System.out.println("I'm fine");
    }

    private static <T> boolean even(List<T> list) {
        if (list.isEmpty())
            return true;
        else
            return odd(list.subList(1, list.size()));
    }


    private static <T> boolean odd(List<T> list) {
        if (list.isEmpty())
            return false;
        else
            return even(list.subList(1, list.size()));
    }

    private static <T> Trampoline<Boolean> evenT(List<T> list) {
        if (list.isEmpty())
            return Done.of(true);
        else
            return More.of(() -> oddT(list.subList(1, list.size())));
    }


    private static <T> Trampoline<Boolean> oddT(List<T> list) {
        if (list.isEmpty())
            return Done.of(false);
        else
            return More.of(() -> evenT(list.subList(1, list.size())));
    }
}
