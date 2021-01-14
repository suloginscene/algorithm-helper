package com.github.suloginscene.algorithmhelper.core.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static java.lang.System.arraycopy;
import static java.lang.System.currentTimeMillis;


@Slf4j
public class SortsProfiler extends Sorts {

    public SortsProfiler(AlgorithmContainer algorithmContainer) {
        super(algorithmContainer);
    }


    public void sort(int[] array) {
        long start = currentTimeMillis();

        super.sort(array);

        long end = currentTimeMillis();
        long time = end - start;
        int n = array.length;

        StringBuilder sb = new StringBuilder();

        sb.append("\n> ").append(super.getStrategy()).append(" Sort in ").append(time).append(" ms.");
        sb.append(" (n: ").append(n).append(")\n");

        if (n <= 16) {
            sb.append(Arrays.toString(array));
        } else {
            int[] preview = new int[16];
            arraycopy(array, 0, preview, 0, 16);
            sb.append(Arrays.toString(preview)).append(" ...");
        }

        log.info(sb.toString());
    }

}
