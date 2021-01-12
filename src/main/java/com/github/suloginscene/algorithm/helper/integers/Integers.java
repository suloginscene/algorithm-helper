package com.github.suloginscene.algorithm.helper.integers;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import static lombok.AccessLevel.PROTECTED;


@Slf4j
@NoArgsConstructor(access = PROTECTED)
public abstract class Integers implements Iterable<Integer> {

    protected final List<Integer> integers = new ArrayList<>();


    public abstract Integer getFirst();

    public abstract Integer getMid();

    public abstract Integer getLast();


    public int[] toArray() {
        int size = integers.size();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = integers.get(i);
        }
        return array;
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

    public void print() {
        log.debug("\n> Numbers: " + toString());
    }

}
