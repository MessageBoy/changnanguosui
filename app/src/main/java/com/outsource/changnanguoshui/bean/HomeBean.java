package com.outsource.changnanguoshui.bean;

/**
 * Created by Administrator on 2017/12/4.
 */

public class HomeBean
{
    int icon;
    String title;

    public HomeBean(int icon, String title)
    {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
