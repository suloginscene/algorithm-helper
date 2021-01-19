package com.github.suloginscene.algorithmhelper.core.sort;


/**
 * Just container for providing Sort to Sorter.
 * User just need to make methods return your Sort.
 */
public interface SortContainer {

    Sort bubble();

    Sort selection();

    Sort insertion();

    Sort merge();

    Sort heap();

    Sort quick();

}
