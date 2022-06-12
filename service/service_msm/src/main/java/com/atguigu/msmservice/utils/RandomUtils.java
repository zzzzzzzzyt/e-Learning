package com.atguigu.msmservice.utils;

public class RandomUtils {
    public static String generateRandom(int digitNumber) {
        if (digitNumber == 0) {
            return "";
        }//Math.random*9 是0-9之间 但是强制取整后 只是0-8整数  所以要先加个1 才可能第一位不是0 有取到9的可能  其实这里也可以向上取整
        String randomStr = String.valueOf((int) ((Math.random() * 9 + 1) * (Math.pow(10, digitNumber - 1))));
        return randomStr;
    }


}
