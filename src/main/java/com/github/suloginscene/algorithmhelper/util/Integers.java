package com.github.suloginscene.algorithmhelper.util;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;


/**
 * Data wrapping list of integer.
 * It can be converted to integer list, int array, and type list.
 */
@NoArgsConstructor(access = PROTECTED)
public abstract class Integers implements Iterable<Integer> {

    protected final List<Integer> integers = new ArrayList<>();

    @Getter protected Integer first;
    @Getter protected Integer mid;
    @Getter protected Integer last;


    public List<Integer> toIntegerList() {
        return new ArrayList<>(integers);
    }

    public int[] toIntArray() {
        int size = integers.size();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = integers.get(i);
        }
        return array;
    }

    public <T> List<T> toTypeList(Function<Integer, T> toTypeFunction) {
        return integers.stream()
                .map(toTypeFunction)
                .collect(Collectors.toList());
    }


    public List<Integer> subList(int start, int end) {
        return integers.subList(start, end);
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        integers.forEach(action);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return integers.spliterator();
    }

    @Override
    public Iterator<Integer> iterator() {
        return integers.iterator();
    }

    @Override
    public String toString() {
        return integers.toString();
    }

}
