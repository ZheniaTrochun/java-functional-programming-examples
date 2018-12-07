package com.yevhenii.functional.collections.list;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        FList<Integer> list = FList.of(1, 2, 3);
        list.forEach(System.out::print);
        System.out.println();

        FList<Integer> list0 = list.prepend(0);
        list0.forEach(System.out::print);
        System.out.println();

        FList<Integer> list1 = list.reverse();
        list1.forEach(System.out::print);
        System.out.println();

        FList<Integer> list2 = list.map(i -> i * i);
        list2.forEach(System.out::print);
        System.out.println();

        FList<Integer> list3 = list.filter(i -> i > 1);
        list3.forEach(System.out::print);
        System.out.println();

        FList<Integer> list4 = list.flatMap(i -> Arrays.asList(i, i, i));
        list4.forEach(System.out::print);
        System.out.println();

        System.out.println(list.fold(0, (a, b) -> a + b));
        System.out.println(list.reduce((a, b) -> a + b));
        
        list.forEach(System.out::print);
        System.out.println();
    }
}
