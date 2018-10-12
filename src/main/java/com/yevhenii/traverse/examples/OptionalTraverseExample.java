package com.yevhenii.traverse.examples;

import com.yevhenii.traverse.OptionalTraverse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OptionalTraverseExample {
    public static void main(String[] args) {
        List<Integer> initialList = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        List<Optional<Integer>> optionals = initialList.stream().map(Optional::of).collect(Collectors.toList());

        Optional<List<Integer>> optionalList1 = OptionalTraverse.traverse(optionals);
        Optional<List<Integer>> optionalList2 = OptionalTraverse.loopTraverse(optionals);

        List<Integer> traversedList1 = optionalList1.get();
        List<Integer> traversedList2 = optionalList2.get();

        for (int i = 0; i < initialList.size(); i++) {
            if (!initialList.get(i).equals(traversedList1.get(i)) || !initialList.get(i).equals(traversedList2.get(i))) {
                System.out.println("ERROR!");
            }
        }

        List<Optional<Integer>> optionalsWithFailed = new ArrayList<>(optionals);
        optionalsWithFailed.add(Optional.empty());

        Optional<List<Integer>> optionalList3 = OptionalTraverse.traverse(optionalsWithFailed);
        Optional<List<Integer>> optionalList4 = OptionalTraverse.loopTraverse(optionalsWithFailed);

        if (optionalList3.isPresent() || optionalList4.isPresent()) {
            System.out.println("ERROR!");
        }
    }
}
