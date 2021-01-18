package com.github.suloginscene.algorithmhelper.core.graph;

import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class Course<V> {

    private final List<V> course;
    private final int distance;

}
