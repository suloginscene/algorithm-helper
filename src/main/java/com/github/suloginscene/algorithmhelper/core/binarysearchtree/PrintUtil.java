package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;


@Slf4j
class PrintUtil {

    protected static <K extends Comparable<K>, V> void print(@NonNull Node<K, V> root, Function<Node<K, V>, String> toStringFunction) {
        if (MetricUtil.isHigherThan(root, 7)) {
            log.warn("\n> Too big to print (max height: 7)\n");
            return;
        }

        List<List<Node<K, V>>> levelOrdered = TraversalUtil.levelOrder(root);

        int contentDigit = contentDigit(root, toStringFunction);
        int canvasDigit = canvasDigit(levelOrdered, contentDigit);

        StringBuilder sb = new StringBuilder();
        for (List<Node<K, V>> level : levelOrdered) {
            int marginDigit = marginDigit(level, contentDigit, canvasDigit);
            for (Node<K, V> node : level) {
                String model = wrap(normalize(string(node, toStringFunction), contentDigit), marginDigit);
                sb.append(model);
            }
            sb.append("\n");
        }
        log.info("\n> Print Tree\n" + sb.toString());
    }


    protected static <K extends Comparable<K>, V> void printPaths(@NonNull Node<K, V> root) {
        if (MetricUtil.isHigherThan(root, 5)) {
            log.warn("\n> Too big to print paths (max height: 5)\n");
            return;
        }

        Function<Node<K, V>, String> toKeyFunction = n -> n.getKey().toString();
        int contentDigit = contentDigit(root, toKeyFunction);

        StringBuilder sb = new StringBuilder();
        sb.append("\n> Print paths\n");

        List<List<Node<K, V>>> lists = PathUtil.pathsToLeaf(root);
        for (List<Node<K, V>> list : lists) {
            sb.append("  ");
            for (Node<K, V> node : list) {
                String summary = string(node, toKeyFunction);
                String content = normalize(summary, contentDigit);
                sb.append(content).append(" - ");
            }
            int length = sb.length();
            sb.delete(length - 3, length);
            sb.append("\n");
        }
        log.debug(sb.toString());
    }

    private static <K extends Comparable<K>, V> int contentDigit(Node<K, V> root, Function<Node<K, V>, String> toStringFunction) {
        List<Node<K, V>> nodes = TraversalUtil.inOrder(root);
        int maxLength = 0;
        for (Node<K, V> node : nodes) {
            int length = toStringFunction.apply(node).length();
            maxLength = Math.max(length, maxLength);
        }
        return ((maxLength % 2) == 0) ? maxLength : maxLength + 1;
    }

    private static <K extends Comparable<K>, V> int canvasDigit(List<List<Node<K, V>>> levelOrdered, int contentDigit) {
        int treeHeight = levelOrdered.size();
        int treeWidth = levelOrdered.get(treeHeight - 1).size();
        return treeWidth * (contentDigit + 2);
    }

    private static <K extends Comparable<K>, V> int marginDigit(List<Node<K, V>> level, int contentDigit, int rowDigit) {
        int levelSize = level.size();
        int needfulDigit = levelSize * (contentDigit + 2);
        int needlessDigit = rowDigit - needfulDigit;
        return (needlessDigit / levelSize) / 2;
    }


    private static <K extends Comparable<K>, V> String string(Node<K, V> node, Function<Node<K, V>, String> toStringFunction) {
        return (node != null) ? toStringFunction.apply(node) : "";
    }

    private static String normalize(String summary, int contentDigit) {
        final String CONTENT_FORMAT = "%" + contentDigit + "s";
        return String.format(CONTENT_FORMAT, summary);
    }

    private static String wrap(String content, int marginDigit) {
        final String MARGIN = " ".repeat(marginDigit);
        final String ELEMENT_FORMAT = MARGIN + "(%s)" + MARGIN;
        return String.format(ELEMENT_FORMAT, content);
    }

}
