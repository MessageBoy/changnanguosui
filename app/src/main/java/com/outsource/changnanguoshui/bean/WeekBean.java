package com.outsource.changnanguoshui.bean;

/**
 * Created by Administrator on 2017/12/9.
 */

public class WeekBean
{
    private String week;
    private String day;
    private boolean isSameDay;

    public WeekBean(String week, String day, boolean isSameDay)
    {
        this.week = week;
        this.day = day;
        this.isSameDay = isSameDay;
    }

    public boolean isSameDay()
    {
        return isSameDay;
    }

    public void setSameDay(boolean sameDay)
    {
        isSameDay = sameDay;
    }

    public String getWeek()
    {
        return week;
    }

    public void setWeek(String week)
    {
        this.week = week;
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }
}
