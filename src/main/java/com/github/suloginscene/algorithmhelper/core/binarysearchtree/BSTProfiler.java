package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;


/**
 * Profiler logging time and path.
 */
@Slf4j @RequiredArgsConstructor
public class BSTProfiler<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    private final BinarySearchTree<K, V> binarySearchTree;


    @Override
    protected Node<K, V> getRoot() {
        return binarySearchTree.getRoot();
    }

    @Override
    protected void setRoot(Node<K, V> node) {
        binarySearchTree.setRoot(node);
    }


    @Override
    protected void doSave(@NonNull Node<K, V> node) {
        binarySearchTree.doSave(node);
    }

    @Override
    public Optional<Node<K, V>> findNode(@NonNull K key) {
        long start = currentTimeMillis();
        Optional<Node<K, V>> node = binarySearchTree.findNode(key);
        long end = currentTimeMillis();

        if (node.isEmpty()) {
            log.info("\n> Node " + key + " not found.\n");
            return node;
        }

        long time = end - start;
        String path = getPath(key);

        log.info("\n> Node " + key + " found in " + time + " ms.\n  Path: " + path + "\n");
        return node;
    }

    private String getPath(K key) {
        List<K> path = PathUtil.pathToNode(getRoot(), key).stream()
                .map(Node::getKey)
                .collect(toList());

        StringBuilder sb = new StringBuilder();
        for (K k : path) {
            sb.append(k);
            sb.append(" - ");
        }
        int length = sb.length();
        sb.delete(length - 3, length);
        return sb.toString();
    }

    @Override
    protected void doDelete(@NonNull K key) {
        binarySearchTree.doDelete(key);
    }

    @Override
    public void inOrder(Consumer<Node<K, V>> consumer) {
        binarySearchTree.inOrder(consumer);
    }

}
