package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/24.
 */

public class HuDongBean {


    /**
     * status : 1
     * msg :
     * list : [{"row_number":1,"inform_id":"c7de24e8e8ff3e03","title":"用户于2017年12月22日提交","content":"测试测试123abcd","add_time":"/Date(1513930174253+0800)/","is_reply":1},{"row_number":2,"inform_id":"ba2fe07b8419f700","title":"用户于2017年12月17日提交","content":"这个内容笔记爱哦少，我多家了几个内容看看你显示的怎么眼个？这个内容笔记爱哦少，我多家了几个内容看看你显示的怎么眼个？这个内容笔记爱哦少，我多家了几个内容看看你显示的怎么眼个？这个内容笔记爱哦少，我多家了几个内容看看你显示的怎么眼个？这个内容笔记爱哦少，\u2026","add_time":"/Date(1513498750740+0800)/","is_reply":1},{"row_number":3,"inform_id":"e9a854e6738ae0ca","title":"用户于2017年12月17日提交","content":"qwewqe","add_time":"/Date(1513482407843+0800)/","is_reply":1},{"row_number":4,"inform_id":"ddbea2c047f5703","title":"用户于2017年12月17日提交","content":"1111111","add_time":"/Date(1513482092300+0800)/","is_reply":1}]
     */

    private int status;
    private String msg;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * row_number : 1
         * inform_id : c7de24e8e8ff3e03
         * title : 用户于2017年12月22日提交
         * content : 测试测试123abcd
         * add_time : /Date(1513930174253+0800)/
         * is_reply : 1
         */

        private int row_number;
        private String inform_id;
        private String title;
        private String content;
        private String add_time;
        private int is_reply;

        public int getRow_number() {
            return row_number;
        }

        public void setRow_number(int row_number) {
            this.row_number = row_number;
        }

        public String getInform_id() {
            return inform_id;
        }

        public void setInform_id(String inform_id) {
            this.inform_id = inform_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getIs_reply() {
            return is_reply;
        }

        public void setIs_reply(int is_reply) {
            this.is_reply = is_reply;
        }
    }
}
