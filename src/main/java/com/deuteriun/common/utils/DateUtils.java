package com.deuteriun.common.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtils {
    public static final String TIME_ZONE = "GMT+8";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_SIMPLE = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_SIMPLE = "yyyyMMdd";
    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final SimpleDateFormat SYSTEM_DATETIME_FORMATOR = new SimpleDateFormat(DATE_TIME_FORMAT);

    /**
     * Get current timestamp
     *
     * @return
     */
    public static String currentTimestamp() {
        return String.valueOf(Instant.now().toEpochMilli());
    }

    public static LocalDateTime currentDate() {
        return LocalDateTime.now();
    }

    public static String currentYearStr() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
    }

    public static String currentMonthStr() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String currentDayStr() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd"));
    }


    /**
     * current system date（yyyy-MM-dd）
     *
     * @return
     */
    public static String currentDateStr() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String currentDateStr(String dateFormat) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(dateFormat));
    }

    /**
     * current system date（yyyy-MM-dd HH:mm:ss）
     *
     * @return
     */
    public static String currentLocalDataTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }


}
