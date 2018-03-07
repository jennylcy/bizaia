package com.bizaia.zhongyin.util;

import java.math.BigDecimal;

/**
 * Created by yan on 2017/4/21.
 */

public class NumUtils {
    public static double getTwoPlace(double num) {
        BigDecimal bd = new BigDecimal(num);
        BigDecimal setScale = bd.setScale(2, BigDecimal.ROUND_DOWN);
        return setScale.doubleValue();
    }

    public static float getTwoPlace(float num) {
        BigDecimal bd = new BigDecimal(num);
        BigDecimal setScale = bd.setScale(2, BigDecimal.ROUND_DOWN);
        return setScale.floatValue();
    }

    public static String getTwoPlaceStr(double num) {
        return String.valueOf(getTwoPlace(num));
    }

    public static String getTwoPlaceStr(float num) {
        return String.valueOf(getTwoPlace(num));
    }

    public static double getRoundTwoPlace(double num) {
        BigDecimal bd = new BigDecimal(num);
        BigDecimal setScale = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return setScale.doubleValue();
    }

    public static float getRoundTwoPlace(float num) {
        BigDecimal bd = new BigDecimal(num);
        BigDecimal setScale = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return setScale.floatValue();
    }

    public static String getRoundTwoPlaceStr(double num) {
        return String.valueOf(getRoundTwoPlace(num));
    }

    public static String getRoundTwoPlaceStr(float num) {
        return String.valueOf(getRoundTwoPlace(num));
    }
}
