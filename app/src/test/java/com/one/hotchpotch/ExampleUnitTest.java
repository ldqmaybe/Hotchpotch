package com.one.hotchpotch;


import com.one.utils.StringUtils;

import org.junit.Test;

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String time = "2019-12-26T00:20:53.212Z";
        System.out.println(time.split("T")[1].substring(0,time.split("T")[1].length()-1));
        System.out.println(StringUtils.getFormattedDate(time.split("T")[0]+time.split("T")[1].substring(0,time.split("T")[1].length()-1),"yyyy-MM-ddhh:mm:ss.SSS","yyyy年MM月dd日hh时mm分ss秒"));
    }
}