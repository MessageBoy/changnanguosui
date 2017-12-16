package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */

public class StudyBean {


    /**
     * status : 1
     * msg :
     * recordcount : 1
     * page : 1
     * pagecount : 1
     * list : [{"row_number":1,"id":489,"category_id":11,"title":"第83起\"幽灵\"事件 日本海岸又现尸体:口袋藏纸条","img_url":"/upload/201712/14/201712142307040504.jpg","zhaiyao":"然后是在日本山形县游佐町，有人发现了不明尸体，位于尸体旁边的塑料桶上面也写有某种文字。日本海上保安厅记录了5年关于漂流事件的数据。2013年共80起，2014年共65起，2015年共45起，2016年共66起，2017年共83起(截至13日中午)。","add_time":"/Date(1513264075083+0800)/","page_url":"/mobile/app_article_show.aspx?id=489","read_status":1}]
     */

    private int status;
    private String msg;
    private int recordcount;
    private int page;
    private int pagecount;
    private List<ListBean> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(int recordcount) {
        this.recordcount = recordcount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * row_number : 1
         * id : 489
         * category_id : 11
         * title : 第83起"幽灵"事件 日本海岸又现尸体:口袋藏纸条
         * img_url : /upload/201712/14/201712142307040504.jpg
         * zhaiyao : 然后是在日本山形县游佐町，有人发现了不明尸体，位于尸体旁边的塑料桶上面也写有某种文字。日本海上保安厅记录了5年关于漂流事件的数据。2013年共80起，2014年共65起，2015年共45起，2016年共66起，2017年共83起(截至13日中午)。
         * add_time : /Date(1513264075083+0800)/
         * page_url : /mobile/app_article_show.aspx?id=489
         * read_status : 1
         */

        private int row_number;
        private int id;
        private int category_id;
        private String title;
        private String img_url;
        private String zhaiyao;
        private String add_time;
        private String page_url;
        private int read_status;

        public int getRow_number() {
            return row_number;
        }

        public void setRow_number(int row_number) {
            this.row_number = row_number;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getZhaiyao() {
            return zhaiyao;
        }

        public void setZhaiyao(String zhaiyao) {
            this.zhaiyao = zhaiyao;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPage_url() {
            return page_url;
        }

        public void setPage_url(String page_url) {
            this.page_url = page_url;
        }

        public int getRead_status() {
            return read_status;
        }

        public void setRead_status(int read_status) {
            this.read_status = read_status;
        }
    }
}
