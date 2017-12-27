package com.outsource.changnanguoshui.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/24.
 */

public class CheckApkUpgradeBean implements Serializable
{

    /**
     * status : 1
     * msg : 获取成功！
     * vcode : 1
     * ver : 1.0
     * intro :  演示版本
     * downurl : /apkdown/cngsdj1.0.apk
     */

    private int status;
    private String msg;
    private int vcode;
    private String ver;
    private String intro;
    private String downurl;

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

    public int getVcode()
    {
        return vcode;
    }

    public void setVcode(int vcode)
    {
        this.vcode = vcode;
    }

    public String getVer()
    {
        return ver;
    }

    public void setVer(String ver)
    {
        this.ver = ver;
    }

    public String getIntro()
    {
        return intro;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    public String getDownurl()
    {
        return downurl;
    }

    public void setDownurl(String downurl)
    {
        this.downurl = downurl;
    }
}
