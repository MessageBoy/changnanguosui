package com.outsource.changnanguoshui.bean;

/**
 * Created by Administrator on 2017/12/4.
 */

public class HomeBean
{
    private int num = 0;
    private int icon;
    private String title;


    public HomeBean(int icon, String title)
    {
        this.icon = icon;
        this.title = title;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
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
