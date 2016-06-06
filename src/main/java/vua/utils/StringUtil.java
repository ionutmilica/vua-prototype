package vua.utils;

public class StringUtil {

    public static String trim(String source, char character) {
        int len = source.length();
        int st = 0;
        char[] val = source.toCharArray();

        while ((st < len) && (val[st] <= character)) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= character)) {
            len--;
        }
        return ((st > 0) || (len < source.length())) ? source.substring(st, len) : source;
    }
}
