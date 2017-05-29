package com.learnice.base_library.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Xuebin He on 2016/10/23.
 */

public class MD5Secret {
    public static String getMD5String(String pass) {
        try {
            char hexDigits[] = {'0', '1', '2', '3', '4',
                    '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F'};
            byte[] bytes = pass.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            byte[] md = messageDigest.digest();
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
