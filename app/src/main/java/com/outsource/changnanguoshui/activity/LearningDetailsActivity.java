package com.outsource.changnanguoshui.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.GetStudyContentBean;
import com.outsource.changnanguoshui.bean.SuccessBean;
import com.outsource.changnanguoshui.utlis.DateUtils;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
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
    @BindView(R.id.videoplayer)
    JCVideoPlayer videoPlayer;
    @BindView(R.id.count_down_view)
    CountdownView countdownView;
    long time = 0;
    boolean ispostStudy = false;
    int isFinish = 0;
    GetStudyContentBean mData;

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
                            mData = response;
                            timeLd.setText(DateUtils.getDate(response.getAdd_time()));
                            titleLb.setText(response.getTitle());
                            webView.loadDataWithBaseURL(null, response.getContent(), "text/html", "utf-8", null);
                            time = (response.getStudy_time() - response.getLearn_time()) * 60000;
                            if (response.getStudy_time() < 1)
                            {
                                ispostStudy = true;
                            }
                            countdownView.start(time);
                            isFinish = response.getIs_finish();
                            videoPlayer.setUp(Constant.DOMAIN_NAME + response.getVideo_src(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, response.getTitle());
                            if (isFinish == 0)
                            {
                                countdownView.setVisibility(View.VISIBLE);
                            }
                            if (response.getIs_video() == 1)
                            {
                                videoPlayer.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed()
    {
        if (JCVideoPlayer.backPress())
        {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        countdownView.restart();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        countdownView.pause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {
            showAlertDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.back)
    public void onViewClicked()
    {
        showAlertDialog();
    }

    private void showAlertDialog()
    {
        if (isFinish == 1)
        {
            finish();
        } else
        {
            final long l = (time - countdownView.getRemainTime()) / 60000;
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("您已学习" + l + "分钟，是否退出！");
            dialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if (ispostStudy)
                            {
                                postStudy(0);
                            } else if (l > 0)
                            {
                                postStudy(l);
                            }
                            dialog.dismiss();
                            finish();
                        }
                    });
            dialog.setNegativeButton("关闭",
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }


    }

    private void postStudy(long l)
    {
        OkHttpUtils
                .post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "AddStudyLog")
                .addParams("article_id", mData.getId() + "")
                .addParams("learn_time", l + "")
                .addParams("title", mData.getTitle())
                .build()
                .execute(new GenericsCallback<SuccessBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(SuccessBean response, int id)
                    {
                        Alert(response.getMsg());
                    }
                });
    }
}
