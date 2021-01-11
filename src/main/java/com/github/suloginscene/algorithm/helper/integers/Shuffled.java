package com.github.suloginscene.algorithm.helper.integers;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


@Getter
class Shuffled extends Integers {

    private final Integer first;
    private final Integer mid;
    private final Integer last;


    protected Shuffled(int n) {
        for (int i = 1; i <= n; i++) {
            integers.add(i);
        }

        int count = 0;
        while ((n > 2) && !shuffled(integers)) {
            hinduShuffle(integers);
            riffleShuffle(integers);
            hinduShuffle(integers);
            count++;
            if (count > 10) throw new RuntimeException("Infinite shuffle.(Try different n)");
        }

        first = integers.get(0);
        mid = integers.get(n / 2);
        last = integers.get(n - 1);
    }


    private void hinduShuffle(List<Integer> integers) {
        int size = integers.size();
        List<Integer> firstHalf = new ArrayList<>(integers.subList(0, size / 3));

        integers.removeAll(firstHalf);
        integers.addAll(firstHalf);
    }

    private void riffleShuffle(List<Integer> integers) {
        int size = integers.size();
        Queue<Integer> firstHalf = new LinkedList<>(integers.subList(0, size / 2));
        Queue<Integer> secondHalf = new LinkedList<>(integers.subList(size / 2, size));
        integers.clear();

        boolean isOdd = false;
        while (integers.size() < size) {
            Integer integer = (isOdd = !isOdd) ? firstHalf.poll() : secondHalf.poll();
            if (integer != null) {
                integers.add(integer);
            }
        }
    }


    private boolean shuffled(List<Integer> integers) {
        return hasProperHead(integers) && isScattered(integers);
    }

    private boolean hasProperHead(List<Integer> integers) {
        int size = integers.size();

        int lowThreshold = size / 4;
        int highThreshold = (size * 3) / 4;

        Integer head = integers.get(0);
        return (head > lowThreshold) && (head < highThreshold);
    }

    private boolean isScattered(List<Integer> integers) {
        int size = integers.size();

        for (int i = 0; i < size; i++) {
            Integer standard = integers.get(i);
            int sequence = 1;

            for (int j = i + 1; j < size; j++) {
                Integer current = integers.get(j);
                if (current != standard + 1) break;

                standard = current;
                sequence++;
                if (sequence > 3) return false;
            }

        }
        return true;
    }

}
