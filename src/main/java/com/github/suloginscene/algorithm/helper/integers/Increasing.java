package com.github.suloginscene.algorithm.helper.integers;

import lombok.Getter;


@Getter
class Increasing extends Integers {

    private final Integer first;
    private final Integer mid;
    private final Integer last;


    protected Increasing(int n) {
        for (int i = 1; i <= n; i++) {
            integers.add(i);
        }
        first = integers.get(0);
        mid = integers.get(n / 2);
        last = integers.get(n - 1);
    }

}
