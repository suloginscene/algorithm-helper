package com.github.suloginscene.algorithmhelper.core.encode;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Encoder {

    public String encode(String text) {
        List<String> tokens = tokenize(text);
        Map<String, String> metadata = mapTokenCode(tokens);
        validateCodes(metadata.values());

        String content = encodeContent(metadata, tokens);
        return exportString(text.length(), metadata, content);
    }

    protected abstract List<String> tokenize(String text);

    protected abstract Map<String, String> mapTokenCode(List<String> tokens);

    private void validateCodes(Collection<String> values) {
        int n = values.size();
        String[] codes = values.toArray(new String[n]);
        for (int i = 0; i < n; i++) {
            String code = codes[i];
            if (code.isEmpty()) {
                throw new IllegalStateException("Code should not empty.");
            }
            if (code.contains("\n") || code.contains(": ")) {
                throw new IllegalStateException("Code should not contains '\\n' or ': '.");
            }
            for (int j = i + 1; j < n; j++) {
                String anotherCode = codes[j];
                if (anotherCode.startsWith(code)) {
                    throw new IllegalStateException("Code should be prefix code.");
                }
            }
        }
    }

    private String encodeContent(Map<String, String> metadata, List<String> tokens) {
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            sb.append(metadata.get(token));
        }
        return sb.toString();
    }

    private String exportString(int rawLen, Map<String, String> metadata, String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("META ").append(rawLen).append("\n");
        for (Map.Entry<String, String> e : metadata.entrySet()) {
            sb.append(EscapeUtil.escape(e.getKey()));
            sb.append(": ").append(e.getValue()).append("\n");
        }
        sb.append("\n");
        sb.append(content);
        return sb.toString();
    }


    public String decode(String encoded) {
        String[] lines = encoded.split("\n");
        Map<String, String> metadata = parseMetadata(lines);
        String encodedContent = lines[lines.length - 1];
        return decodeContent(encodedContent, metadata);
    }

    private Map<String, String> parseMetadata(String[] lines) {
        validateEncoding(lines[0]);
        Map<String, String> metadata = new HashMap<>();
        int i = 1;
        String line;
        while (!(line = lines[i++]).isEmpty()) {
            String[] tokens = line.split(": ");
            metadata.put(tokens[1], tokens[0]);
        }
        return metadata;
    }

    private String decodeContent(String encodedContent, Map<String, String> meta) {
        String[] chars = encodedContent.split("");
        StringBuilder out = new StringBuilder();
        StringBuilder searchBuffer = new StringBuilder();
        for (String c : chars) {
            searchBuffer.append(c);
            String code = searchBuffer.toString();
            if (meta.containsKey(code)) {
                String token = EscapeUtil.restore(meta.get(code));
                out.append(token);
                searchBuffer.setLength(0);
            }
        }
        return out.toString();
    }

    private void validateEncoding(String startLine) {
        if (!startLine.startsWith("META ")) {
            throw new IllegalArgumentException("Invalid encoding.");
        }
    }

}
