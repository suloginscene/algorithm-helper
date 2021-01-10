package com.github.suloginscene.algorithm.helper.binarytree;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
class PrintUtil {

    /**
     * [ row > element > content > summary ]
     * Row have many elements.
     * Element wrap content in parenthesis and has margin.
     * Content normalize length of summary.
     * Summary is string from node.toSummary().
     */
    protected static <N extends Node<N, V>, V> void print(@NonNull N root) {
        StringBuilder sb = new StringBuilder();

        List<List<N>> levelOrdered = TraversalUtil.levelOrder(root);

        int contentDigit = DigitUtil.calculateContentDigit(root);
        int elementDigit = DigitUtil.calculateElementDigit(contentDigit);
        int rowDigit = DigitUtil.calculateRowDigit(levelOrdered, elementDigit);

        for (List<N> level : levelOrdered) {
            int marginDigit = DigitUtil.calculateMarginDigit(level, elementDigit, rowDigit);

            for (N node : level) {
                String summary = ConvertUtil.nodeToSummary(node);
                String content = ConvertUtil.summaryToContent(summary, contentDigit);
                String element = ConvertUtil.contentToElement(content, marginDigit);
                sb.append(element);
            }
            sb.append("\n");
        }

        printToLogger(sb.toString());
    }

    private static void printToLogger(String treeString) {
        log.info("\n" +
                "=".repeat(34) + " Print Tree " + "=".repeat(34) + "\n" +
                treeString
        );
    }


    private static class DigitUtil {
        private static <N extends Node<N, V>, V> int calculateContentDigit(N root) {
            int maxLength = 0;
            List<N> nodes = TraversalUtil.inOrder(root);
            for (N node : nodes) {
                int length = node.toSummary().length();
                maxLength = Math.max(length, maxLength);
            }
            if ((maxLength % 2) != 0) {
                maxLength++;
            }
            return maxLength;
        }

        private static int calculateElementDigit(int contentDigit) {
            return contentDigit + 2;
        }

        private static <N extends Node<N, V>, V> int calculateRowDigit(List<List<N>> levelOrdered, int elementDigit) {
            int treeHeight = levelOrdered.size();
            int treeWidth = levelOrdered.get(treeHeight - 1).size();
            return treeWidth * elementDigit;
        }

        private static <N extends Node<N, V>, V> int calculateMarginDigit(List<N> level, int elementDigit, int rowDigit) {
            int levelSize = level.size();
            int needfulDigit = levelSize * elementDigit;
            int needlessDigit = rowDigit - needfulDigit;
            return (needlessDigit / levelSize) / 2;
        }
    }


    private static class ConvertUtil {
        private static <N extends Node<N, V>, V> String nodeToSummary(N node) {
            return (node != null) ? node.toSummary() : "";
        }

        private static String summaryToContent(String summary, int contentDigit) {
            final String CONTENT_FORMAT = "%" + contentDigit + "s";
            return String.format(CONTENT_FORMAT, summary);
        }

        private static String contentToElement(String content, int marginDigit) {
            final String MARGIN = " ".repeat(marginDigit);
            final String ELEMENT_FORMAT = MARGIN + "(%s)" + MARGIN;
            return String.format(ELEMENT_FORMAT, content);
        }
    }

}
