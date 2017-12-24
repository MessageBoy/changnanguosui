package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/24.
 */

public class GetIndexInfoNumBean
{
    /**
     * status : 1
     * msg : 有数据
     * list : [{"id":-1,"title":"计划任务","info_num":0},{"id":14,"title":"上级文件","info_num":99},{"id":15,"title":"学习教育","info_num":13},{"id":16,"title":"活动展示","info_num":22},{"id":22,"title":"企业动态","info_num":31},{"id":23,"title":"党建体系","info_num":0},{"id":17,"title":"通知公告","info_num":50},{"id":-2,"title":"爱心接力","info_num":0}]
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
         * id : -1
         * title : 计划任务
         * info_num : 0
         */

        private int id;
        private String title;
        private int info_num;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public int getInfo_num()
        {
            return info_num;
        }

        public void setInfo_num(int info_num)
        {
            this.info_num = info_num;
        }
    }
}
