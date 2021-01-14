package com.github.suloginscene.algorithmhelper.util;

import com.github.suloginscene.algorithmhelper.core.binarysearchtree.BST;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.lang.System.currentTimeMillis;


@Slf4j
public class BSTUtil {

    public static <K extends Comparable<K>, V> void initWithProfiling(BST<K, V> bst, List<BST.Node<K, V>> data, int interval) {
        int i = 0;
        long start = currentTimeMillis();
        for (BST.Node<K, V> node : data) {
            bst.save(node);
            if ((i % interval) == 0) {
                log.debug("data {} saved.", i);
            }
            i++;
        }
        long end = currentTimeMillis();

        log.info("\n> " + "BST Initialized in " + (end - start) + " ms.\n");
    }

}
