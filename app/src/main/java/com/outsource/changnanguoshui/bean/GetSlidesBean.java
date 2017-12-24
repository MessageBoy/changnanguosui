package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/23.
 */

public class GetSlidesBean
{
    /**
     * status : 1
     * msg :
     * list : [{"slide_id":"98bedd27cea5ffbf","title":"幻灯片1","pic_url":"/upload/201712/23/201712231820060853.png","link_url":""},{"slide_id":"963e52c2b6e2b172","title":"幻灯片2","pic_url":"/upload/201712/23/201712231820060853.png","link_url":""},{"slide_id":"4952bea42957e540","title":"幻灯片3","pic_url":"/upload/201712/23/201712231820060853.png","link_url":""}]
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
         * slide_id : 98bedd27cea5ffbf
         * title : 幻灯片1
         * pic_url : /upload/201712/23/201712231820060853.png
         * link_url :
         */

        private String slide_id;
        private String title;
        private String pic_url;
        private String link_url;

        public String getSlide_id()
        {
            return slide_id;
        }

        public void setSlide_id(String slide_id)
        {
            this.slide_id = slide_id;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getPic_url()
        {
            return pic_url;
        }

        public void setPic_url(String pic_url)
        {
            this.pic_url = pic_url;
        }

        public String getLink_url()
        {
            return link_url;
        }

        public void setLink_url(String link_url)
        {
            this.link_url = link_url;
        }
    }
}
