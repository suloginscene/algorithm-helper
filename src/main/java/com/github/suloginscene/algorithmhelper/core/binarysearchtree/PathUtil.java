package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import java.util.ArrayList;
import java.util.List;


class PathUtil {

    protected static <K extends Comparable<K>, V> List<List<Node<K, V>>> pathsToLeaf(Node<K, V> root) {
        List<List<Node<K, V>>> paths = new ArrayList<>();
        List<Node<K, V>> path = new ArrayList<>();
        recursivePathsToLeaf(root, paths, path);
        return paths;
    }

    private static <K extends Comparable<K>, V> void recursivePathsToLeaf(Node<K, V> node, List<List<Node<K, V>>> paths, List<Node<K, V>> path) {

        path.add(node);

        if (!node.hasChild()) {
            paths.add(new ArrayList<>(path));
            path.remove(node);
            return;
        }

        if (node.hasOnlyChild()) {
            paths.add(new ArrayList<>(path));
        }

        if (node.hasLeft()) {
            recursivePathsToLeaf(node.getLeft(), paths, path);
            path.remove(node.getLeft());
        }

        if (node.hasRight()) {
            recursivePathsToLeaf(node.getRight(), paths, path);
            path.remove(node.getRight());
        }

    }


    protected static <K extends Comparable<K>, V> List<Node<K, V>> pathToNode(Node<K, V> root, K key) {
        ArrayList<Node<K, V>> path = new ArrayList<>();
        if (root == null) return path;

        Node<K, V> finder = root;
        while ((finder != null) && !finder.isIdentifiedBy(key)) {
            path.add(finder);
            finder = finder.isBiggerThan(key) ? finder.getLeft() : finder.getRight();
        }
        path.add(finder);
        return path;
    }

}
