package com.github.suloginscene.algorithmhelper.core.sort;


public interface SortFactory {

    Sort bubble();

    Sort selection();

    Sort insertion();

    Sort merge();

    Sort heap();

    Sort quick();

}
