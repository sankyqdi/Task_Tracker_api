package app.utils;

import java.util.ArrayList;
import java.util.List;

public class SeparatorObject {

    public static <T> List<T> separatorList(List<T> list, int interval) {

        if (list == null
                ||
                interval <= 0
                ||
                interval > list.size()) {

            return new ArrayList<>();

        }

        List<T> newList = new ArrayList<>();

        for (int i = 0; i < list.size(); i += interval ) {

            newList.add(list.get(i));

        }

        return newList;

    }

    public static <T> List<T> separatorList(List<T> list, int interval, int startIndex) {

        if (list == null
                ||
                interval <= 0
                ||
                interval > list.size()
                ||
                startIndex < 0
                ||
                startIndex >= list.size()
        ) {

            return new ArrayList<>();

        }

        List<T> newList = new ArrayList<>();

        for (int i = startIndex; i < list.size(); i += interval ) {

            newList.add(list.get(i));

        }

        return newList;

    }

}
