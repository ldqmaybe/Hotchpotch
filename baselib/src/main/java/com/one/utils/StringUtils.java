package com.one.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author LinDingQiang
 * @time 2019/12/27 16:59
 * @email dingqiang.l@verifone.cn
 */
public class StringUtils {
    public static String getFormattedDate(String date, String oldFormat, String newFormat) {
        try {
            return (new SimpleDateFormat(newFormat)).format((new SimpleDateFormat(oldFormat)).parse(date));
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
