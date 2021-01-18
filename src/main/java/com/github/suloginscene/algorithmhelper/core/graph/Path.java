package com.github.suloginscene.algorithmhelper.core.graph;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter @EqualsAndHashCode(of = {"from", "to"})
@RequiredArgsConstructor
public class Path<V> implements Comparable<Path<V>> {

    private final V from;
    private final V to;

    private final int weight;


    @Override
    public int compareTo(Path<V> another) {
        return Integer.compare(weight, another.weight);
    }

}
