package com.outsource.changnanguoshui.activity;

import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.utlis.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/16.
 */

public class StudyDetailsActivity extends BaseActivity
{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.context_ld)
    WebView webView;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_study_details);
    }

    @Override
    protected void initData()
    {
        title.setText(getIntent().getStringExtra("activityTitle"));
//        WebUtils.webSetting(webView, this);
        String webUrl = getIntent().getStringExtra("webUrl");
//        Log.e("webUrl", webUrl);
//        webView.loadUrl(webUrl + "&user_id=" + SpUtils.getParam(this, Constant.USER_ID, ""));


        WebSettings webSettings = webView.getSettings();
        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置文本编码
        webSettings.setDefaultTextEncodingName("GBK");
        // 适应屏幕，内容将自动缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.loadUrl(webUrl + "&user_id=" + SpUtils.getParam(this, Constant.USER_ID, ""));
        webView.setWebViewClient(new webViewClient());

    }

    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack())
        {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }

        finish();// 结束退出程序
        return false;
    }

    // Web视图
    private class webViewClient extends WebViewClient
    {
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }
}
