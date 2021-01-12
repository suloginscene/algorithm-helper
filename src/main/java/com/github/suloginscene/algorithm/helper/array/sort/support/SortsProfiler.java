package com.github.suloginscene.algorithm.helper.array.sort.support;

import com.github.suloginscene.algorithm.helper.array.sort.SortFactory;
import com.github.suloginscene.algorithm.helper.array.sort.Sorts;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;


@Slf4j
public final class SortsProfiler extends Sorts {

    public SortsProfiler(SortFactory sortFactory) {
        super(sortFactory);
    }


    public void sort(int[] array) {
        long start = System.currentTimeMillis();

        super.sort(array);

        long end = System.currentTimeMillis();
        long time = end - start;
        int n = array.length;

        StringBuilder sb = new StringBuilder();

        sb.append("\n> ").append(getStrategy()).append(" Sort in ").append(time).append(" ms.");
        sb.append(" (n: ").append(n).append(")\n");

        if (n <= 16) {
            sb.append(Arrays.toString(array));
        } else {
            int[] preview = new int[16];
            System.arraycopy(array, 0, preview, 0, 16);
            sb.append(Arrays.toString(preview)).append(" ...");
        }

        log.info(sb.toString());
    }

}
