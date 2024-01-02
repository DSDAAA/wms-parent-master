package com.atguigu.wms.common.util;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public final class MD5 {

    public static String encrypt(String strSrc) {
        try {
            char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            byte[] bytes = strSrc.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);
        }
    }

    public static void main(String[] args) {
//        Long a = new Date().getTime() / 1000;
//        System.out.println(a);
        DateTime dateTime = new DateTime().plusDays(5);
        int dayOfMonth = dateTime.getDayOfMonth();
        DateTime lastDate = dateTime.dayOfMonth().withMaximumValue();
        int lastDay = lastDate.getDayOfMonth();
        System.out.println(dateTime.getDayOfWeek());
        System.out.println(lastDay);
    }
}
