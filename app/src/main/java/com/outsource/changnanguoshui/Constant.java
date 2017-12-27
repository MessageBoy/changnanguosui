package com.outsource.changnanguoshui;

import android.Manifest;

/**
 * Created by Administrator on 2017/12/8.
 */

public class Constant
{
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int Two = 2;
    public static final int Three = 3;
    public static final String DOMAIN_NAME = "http://cndj.edongli.net";
    public static final String HTTP_URL = DOMAIN_NAME + "/services/pub/api.ashx";
    public static final String USER_ID = "user_id";
    public static final String TOKEN = "token";
    public static final String ACT = "act";
    public static final String IsLogin = "login";
    public static final String BUSINESS_TRANSACT = DOMAIN_NAME + "/mobile/kjjy.html";
    public static final String VOTE_URL = DOMAIN_NAME + "/mobile/app_vote.aspx?user_id=";
    /**
     * 位置信息权限请求标志
     */
    public static final int QUEST_CODE_LOCTION = 1;
    /**
     * 发送短信权限请求标志
     */
    public static final int QUEST_CODE_SEND_SMS = 2;
    /**
     * 摄像头权限标志
     */
    public static final int QUEST_CODE_CAMERA = 3;
    /**
     * 批量请求权限
     */
    public static final int QUEST_CODE_ALL = 4;
    /**
     * 拨打电话权限
     */
    public static final int QUEST_CODE_CALL_PHONE = 5;
    //要申请的权限  
    public static final String[] permArray =
            {Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
}
