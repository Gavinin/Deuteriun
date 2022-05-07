package com.deuteriun.system.utils;

import com.deuteriun.system.cache.redis.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateUtils {
    public static final String TIME_ZONE = "GMT+8";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
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

    public static LocalDateTime currentDate(){
        return LocalDateTime.now();
    }

    /**
     * current system date（yyyy-MM-dd）
     *
     * @return
     */
    public static String currentDateStr() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
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
