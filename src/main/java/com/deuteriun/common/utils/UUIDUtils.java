package com.deuteriun.common.utils;

import java.util.UUID;

/**
 * @ClassName UUIDUtils
 * @Description TODO
 * @Author gavin
 * @Date 9/5/2022 19:54
 * @Version 1.0
 **/
public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getUUIDNonCrossbar() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
