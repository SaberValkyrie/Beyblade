package com.example.demo.constant;

import java.util.Date;

public class DateConstant {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE = "2002-05-07 07:00:00";
    public static final Integer DEFAULT_EXPIRY = 1; // 1 day

    public static Date getDay(int day) {
        return new Date(System.currentTimeMillis() + day * 86400000);
    }

    public static Date currentDate() {
        return new Date();
    }
}
