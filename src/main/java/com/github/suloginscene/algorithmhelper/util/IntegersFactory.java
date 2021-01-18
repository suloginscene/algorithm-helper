package com.github.suloginscene.algorithmhelper.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import static java.lang.System.currentTimeMillis;
import static lombok.AccessLevel.PRIVATE;


/**
 * Create integers of increasing, stably shuffled, unique random.
 * Stably shuffled integers are same on same n.
 * Unique random integers are randomized, but has no same element.
 */
@Slf4j @NoArgsConstructor(access = PRIVATE)
public class IntegersFactory {

    public static Integers increasingFromOne(int n) {
        return new Increasing(n);
    }

    public static Integers increasingFromOne(int n, boolean logging) {
        Integers integers = increasingFromOne(n);

        if (!logging) return integers;

        logIntegers(integers, n, null);
        return integers;
    }


    public static Integers stablyShuffled(int n) {
        if (n < 3) throw new IllegalArgumentException("Too short to shuffle.(min: 3)");

        return new Shuffled(n);
    }

    public static Integers stablyShuffled(int n, boolean logging) {
        long start = currentTimeMillis();
        Integers integers = stablyShuffled(n);
        long end = currentTimeMillis();

        if (!logging) return integers;

        logIntegers(integers, n, end - start);
        return integers;
    }


    public static Integers uniqueRandom(int n) {
        return new Randomized(n);
    }

    public static Integers uniqueRandom(int n, boolean logging) {
        long start = currentTimeMillis();
        Integers integers = uniqueRandom(n);
        long end = currentTimeMillis();

        if (!logging) return integers;

        logIntegers(integers, n, end - start);
        return integers;
    }


    private static void logIntegers(Integers integers, int n, Long time) {
        StringBuilder sb = new StringBuilder();

        if (time != null) sb.append("\n> ").append("Integers created in ").append(time).append(" ms.");

        sb.append("\n> ");
        if (n <= 16) sb.append(integers);
        else sb.append(integers.subList(0, 16)).append(" ...");
        sb.append("\n");

        log.debug(sb.toString());
    }


    private static class Increasing extends Integers {

        private Increasing(int n) {
            for (int i = 1; i <= n; i++) {
                integers.add(i);
            }
            first = integers.get(0);
            mid = integers.get(n / 2);
            last = integers.get(n - 1);
        }

    }


    private static class Shuffled extends Integers {

        private Shuffled(int n) {
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

    }


    private static class Randomized extends Integers {

        private static final Random random = new Random();

        private Randomized(int n) {
            List<Integer> src = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                src.add(i);
            }

            while (integers.size() != n) {
                int index = random.nextInt(src.size());
                int value = src.get(index);
                integers.add(value);
                src.remove(index);
            }

            first = integers.get(0);
            mid = integers.get(n / 2);
            last = integers.get(n - 1);
        }
    }

}
