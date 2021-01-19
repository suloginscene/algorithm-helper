package com.github.suloginscene.algorithmhelper.core.sort;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static com.github.suloginscene.algorithmhelper.core.sort.Strategy.BUBBLE;
import static com.github.suloginscene.algorithmhelper.core.sort.Strategy.HEAP;
import static com.github.suloginscene.algorithmhelper.core.sort.Strategy.INSERTION;
import static com.github.suloginscene.algorithmhelper.core.sort.Strategy.MERGE;
import static com.github.suloginscene.algorithmhelper.core.sort.Strategy.QUICK;
import static com.github.suloginscene.algorithmhelper.core.sort.Strategy.SELECTION;


/**
 * Algorithm executor holding Strategy.
 * On construction, it store Algorithms by injection.
 * On performing, it execute Algorithm by Strategy.
 * Strategy is just enum, and it can be injected.
 */
public class Sorter {

    private final Map<Strategy, Sort> map = new HashMap<>();

    @Getter @Setter
    private Strategy strategy;


    public Sorter(SortContainer sortContainer) {
        map.put(BUBBLE, sortContainer.bubble());
        map.put(SELECTION, sortContainer.selection());
        map.put(INSERTION, sortContainer.insertion());
        map.put(MERGE, sortContainer.merge());
        map.put(HEAP, sortContainer.heap());
        map.put(QUICK, sortContainer.quick());
        strategy = QUICK;
    }


    public void sort(int[] array) {
        Sort sort = map.get(strategy);
        sort.execute(array);
    }

}
