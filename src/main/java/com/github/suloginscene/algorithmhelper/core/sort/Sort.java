package com.github.suloginscene.algorithmhelper.core.sort;

import java.util.Arrays;


/**
 * Core abstract class to extend.
 * User can sort by this type, but using Sorter is recommended.
 */
public abstract class Sort {

    /**
     * Core method to implement for sorting.
     */
    protected abstract void doExecute(int[] array);

    /**
     * Validate sort.
     */
    public void execute(int[] array) {
        int[] clone = array.clone();

        doExecute(array);

        Arrays.sort(clone);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != clone[i]) {
                throw new IllegalStateException("Sort fail.");
            }
        }
    }

}
