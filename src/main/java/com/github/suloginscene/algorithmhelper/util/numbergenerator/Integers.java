package com.github.suloginscene.algorithmhelper.util.numbergenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import static lombok.AccessLevel.PROTECTED;


@Getter @NoArgsConstructor(access = PROTECTED)
public abstract class Integers implements Iterable<Integer> {

    protected final List<Integer> integers = new ArrayList<>();

    protected Integer first;
    protected Integer mid;
    protected Integer last;


    public int[] toIntArray() {
        int size = integers.size();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = integers.get(i);
        }
        return array;
    }

    public List<Integer> subList(int start, int end) {
        return integers.subList(start, end);
    }

    @Override
    public String toString() {
        return integers.toString();
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

}
