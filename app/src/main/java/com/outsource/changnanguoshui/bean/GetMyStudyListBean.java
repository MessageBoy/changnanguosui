package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/17.
 */

public class GetMyStudyListBean
{

    /**
     * status : 1
     * msg :
     * page : 1
     * pagecount : 1
     * list : [{"row_number":1,"id":508,"category_id":5,"title":"市直机关工委\u201c南昌慈善日\u201d积极开展\u201c连心\u201d志愿服务活动","img_url":"/upload/201712/16/201712160949468526.jpg","add_time":"/Date(1513389053887+0800)/","study_time":2,"video_src":"/upload/a.mp4","point":12,"study_type":0,"start_date":"/Date(-2209017600000+0800)/","end_date":"/Date(-2209017600000+0800)/","status":1,"learn_time":2,"is_timespan":0},{"row_number":2,"id":507,"category_id":5,"title":"建立健全五项长效机制 助推远程教育工作增活力出成效","img_url":"/mobile/images/nopic.jpg","add_time":"/Date(1513388871000+0800)/","study_time":2,"video_src":"","point":10,"study_type":1,"start_date":"/Date(-2209017600000+0800)/","end_date":"/Date(-2209017600000+0800)/","status":1,"learn_time":2,"is_timespan":0}]
     */

    private int status;
    private String msg;
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
         * id : 508
         * category_id : 5
         * title : 市直机关工委“南昌慈善日”积极开展“连心”志愿服务活动
         * img_url : /upload/201712/16/201712160949468526.jpg
         * add_time : /Date(1513389053887+0800)/
         * study_time : 2
         * video_src : /upload/a.mp4
         * point : 12
         * study_type : 0
         * start_date : /Date(-2209017600000+0800)/
         * end_date : /Date(-2209017600000+0800)/
         * status : 1
         * learn_time : 2
         * is_timespan : 0
         */

        private int row_number;
        private int id;
        private int category_id;
        private String title;
        private String img_url;
        private String add_time;
        private int study_time;
        private String video_src;
        private int point;
        private int study_type;
        private String start_date;
        private String end_date;
        private int status;
        private int learn_time;
        private int is_timespan;

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

        public String getVideo_src()
        {
            return video_src;
        }

        public void setVideo_src(String video_src)
        {
            this.video_src = video_src;
        }

        public int getPoint()
        {
            return point;
        }

        public void setPoint(int point)
        {
            this.point = point;
        }

        public int getStudy_type()
        {
            return study_type;
        }

        public void setStudy_type(int study_type)
        {
            this.study_type = study_type;
        }

        public String getStart_date()
        {
            return start_date;
        }

        public void setStart_date(String start_date)
        {
            this.start_date = start_date;
        }

        public String getEnd_date()
        {
            return end_date;
        }

        public void setEnd_date(String end_date)
        {
            this.end_date = end_date;
        }

        public int getStatus()
        {
            return status;
        }

        public void setStatus(int status)
        {
            this.status = status;
        }

        public int getLearn_time()
        {
            return learn_time;
        }

        public void setLearn_time(int learn_time)
        {
            this.learn_time = learn_time;
        }

        public int getIs_timespan()
        {
            return is_timespan;
        }

        public void setIs_timespan(int is_timespan)
        {
            this.is_timespan = is_timespan;
        }
    }
}
