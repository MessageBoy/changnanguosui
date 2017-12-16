package com.outsource.changnanguoshui.activity;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.GetStudyContentBean;
import com.outsource.changnanguoshui.utlis.DateUtils;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/16.
 */

public class LearningDetailsActivity extends BaseActivity
{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.context_ld)
    WebView webView;
    @BindView(R.id.title_ld)
    TextView titleLb;
    @BindView(R.id.time_ld)
    TextView timeLd;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_learning_details);
    }

    @Override
    protected void initData()
    {
        title.setText("学习内容");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        getData();
        
    }

    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetStudyContent")
                .addParams("id", getIntent().getIntExtra("id", 0) + "")
                .build()
                .execute(new GenericsCallback<GetStudyContentBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(GetStudyContentBean response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            timeLd.setText(DateUtils.getDate(response.getAdd_time()));
                            titleLb.setText(response.getTitle());
                            webView.loadDataWithBaseURL(null, response.getContent(), "text/html", "utf-8", null);
                        }
                    }
                });
    }

}
