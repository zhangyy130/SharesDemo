package com.learnice.base_library.utils;

import java.math.BigDecimal;

/**
 * Created by Xuebin He on 2017/5/30.
 * e-mail:learnice.he@gmail.com.
 */

public class HandleNumber {
    /**
     *
     * @param num  double数字
     * @param n  保留小数位数
     * @return  处理后的数字
     */
    public static double handleDoubleTwo(double num,int n) {
        BigDecimal bigDecimal = new BigDecimal(num);
        return bigDecimal.setScale(2, BigDecimal.ROUND_CEILING).doubleValue();
    }
}
