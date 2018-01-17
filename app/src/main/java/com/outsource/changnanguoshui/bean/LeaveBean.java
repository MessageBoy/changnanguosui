package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/15.
 */

public class LeaveBean
{

    /**
     * status : 1
     * msg :
     * page : 1
     * pagecount : 1
     * list : [{"row_number":1,"vacate_id":"86a077fa82af27f6","company_code":"01001","user_id":"635178867d268dfa","dep_id":"fe081443d5bb1ab","party_dep_id":"8bd0874dc7fbeec2,","intro":"回家过年","begin_time":"/Date(1516029840000+0800)/","end_time":"/Date(1518708240000+0800)/","verify":0,"add_time":"/Date(1516030019620+0800)/","to_user_id":"635178867d268dfa","memo":null,"real_name":"popo","verify_str":"审批中"}]
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
         * vacate_id : 86a077fa82af27f6
         * company_code : 01001
         * user_id : 635178867d268dfa
         * dep_id : fe081443d5bb1ab
         * party_dep_id : 8bd0874dc7fbeec2,
         * intro : 回家过年
         * begin_time : /Date(1516029840000+0800)/
         * end_time : /Date(1518708240000+0800)/
         * verify : 0
         * add_time : /Date(1516030019620+0800)/
         * to_user_id : 635178867d268dfa
         * memo : null
         * real_name : popo
         * verify_str : 审批中
         */

        private int row_number;
        private String vacate_id;
        private String company_code;
        private String user_id;
        private String dep_id;
        private String party_dep_id;
        private String intro;
        private String begin_time;
        private String end_time;
        private int verify;
        private String add_time;
        private String to_user_id;
        private String memo;
        private String real_name;
        private String verify_str;

        public int getRow_number()
        {
            return row_number;
        }

        public void setRow_number(int row_number)
        {
            this.row_number = row_number;
        }

        public String getVacate_id()
        {
            return vacate_id;
        }

        public void setVacate_id(String vacate_id)
        {
            this.vacate_id = vacate_id;
        }

        public String getCompany_code()
        {
            return company_code;
        }

        public void setCompany_code(String company_code)
        {
            this.company_code = company_code;
        }

        public String getUser_id()
        {
            return user_id;
        }

        public void setUser_id(String user_id)
        {
            this.user_id = user_id;
        }

        public String getDep_id()
        {
            return dep_id;
        }

        public void setDep_id(String dep_id)
        {
            this.dep_id = dep_id;
        }

        public String getParty_dep_id()
        {
            return party_dep_id;
        }

        public void setParty_dep_id(String party_dep_id)
        {
            this.party_dep_id = party_dep_id;
        }

        public String getIntro()
        {
            return intro;
        }

        public void setIntro(String intro)
        {
            this.intro = intro;
        }

        public String getBegin_time()
        {
            return begin_time;
        }

        public void setBegin_time(String begin_time)
        {
            this.begin_time = begin_time;
        }

        public String getEnd_time()
        {
            return end_time;
        }

        public void setEnd_time(String end_time)
        {
            this.end_time = end_time;
        }

        public int getVerify()
        {
            return verify;
        }

        public void setVerify(int verify)
        {
            this.verify = verify;
        }

        public String getAdd_time()
        {
            return add_time;
        }

        public void setAdd_time(String add_time)
        {
            this.add_time = add_time;
        }

        public String getTo_user_id()
        {
            return to_user_id;
        }

        public void setTo_user_id(String to_user_id)
        {
            this.to_user_id = to_user_id;
        }

        public String getMemo()
        {
            return memo;
        }

        public void setMemo(String memo)
        {
            this.memo = memo;
        }

        public String getReal_name()
        {
            return real_name;
        }

        public void setReal_name(String real_name)
        {
            this.real_name = real_name;
        }

        public String getVerify_str()
        {
            return verify_str;
        }

        public void setVerify_str(String verify_str)
        {
            this.verify_str = verify_str;
        }
    }
}
