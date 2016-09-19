package com.learnice.sharesdemo.Other;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Xuebin He on 2016/6/24.
 */
public class CurrentTime {
    public static String getCurrentTime(){

        return  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

}
