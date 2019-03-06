package com.android.library.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by fitz on 2017/1/10.
 */

public class ConvertUtils {
    public static BigDecimal toDecimal(Object value) {
        if (StringUtils.isEquals(ObjectUtils.nullStrToEmpty(value), "")) {
            return BigDecimal.ZERO;
        } else {
            try {
                BigDecimal bd = new BigDecimal(ObjectUtils.nullStrToEmpty(value));
                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                return bd;
            } catch (Exception ex) {
                return BigDecimal.ZERO;
            }
        }
    }

    /**
     * String To Int
     * @param value
     * @return
     */
    public static int toInt(String value) {
        try {
            int a = Integer.parseInt(value);
            return a;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * String To Int
     * @param value
     * @return
     */
    public static float toFloat(String value) {
        try {
            float a = Float.parseFloat(value);
            return a;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * toDouble
     * @param value
     * @return
     */
    public static double toDouble(String value){
        try {
            if(TextUtils.isEmpty(value)){
                return 0;
            }
            BigDecimal b =   new BigDecimal(value);
            return  b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * toLong
     * @param value
     * @return
     */
    public static long toLong(String value){
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * @Title: fmtMicrometer
     * @Description: 格式化数字为千分位
     * @param text
     * @return    设定文件
     * @return String    返回类型
     */
    public static String fmtMicrometer(String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.00");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.00");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.00;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.00;
        }
        return df.format(number);
    }

    /**
     * 数字转汉字
     * @param number
     * @return
     */
    public static String toChinese(int number){
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";

        String str = String.valueOf(number);
        int n = str.length();
        for (int i = 0; i < n; i++) {

            int num = str.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }
        return result;
    }

    /**
     * doule 类型如果没有小数,显示整数部分
     * @param d
     * @return
     */
    public static String doubleTrans(double d){
        if(Math.round(d)-d==0){
            return String.valueOf((long)d);
        }
        return String.valueOf(d);
    }

    /**
     * 除法保留小数位数
     * @param val
     * @param num
     * @return
     */
    public static String decimalFormat(float val, int num){
        String format = "0";
        if(num > 0){
            format += ".";
        }
        for(int i=0; i<num; i++){
            format += "0";
        }
        DecimalFormat df=new DecimalFormat(format);//设置保留位数

        return df.format(val);
    }

    // 浮点类型保留小数位
    public static float toBigDecimalString(String val, int num){
        if(StringUtils.isEmpty(val)){
            return 0;
        }
        BigDecimal bd = new BigDecimal(val);
        return bd.setScale(num, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    // 浮点类型保留小数位
    public static float toBigDecimalFloat(float val, int num){
        if(val == 0){
            return 0;
        }
        BigDecimal bd = new BigDecimal(val);
        return bd.setScale(num, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    // float和整数乘积，精度丢失问题
    public static BigDecimal toBigDecimal(String price, int cint){
        if(StringUtils.isEmpty(price)){
            price = "0";
        }
        BigDecimal b = new BigDecimal(price);
        BigDecimal aa = b.multiply(new BigDecimal(cint));
        return aa;
    }
}
