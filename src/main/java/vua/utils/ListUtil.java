package vua.utils;

import java.util.ArrayList;
import java.util.Collections;

public class ListUtil {

    public static <T> ArrayList<T> newList(T ...items) {
        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, items);
        return list;
    }
}
