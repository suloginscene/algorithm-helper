package com.github.suloginscene.algorithm.helper.integers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.System.currentTimeMillis;


@Getter @Slf4j
class Shuffled extends Integers {

    private final Integer first;
    private final Integer mid;
    private final Integer last;


    protected Shuffled(int n, boolean logShuffle) {
        if (n < 3) throw new IllegalArgumentException("Too short to shuffle.(min: 3)");

        long start = currentTimeMillis();

        for (int i = 1; i <= n; i++) {
            integers.add(i);
        }

        hinduShuffle(integers, 2);
        for (int i = 0; i < 7; i++) {
            riffleShuffle(integers);
        }
        hinduShuffle(integers, 3);

        first = integers.get(0);
        mid = integers.get(n / 2);
        last = integers.get(n - 1);

        long end = currentTimeMillis();
        long time = end - start;

        if (logShuffle) {
            logShuffle(n, time);
        }
    }


    private void hinduShuffle(List<Integer> integers, int n) {
        List<Integer> headers = new ArrayList<>(integers.subList(0, n));

        integers.removeAll(headers);
        integers.addAll(headers);
    }

    private void riffleShuffle(List<Integer> integers) {
        int size = integers.size();
        Queue<Integer> left = new LinkedList<>(integers.subList(0, size / 2));
        Queue<Integer> right = new LinkedList<>(integers.subList(size / 2, size));
        integers.clear();

        boolean odd = false;
        while (!left.isEmpty()) {
            Integer next = (odd = !odd) ? left.poll() : right.poll();
            integers.add(next);
        }
        while (!right.isEmpty()) {
            integers.add(right.poll());
        }
    }


    private void logShuffle(int n, long time) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n> Shuffled in ").append(time).append(" ms.\n");

        if (n <= 16) sb.append(integers);
        else sb.append(integers.subList(0, 16)).append(" ...");

        log.debug(sb.toString());
    }

}
