package com.lazywg.assembly.sql.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * author: gaowang
 * <p>
 * createTime:2018年2月1日 下午9:23:53
 */
public class DateFmtUtil {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String dateFormate(Date date) {
        return dateFormat.format(date);
    }

    public static String simpleDateFormate(Date date) {
        return simpleDateFormat.format(date);
    }

    public static String shortDateFormate(Date date) {
        return shortDateFormat.format(date);
    }

    public static String currentDateFormate() {
        Calendar calendar = Calendar.getInstance();
        return dateFormate(calendar.getTime());
    }
}
