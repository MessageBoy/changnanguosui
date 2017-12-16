package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */

public class GetStudyListBean
{

    /**
     * status : 1
     * msg :
     * recordcount : 3
     * page : 1
     * pagecount : 1
     * list : [{"row_number":1,"id":507,"category_id":5,"title":"建立健全五项长效机制 助推远程教育工作增活力出成效","img_url":"/mobile/images/nopic.jpg","add_time":"/Date(1513388871000+0800)/","study_time":2,"category_name":"网上党课","is_favorite":0,"status":0,"read_status":1},{"row_number":2,"id":506,"category_id":4,"title":"政党知识","img_url":"/mobile/images/nopic.jpg","add_time":"/Date(1513388652233+0800)/","study_time":0,"category_name":"党规","is_favorite":0,"status":0,"read_status":1},{"row_number":3,"id":505,"category_id":3,"title":"中国共产党发展党员工作细则(2014年6月10日中办印发)","img_url":"/mobile/images/nopic.jpg","add_time":"/Date(1513388617297+0800)/","study_time":0,"category_name":"党章","is_favorite":0,"status":0,"read_status":1}]
     */

    private int status;
    private String msg;
    private int recordcount;
    private int page;
    private int pagecount;
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

    public int getRecordcount()
    {
        return recordcount;
    }

    public void setRecordcount(int recordcount)
    {
        this.recordcount = recordcount;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getPagecount()
    {
        return pagecount;
    }

    public void setPagecount(int pagecount)
    {
        this.pagecount = pagecount;
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
         * row_number : 1
         * id : 507
         * category_id : 5
         * title : 建立健全五项长效机制 助推远程教育工作增活力出成效
         * img_url : /mobile/images/nopic.jpg
         * add_time : /Date(1513388871000+0800)/
         * study_time : 2
         * category_name : 网上党课
         * is_favorite : 0
         * status : 0
         * read_status : 1
         */

        private int row_number;
        private int id;
        private int category_id;
        private String title;
        private String img_url;
        private String add_time;
        private int study_time;
        private String category_name;
        private int is_favorite;
        private int status;
        private int read_status;

        public int getRow_number()
        {
            return row_number;
        }

        public void setRow_number(int row_number)
        {
            this.row_number = row_number;
        }

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public int getCategory_id()
        {
            return category_id;
        }

        public void setCategory_id(int category_id)
        {
            this.category_id = category_id;
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

        public String getAdd_time()
        {
            return add_time;
        }

        public void setAdd_time(String add_time)
        {
            this.add_time = add_time;
        }

        public int getStudy_time()
        {
            return study_time;
        }

        public void setStudy_time(int study_time)
        {
            this.study_time = study_time;
        }

        public String getCategory_name()
        {
            return category_name;
        }

        public void setCategory_name(String category_name)
        {
            this.category_name = category_name;
        }

        public int getIs_favorite()
        {
            return is_favorite;
        }

        public void setIs_favorite(int is_favorite)
        {
            this.is_favorite = is_favorite;
        }

        public int getStatus()
        {
            return status;
        }

        public void setStatus(int status)
        {
            this.status = status;
        }

        public int getRead_status()
        {
            return read_status;
        }

        public void setRead_status(int read_status)
        {
            this.read_status = read_status;
        }
    }
}
