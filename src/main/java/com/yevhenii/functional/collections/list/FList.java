package com.yevhenii.functional.collections.list;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FList<T> {

    default <R extends T> FList<T> prepend(R element) {
        return new FListNode<>(element, this);
    }

    T head();

    Optional<T> headOption();

    FList<T> tail();

    boolean isEmpty();

    int size();

    T fold(T zero, BiFunction<T, T, T> reducer);


    default <R> FList<R> map(Function<T, R> mapper) {
        if (isEmpty()) {
            return nil();
        } else {
            return tail()
                    .map(mapper)
                    .prepend(mapper.apply(head()));
        }
    }

    default FList<T> filter(Function<T, Boolean> filterFunc) {
        if (isEmpty()) {
            return this;
        }

        if (filterFunc.apply(head())) {
            return tail().filter(filterFunc).prepend(head());
        } else {
            return tail().filter(filterFunc);
        }
    }

    default void forEach(Consumer<T> func) {
        if (!isEmpty()) {
            func.accept(head());
            tail().forEach(func);
        }
    }

    default <C extends Collection<T>> C to(C collection) {
        if (!isEmpty()) {
            forEach(collection::add);
        }

        return collection;
    }

    default Optional<T> reduce(BiFunction<T, T, T> reducer) {
        if (isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(tail().fold(head(), reducer));
        }
    }

    default FList<T> reverse() {
        List<T> elements = to(new ArrayList<>(size()));
        Collections.reverse(elements);

        return of(elements);
    }

    default <R> FList<R> flatMap(Function<T, List<R>> mapper) {
        if (isEmpty()) {
            return nil();
        } else {
            List<R> mapRes = mapper.apply(head());
            FList<R> prev = tail().flatMap(mapper);

            for (int i = mapRes.size() - 1; i >= 0; i--) {
                prev = prev.prepend(mapRes.get(i));
            }

            return prev;
        }
    }

    default T get(int i) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        if (i == 0) {
            return head();
        } else {
            return tail().get(i - 1);
        }
    }


    static <T> FNil<T> nil() {
        return new FNil<>();
    }

    static <T> FList<T> empty() {
        return nil();
    }

    static <T> FList<T> of(T... elements) {
        FList<T> initial = empty();

        for (int i = elements.length - 1; i >= 0; i--) {
            initial = initial.prepend(elements[i]);
        }

        return initial;
    }

    static <T> FList<T> of(List<T> elements) {
        FList<T> initial = empty();

        for (int i = elements.size() - 1; i >= 0; i--) {
            initial = initial.prepend(elements.get(i));
        }

        return initial;
    }



    class FListNode<T> implements FList<T> {

        private final T head;
        private final FList<T> tail;
        private int size;

        FListNode(T head, FList<T> tail) {
            this.head = head;
            this.tail = tail;
            size = tail.size() + 1;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public Optional<T> headOption() {
            return Optional.ofNullable(head);
        }

        @Override
        public FList<T> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public T fold(T zero, BiFunction<T, T, T> reducer) {
            return foldLoop(zero, reducer, this);
        }

        private static <T> T foldLoop(T acc, BiFunction<T, T, T> reducer, FList<T> list) {
            if (list.isEmpty()) {
                return acc;
            } else {
                return foldLoop(
                        reducer.apply(acc, list.head()),
                        reducer,
                        list.tail()
                );
            }
        }
    }



    class FNil<T> implements FList<T> {

        FNil() {
        }

        @Override
        public T head() {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public Optional<T> headOption() {
            return Optional.empty();
        }

        @Override
        public FList<T> tail() {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public T fold(T zero, BiFunction<T, T, T> reducer) {
            return zero;
        }
    }
}
