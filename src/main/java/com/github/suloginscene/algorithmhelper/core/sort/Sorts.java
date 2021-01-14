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


public class Sorts {

    private final Map<Strategy, Algorithm> map = new HashMap<>();

    @Getter @Setter
    private Strategy strategy;


    public Sorts(AlgorithmContainer algorithmContainer) {
        map.put(BUBBLE, algorithmContainer.bubble());
        map.put(SELECTION, algorithmContainer.selection());
        map.put(INSERTION, algorithmContainer.insertion());
        map.put(MERGE, algorithmContainer.merge());
        map.put(HEAP, algorithmContainer.heap());
        map.put(QUICK, algorithmContainer.quick());
        strategy = QUICK;
    }


    public void sort(int[] array) {
        Algorithm algorithm = map.get(strategy);
        algorithm.execute(array);
    }

}
