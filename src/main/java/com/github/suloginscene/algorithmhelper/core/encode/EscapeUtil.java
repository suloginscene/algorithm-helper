package com.github.suloginscene.algorithmhelper.core.encode;

import java.util.HashMap;
import java.util.Map;


class EscapeUtil {

    private static final Map<String, String> ESCAPE;
    private static final Map<String, String> RESTORE;

    static {
        ESCAPE = new HashMap<>();
        ESCAPE.put("\n", "<br>");
        ESCAPE.put(": ", "<s>");
        RESTORE = new HashMap<>();
        RESTORE.put("<br>", "\n");
        RESTORE.put("<s>", ": ");
    }


    protected static String escape(String s) {
        return ESCAPE.getOrDefault(s, s);
    }

    public static String restore(String s) {
        return RESTORE.getOrDefault(s, s);
    }

}
