package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/23.
 */

public class GetMyStudyPlanBean
{
    /**
     * status : 1
     * msg :
     * syear : 2017
     * plan_point : 20
     * finish_point : 0
     * list : [{"learn_month":"2017-12","point":20,"count_point":0}]
     */

    private int status;
    private String msg;
    private int syear;
    private int plan_point;
    private int finish_point;
    private List<ListBean> list;

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

    public int getSyear()
    {
        return syear;
    }

    public void setSyear(int syear)
    {
        this.syear = syear;
    }

    public int getPlan_point()
    {
        return plan_point;
    }

    public void setPlan_point(int plan_point)
    {
        this.plan_point = plan_point;
    }

    public int getFinish_point()
    {
        return finish_point;
    }

    public void setFinish_point(int finish_point)
    {
        this.finish_point = finish_point;
    }

    public List<ListBean> getList()
    {
        return list;
    }

    public void setList(List<ListBean> list)
    {
        this.list = list;
    }

    public static class ListBean
    {
        /**
         * learn_month : 2017-12
         * point : 20
         * count_point : 0
         */

        private String learn_month;
        private int point;
        private int count_point;

        public String getLearn_month()
        {
            return learn_month;
        }

        public void setLearn_month(String learn_month)
        {
            this.learn_month = learn_month;
        }

        public int getPoint()
        {
            return point;
        }

        public void setPoint(int point)
        {
            this.point = point;
        }

        public int getCount_point()
        {
            return count_point;
        }

        public void setCount_point(int count_point)
        {
            this.count_point = count_point;
        }
    }
}
