package com.outsource.changnanguoshui.bean;

/**
 * Created by Administrator on 2017/12/24.
 */

public class GetPunchSetBean
{

    /**
     * status : 1
     * msg : 获取成功！
     * map : 
     * map_range : 50
     * time1_str : 8:00-9:05
     * time2_str : 11:55-12:49
     * time3_str : 12:50-13:35
     * time4_str : 16:55-18:00
     */

    private int status;
    private String msg;
    private String map;
    private int map_range;
    private String time1_str;
    private String time2_str;
    private String time3_str;
    private String time4_str;

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getMap()
    {
        return map;
    }

    public void setMap(String map)
    {
        this.map = map;
    }

    public int getMap_range()
    {
        return map_range;
    }

    public void setMap_range(int map_range)
    {
        this.map_range = map_range;
    }

    public String getTime1_str()
    {
        return time1_str;
    }

    public void setTime1_str(String time1_str)
    {
        this.time1_str = time1_str;
    }

    public String getTime2_str()
    {
        return time2_str;
    }

    public void setTime2_str(String time2_str)
    {
        this.time2_str = time2_str;
    }

    public String getTime3_str()
    {
        return time3_str;
    }

    public void setTime3_str(String time3_str)
    {
        this.time3_str = time3_str;
    }

    public String getTime4_str()
    {
        return time4_str;
    }

    public void setTime4_str(String time4_str)
    {
        this.time4_str = time4_str;
    }
}
