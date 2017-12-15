package com.outsource.changnanguoshui.bean;

/**
 * Created by Administrator on 2017/12/15.
 */

public class GetMyBean
{

    /**
     * status : 1
     * msg : 获取成功
     * real_name : 李宏
     * pic_url : /upload/avatar/1cb6ca1186cfd000.jpg
     * study_unfinish : 0
     * study_plan : 0
     * favorite_num : 0
     */

    private int status;
    private String msg;
    private String real_name;
    private String pic_url;
    private int study_unfinish;
    private int study_plan;
    private int favorite_num;

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

    public String getReal_name()
    {
        return real_name;
    }

    public void setReal_name(String real_name)
    {
        this.real_name = real_name;
    }

    public String getPic_url()
    {
        return pic_url;
    }

    public void setPic_url(String pic_url)
    {
        this.pic_url = pic_url;
    }

    public int getStudy_unfinish()
    {
        return study_unfinish;
    }

    public void setStudy_unfinish(int study_unfinish)
    {
        this.study_unfinish = study_unfinish;
    }

    public int getStudy_plan()
    {
        return study_plan;
    }

    public void setStudy_plan(int study_plan)
    {
        this.study_plan = study_plan;
    }

    public int getFavorite_num()
    {
        return favorite_num;
    }

    public void setFavorite_num(int favorite_num)
    {
        this.favorite_num = favorite_num;
    }
}
