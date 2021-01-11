package com.github.suloginscene.algorithm.helper.integers;


public class IntegersFactory {

    public static Integers increasingFromOne(int n) {
        return new Increasing(n);
    }

    public static Integers stablyShuffled(int n) {
        return new Shuffled(n);
    }

}
