package com.github.suloginscene.algorithmhelper.core.encode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;


/**
 * Profiler logging expected file size on encoded binary string.
 */
@Slf4j @RequiredArgsConstructor
public class EncoderProfiler extends Encoder {

    private final Encoder encoder;

    @Override
    public String encode(String text) {
        int rawLen = text.getBytes().length;

        String encoded = super.encode(text);

        boolean binary = true;
        String[] lines = encoded.split("\n");
        String[] chars = lines[lines.length - 1].split("");
        for (String c : chars) {
            if (!c.equals("0") && !c.equals("1")) {
                binary = false;
                break;
            }
        }
        if (binary) {
            int compLen = (chars.length / 8) + 1;
            log.info("\n> This is binary string. " + rawLen + " bytes(raw) -> " + compLen + " bytes(comp, to bits), except metadata.");
        }
        return encoded;
    }

    @Override
    protected List<String> tokenize(String text) {
        return encoder.tokenize(text);
    }

    @Override
    protected Map<String, String> mapTokenCode(List<String> tokens) {
        return encoder.mapTokenCode(tokens);
    }

}
