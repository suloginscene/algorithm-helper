package com.github.suloginscene.algorithmhelper.util;

import com.github.suloginscene.algorithmhelper.core.binarysearchtree.BST;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PROTECTED;


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

    public List<BST.Node<Integer, String>> toNodeList() {
        return toNodeList(Integer::toBinaryString);
    }

    public <V> List<BST.Node<Integer, V>> toNodeList(Function<Integer, V> toValueFunction) {
        return integers.stream()
                .map(i -> new BST.Node<>(i, toValueFunction.apply(i)))
                .collect(toList());
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
