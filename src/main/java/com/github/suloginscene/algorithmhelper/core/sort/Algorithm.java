package com.github.suloginscene.algorithmhelper.core.sort;


/**
 * Core interface to implement.
 * ex) class Quick implements Algorithm {...}
 * User can sort by this type, but using Sorter is recommended.
 */
public interface Algorithm {

    /**
     * Core method to implement for sorting.
     */
    void execute(int[] array);

}
