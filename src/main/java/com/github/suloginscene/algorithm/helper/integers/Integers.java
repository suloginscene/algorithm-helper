package com.github.suloginscene.algorithm.helper.integers;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;


@Slf4j
public abstract class Integers implements Iterable<Integer> {

    protected final List<Integer> integers = new ArrayList<>();


    protected Integers() {
    }


    public abstract Integer getFirst();

    public abstract Integer getMid();

    public abstract Integer getLast();


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
