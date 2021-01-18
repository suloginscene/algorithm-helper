package com.github.suloginscene.algorithmhelper.util;

import com.github.suloginscene.algorithmhelper.core.binarysearchtree.BinarySearchTree;
import com.github.suloginscene.algorithmhelper.core.binarysearchtree.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.lang.System.currentTimeMillis;


@Slf4j
public class BSTUtil {

    /**
     * When save many data in bst, like on test, log progress.
     */
    public static <K extends Comparable<K>, V> void saveDataLoggingProgress(BinarySearchTree<K, V> bst, List<? extends Node<K, V>> data, int interval) {
        int i = 0;
        long start = currentTimeMillis();
        for (Node<K, V> node : data) {
            bst.save(node.getKey(), node.getValue());
            if ((i % interval) == 0) {
                log.debug("data {} saved.", i);
            }
            i++;
        }
        long end = currentTimeMillis();

        log.info("\n> " + "BST Initialized in " + (end - start) + " ms.\n");
    }

}
