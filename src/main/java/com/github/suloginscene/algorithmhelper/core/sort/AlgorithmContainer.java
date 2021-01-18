package com.github.suloginscene.algorithmhelper.core.sort;


/**
 * Just container for providing algorithms to Sorter.
 * User just need to make methods return your Algorithm.
 */
public interface AlgorithmContainer {

    Algorithm bubble();

    Algorithm selection();

    Algorithm insertion();

    Algorithm merge();

    Algorithm heap();

    Algorithm quick();

}
