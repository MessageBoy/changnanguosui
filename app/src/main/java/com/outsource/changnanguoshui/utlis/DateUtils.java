package com.outsource.changnanguoshui.utlis;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/4.
 */

public class DateUtils
{

    public static String getSystemTime()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 EEEE");
        Date date = new Date();
        return df.format(date);
    }
}
