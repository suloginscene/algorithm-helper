package com.github.suloginscene.algorithm.helper.array.sort;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static com.github.suloginscene.algorithm.helper.array.sort.Strategy.BUBBLE;
import static com.github.suloginscene.algorithm.helper.array.sort.Strategy.HEAP;
import static com.github.suloginscene.algorithm.helper.array.sort.Strategy.INSERTION;
import static com.github.suloginscene.algorithm.helper.array.sort.Strategy.MERGE;
import static com.github.suloginscene.algorithm.helper.array.sort.Strategy.QUICK;
import static com.github.suloginscene.algorithm.helper.array.sort.Strategy.SELECTION;


public class Sorts {

    private final Map<Strategy, Sort> map = new HashMap<>();

    @Getter @Setter
    private Strategy strategy;


    public Sorts(SortFactory sortFactory) {
        map.put(BUBBLE, sortFactory.bubble());
        map.put(SELECTION, sortFactory.selection());
        map.put(INSERTION, sortFactory.insertion());
        map.put(MERGE, sortFactory.merge());
        map.put(HEAP, sortFactory.heap());
        map.put(QUICK, sortFactory.quick());
        strategy = QUICK;
    }


    public void sort(int[] array) {
        Sort sort = map.get(strategy);
        sort.execute(array);
    }

}
