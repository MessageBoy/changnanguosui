package com.outsource.changnanguoshui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public class GetDepartmentBean
{


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

    public static class ListBean implements Serializable
    {


        private String dep_id;
        private String dep_name;
        private String content;
        private List<UserlistBean> userlist;
        private List<ListBean> children;

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

        public String getContent()
        {
            return content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }

        public List<UserlistBean> getUserlist()
        {
            return userlist;
        }

        public void setUserlist(List<UserlistBean> userlist)
        {
            this.userlist = userlist;
        }

        public List<ListBean> getChildren()
        {
            return children;
        }

        public void setChildren(List<ListBean> children)
        {
            this.children = children;
        }

        public static class UserlistBean implements Serializable
        {
            /**
             * user_id : 4b6e856acd067838
             * user_name : 13907090374
             * real_name : 黄建军
             * pic_url : /upload/201712/19/201712191025497848.jpg
             * mobile : 13907090374
             * status : 在职
             */

            private String user_id;
            private String user_name;
            private String real_name;
            private String pic_url;
            private String mobile;
            private String status;

            public String getUser_id()
            {
                return user_id;
            }

            public void setUser_id(String user_id)
            {
                this.user_id = user_id;
            }

            public String getUser_name()
            {
                return user_name;
            }

            public void setUser_name(String user_name)
            {
                this.user_name = user_name;
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

            public String getMobile()
            {
                return mobile;
            }

            public void setMobile(String mobile)
            {
                this.mobile = mobile;
            }

            public String getStatus()
            {
                return status;
            }

            public void setStatus(String status)
            {
                this.status = status;
            }
        }
    }
}
