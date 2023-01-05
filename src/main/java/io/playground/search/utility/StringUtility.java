package io.playground.search.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtility {
    public static String removeHtmlTags(String str) {
        return str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    }

    public static String removeWhiteSpace(String str) {
        return str.replaceAll("\\s", "");
    }
}
