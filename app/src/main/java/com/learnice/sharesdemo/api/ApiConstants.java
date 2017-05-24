package com.learnice.sharesdemo.api;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class ApiConstants {

    public static final String HOST_STOCK = "http://web.juhe.cn:8080/finance/stock/";
    public static final String HOST_NEWS = "http://v.juhe.cn/toutiao/";
    public static final String STOCK_APPKEY = "bcafd8d700363a408b74269134b4e744";
    public static final String NEW_APPKEY = "12f98ddf96cac91d8074a00ba712acf7";

    public static String getHost(int hostType) {
        String host = "";
        switch (hostType) {
            case HostType.JU_HE_STOCK:
                host = HOST_STOCK;
                break;
            case HostType.JU_HE_NEWS:
                host = HOST_NEWS;
                break;
        }
        return host;
    }
}
