package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/24.
 */

public class MyCollectBean {


    /**
     * status : 1
     * msg :
     * page : 1
     * pagecount : 1
     * list : [{"row_number":1,"favorite_id":"d60dfb1d94661ce1","info_id":"643","title":"坚持和平发展道路，推动构建人类命运共同体（十九大报告第十二部分）"},{"row_number":2,"favorite_id":"5631c50aeb0c62fb","info_id":"644","title":"坚定不移全面从严治党，不断提高党的执政能力和领导水平上（十九大报告第十三部分）"},{"row_number":3,"favorite_id":"ba0cd7099fe1d03e","info_id":"649","title":"坚定不移全面从严治党，不断提高党的执政能力和领导水平下（十九大报告第十三部分）"},{"row_number":4,"favorite_id":"fb75f7789e0b7389","info_id":"820","title":"2017年11月11日江西卫视新闻联播-南昌县国税局小蓝支部落实十九大精神，用实际行动抓好基层党建"},{"row_number":5,"favorite_id":"ea359d8252bfc080","info_id":"619","title":"中国共产党廉洁自律准则"},{"row_number":6,"favorite_id":"cb8f30dff9ff51f8","info_id":"620","title":"中国共产党纪律处分条例"},{"row_number":7,"favorite_id":"bbc14c9bb54e4199","info_id":"622","title":"中国共产党章程（新修订）"}]
     */

    private int status;
    private String msg;
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
         * favorite_id : d60dfb1d94661ce1
         * info_id : 643
         * title : 坚持和平发展道路，推动构建人类命运共同体（十九大报告第十二部分）
         */

        private int row_number;
        private String favorite_id;
        private String info_id;
        private String title;

        public int getRow_number() {
            return row_number;
        }

        public void setRow_number(int row_number) {
            this.row_number = row_number;
        }

        public String getFavorite_id() {
            return favorite_id;
        }

        public void setFavorite_id(String favorite_id) {
            this.favorite_id = favorite_id;
        }

        public String getInfo_id() {
            return info_id;
        }

        public void setInfo_id(String info_id) {
            this.info_id = info_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
