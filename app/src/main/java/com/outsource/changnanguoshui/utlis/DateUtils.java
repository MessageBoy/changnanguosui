package com.outsource.changnanguoshui.utlis;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2017/12/4.
 */

public class DateUtils
{

    public static String getSystemTime()
    {
        DateTime dateTime = new DateTime();
        return dateTime.toString("yyyy-MM-dd");
    }

    public static String getDate(String str)
    {
        String time = "";
        try
        {
            str = str.substring(str.indexOf("(") + 1, str.indexOf("+"));
            time = new DateTime(Long.parseLong(str)).toString("yyyy-MM-dd  HH:mm");
        } catch (Exception e)
        {
            e.getMessage();
        }
        return time;
    }

    // 当周开始时间
    public static Date getWeekStart()
    {

        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday 
        return c.getTime();
    }

    public static boolean isBefore(String dateTime)
    {
        return new DateTime().isBefore(new DateTime(dateTime));
    }

    public static boolean isAfter(String dateTime)
    {
        return new DateTime().isAfter(new DateTime(dateTime));
    }

}
