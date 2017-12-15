package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class GetCategorysBean
{

    /**
     * status : 1
     * msg :
     * list : [{"id":34,"title":"税政法规","img_url":"","child":0},{"id":35,"title":"纳税服务","img_url":"","child":0},{"id":36,"title":"征收管理","img_url":"","child":0},{"id":37,"title":"其他","img_url":"","child":0}]
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
         * id : 34
         * title : 税政法规
         * img_url :
         * child : 0
         */

        private int id;
        private String title;
        private String img_url;
        private int child;

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

        public String getImg_url()
        {
            return img_url;
        }

        public void setImg_url(String img_url)
        {
            this.img_url = img_url;
        }

        public int getChild()
        {
            return child;
        }

        public void setChild(int child)
        {
            this.child = child;
        }
    }
}
