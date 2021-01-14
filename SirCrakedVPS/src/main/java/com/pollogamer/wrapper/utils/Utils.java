package com.pollogamer.wrapper.utils;

import java.util.List;
import java.util.Random;

public class Utils {

    public static final Random r = new Random();

    public static <T> T getRandomObjectFromList(List<T> list) {
        return list.get(r.nextInt(list.size()));
    }
}
