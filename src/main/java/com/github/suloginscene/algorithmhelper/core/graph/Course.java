package com.github.suloginscene.algorithmhelper.core.graph;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Getter @ToString
@Slf4j @RequiredArgsConstructor
public class Course<V> {

    private final List<V> course;
    private final int distance;

    public void print() {
        V from = course.get(0);
        V to = course.get(course.size() - 1);

        StringBuilder sb = new StringBuilder();
        for (V v : course) {
            sb.append(v).append(" - ");
        }
        int length = sb.length();
        sb.delete(length - 3, length);
        sb.append("\n");

        log.info("\n> Print course\n  {} to {} ({}): {}", from, to, distance, sb.toString());
    }

}
