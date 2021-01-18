package com.github.suloginscene.algorithmhelper.core.graph;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * Data for weighted edge.
 * It is comparable by weight, can be found by from and to.
 */
@Getter @EqualsAndHashCode(of = {"from", "to"})
@RequiredArgsConstructor
public class Edge<V> implements Comparable<Edge<V>> {

    private final V from;
    private final V to;

    private final int weight;


    @Override
    public int compareTo(Edge<V> another) {
        return Integer.compare(weight, another.weight);
    }

}
