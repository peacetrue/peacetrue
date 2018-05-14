package com.github.peacetrue.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a util class for regex
 *
 * @author xiayx
 */
public abstract class RegexUtils {


    /**
     * extract value of placeholder in regex
     *
     * @param regex   the regex
     * @param message the message
     * @return the value of placeholder in regex
     */
    public static String[] extractValue(String regex, String message) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if (!matcher.find()) return CollectionUtils.emptyArray(String.class);
        String[] placeholders = new String[matcher.groupCount()];
        for (int i = 1; i <= matcher.groupCount(); i++) {
            placeholders[i - 1] = matcher.group(i);
        }
        return placeholders;
    }
}
