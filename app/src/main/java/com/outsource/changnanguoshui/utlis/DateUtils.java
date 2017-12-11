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

    // 当周开始时间
    public static Date getWeekStart()
    {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new DateTime().toDate());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return (Date) c.getTime().clone();
    }


}
