package com.yaothao.order.utils;

import java.util.Random;

public class KeyUtil {

    public static synchronized String getUniqueKey() {
        Random random = new Random();
        int num = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(num);
    }
}
