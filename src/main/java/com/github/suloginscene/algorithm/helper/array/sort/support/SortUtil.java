package com.github.suloginscene.algorithm.helper.array.sort.support;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class SortUtil {

    public static void swap(int[] array, int i, int j) {
        if (i == j) return;

        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }

}
