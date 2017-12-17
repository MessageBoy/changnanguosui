package com.outsource.changnanguoshui.activity;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/17.
 */

public class LearningProgressActivity extends BaseActivity
{
    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_earning_progress);
    }

    @Override
    protected void initData()
    {
        getData();
    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetMyStudyPlan")
                .addParams("syear", "2017")
                .build()
                .execute(new GenericsCallback<String>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id)
                    {
                    }
                });
    }

}
