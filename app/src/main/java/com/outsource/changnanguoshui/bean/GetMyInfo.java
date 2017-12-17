package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/17.
 */

public class GetMyInfo
{
    /**
     * status : 1
     * msg : 获取成功
     * real_name : 李宏
     * pic_url : /upload/avatar/1cb6ca1186cfd000.jpg
     * sex : 男
     * id_card : 362301196104210517
     * telephone :
     * mobile : 13870057068
     * email :
     * birthday : 1961-04-21
     * dep_id : cc9494a81282dac2
     * dep_name : 测试行政机构
     * deplist : [{"dep_id":"cc9494a81282dac2","dep_name":"南昌县国家税务局","children":[{"dep_id":"d61dc5100384876b","dep_name":"局领导","children":[]},{"dep_id":"fe081443d5bb1ab","dep_name":"办公室","children":[]},{"dep_id":"47c2a7bea054f625","dep_name":"税政法规科","children":[]},{"dep_id":"7a5537238d22728c","dep_name":"财务科","children":[]},{"dep_id":"8148d5b536c764c2","dep_name":"信息中心","children":[]},{"dep_id":"4ca88ed748ffcc11","dep_name":"人事教育科","children":[]},{"dep_id":"93b23ff2a779bb10","dep_name":"征收管理科","children":[]},{"dep_id":"c3037eb65cac68d9","dep_name":"监察室","children":[]},{"dep_id":"3a381ceab874c901","dep_name":"工会","children":[]},{"dep_id":"2c7ce13709c716bb","dep_name":"办税服务厅","children":[]},{"dep_id":"eaa3662b343cd6fa","dep_name":"纳税评估科","children":[]},{"dep_id":"e44689382aba99da","dep_name":"税源管理科一科","children":[]},{"dep_id":"efe2ecebe9e760a8","dep_name":"税源管理科二科","children":[]},{"dep_id":"6b240296680873d1","dep_name":"稽查局","children":[]},{"dep_id":"74bfe33177b7c7c6","dep_name":"小蓝分局","children":[]},{"dep_id":"2dff6c31aaa095ae","dep_name":"向塘分局","children":[]},{"dep_id":"5052e6f7357edf51","dep_name":"武阳分局","children":[]},{"dep_id":"c5ad60912d6c951e","dep_name":"蒋巷分局","children":[]},{"dep_id":"1f471204f4e77d98","dep_name":"离退休","children":[]}]}]
     */

    private int status;
    private String msg;
    private String real_name;
    private String pic_url;
    private String sex;
    private String id_card;
    private String telephone;
    private String mobile;
    private String email;
    private String birthday;
    private String dep_id;
    private String dep_name;
    private List<DeplistBean> deplist;

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

    public String getReal_name()
    {
        return real_name;
    }

    public void setReal_name(String real_name)
    {
        this.real_name = real_name;
    }

    public String getPic_url()
    {
        return pic_url;
    }

    public void setPic_url(String pic_url)
    {
        this.pic_url = pic_url;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getId_card()
    {
        return id_card;
    }

    public void setId_card(String id_card)
    {
        this.id_card = id_card;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getDep_id()
    {
        return dep_id;
    }

    public void setDep_id(String dep_id)
    {
        this.dep_id = dep_id;
    }

    public String getDep_name()
    {
        return dep_name;
    }

    public void setDep_name(String dep_name)
    {
        this.dep_name = dep_name;
    }

    public List<DeplistBean> getDeplist()
    {
        return deplist;
    }

    public void setDeplist(List<DeplistBean> deplist)
    {
        this.deplist = deplist;
    }

    public static class DeplistBean
    {
        /**
         * dep_id : cc9494a81282dac2
         * dep_name : 南昌县国家税务局
         * children : [{"dep_id":"d61dc5100384876b","dep_name":"局领导","children":[]},{"dep_id":"fe081443d5bb1ab","dep_name":"办公室","children":[]},{"dep_id":"47c2a7bea054f625","dep_name":"税政法规科","children":[]},{"dep_id":"7a5537238d22728c","dep_name":"财务科","children":[]},{"dep_id":"8148d5b536c764c2","dep_name":"信息中心","children":[]},{"dep_id":"4ca88ed748ffcc11","dep_name":"人事教育科","children":[]},{"dep_id":"93b23ff2a779bb10","dep_name":"征收管理科","children":[]},{"dep_id":"c3037eb65cac68d9","dep_name":"监察室","children":[]},{"dep_id":"3a381ceab874c901","dep_name":"工会","children":[]},{"dep_id":"2c7ce13709c716bb","dep_name":"办税服务厅","children":[]},{"dep_id":"eaa3662b343cd6fa","dep_name":"纳税评估科","children":[]},{"dep_id":"e44689382aba99da","dep_name":"税源管理科一科","children":[]},{"dep_id":"efe2ecebe9e760a8","dep_name":"税源管理科二科","children":[]},{"dep_id":"6b240296680873d1","dep_name":"稽查局","children":[]},{"dep_id":"74bfe33177b7c7c6","dep_name":"小蓝分局","children":[]},{"dep_id":"2dff6c31aaa095ae","dep_name":"向塘分局","children":[]},{"dep_id":"5052e6f7357edf51","dep_name":"武阳分局","children":[]},{"dep_id":"c5ad60912d6c951e","dep_name":"蒋巷分局","children":[]},{"dep_id":"1f471204f4e77d98","dep_name":"离退休","children":[]}]
         */

        private String dep_id;
        private String dep_name;
        private List<ChildrenBean> children;

        public String getDep_id()
        {
            return dep_id;
        }

        public void setDep_id(String dep_id)
        {
            this.dep_id = dep_id;
        }

        public String getDep_name()
        {
            return dep_name;
        }

        public void setDep_name(String dep_name)
        {
            this.dep_name = dep_name;
        }

        public List<ChildrenBean> getChildren()
        {
            return children;
        }

        public void setChildren(List<ChildrenBean> children)
        {
            this.children = children;
        }

        public static class ChildrenBean
        {
            /**
             * dep_id : d61dc5100384876b
             * dep_name : 局领导
             * children : []
             */

            private String dep_id;
            private String dep_name;
            private List<ChildrenBean> children;

            public String getDep_id()
            {
                return dep_id;
            }

            public void setDep_id(String dep_id)
            {
                this.dep_id = dep_id;
            }

            public String getDep_name()
            {
                return dep_name;
            }

            public void setDep_name(String dep_name)
            {
                this.dep_name = dep_name;
            }

            public List<ChildrenBean> getChildren()
            {
                return children;
            }

            public void setChildren(List<ChildrenBean> children)
            {
                this.children = children;
            }
        }
    }
}
