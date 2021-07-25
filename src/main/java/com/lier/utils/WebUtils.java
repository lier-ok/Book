package com.lier.utils;


import java.util.Map;

public class WebUtils {
    /*
     * 将字符串转换成为int类型的数据
     * @param strInt
     * @param defaultValue
     * @return
     */
    public static int parseInt(String strInt,int defaultValue) {
        if(strInt == null){
            return defaultValue;
        }
        return Integer.parseInt(strInt);
    }

}
