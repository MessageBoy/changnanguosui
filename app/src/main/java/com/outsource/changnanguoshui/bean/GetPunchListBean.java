package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public class GetPunchListBean
{

    /**
     * status : 1
     * msg : 
     * list : [{"date_str":"2017-12-10","time1":"8:30","time1_status":"正常","time2":"17:35","time2_status":"正常"},{"date_str":"2017-12-11","time1":"8:30","time1_status":"正常","time2":"17:35","time2_status":"正常"},{"date_str":"2017-12-12","time1":"8:30","time1_status":"正常","time2":"17:35","time2_status":"正常"},{"date_str":"2017-12-13","time1":"--:--","time1_status":"请假","time2":"--:--","time2_status":"请假"},{"date_str":"2017-12-14","time1":"8:50","time1_status":"迟到","time2":"17:35","time2_status":"正常"},{"date_str":"2017-12-15","time1":"8:30","time1_status":"正常","time2":"17:35","time2_status":"正常"},{"date_str":"2017-12-16","time1":"8:30","time1_status":"正常","time2":"17:01","time2_status":"早退"},{"date_str":"2017-12-17","time1":"8:30","time1_status":"正常","time2":"17:35","time2_status":"正常"},{"date_str":"2017-12-18","time1":"8:30","time1_status":"正常","time2":"--:--","time2_status":"漏签"},{"date_str":"2017-12-19","time1":"8:30","time1_status":"正常","time2":"17:35","time2_status":"正常"},{"date_str":"2017-12-20","time1":"8:30","time1_status":"正常","time2":"17:35","time2_status":"正常"}]
     */

    private int status;
    private String msg;
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
         * date_str : 2017-12-10
         * time1 : 8:30
         * time1_status : 正常
         * time2 : 17:35
         * time2_status : 正常
         */

        private String date_str;
        private String time1;
        private String time1_status;
        private String time2;
        private String time2_status;

        public String getDate_str()
        {
            return date_str;
        }

        public void setDate_str(String date_str)
        {
            this.date_str = date_str;
        }

        public String getTime1()
        {
            return time1;
        }

        public void setTime1(String time1)
        {
            this.time1 = time1;
        }

        public String getTime1_status()
        {
            return time1_status;
        }

        public void setTime1_status(String time1_status)
        {
            this.time1_status = time1_status;
        }

        public String getTime2()
        {
            return time2;
        }

        public void setTime2(String time2)
        {
            this.time2 = time2;
        }

        public String getTime2_status()
        {
            return time2_status;
        }

        public void setTime2_status(String time2_status)
        {
            this.time2_status = time2_status;
        }
    }
}
