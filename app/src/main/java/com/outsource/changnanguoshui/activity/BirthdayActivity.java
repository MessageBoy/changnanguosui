package com.outsource.changnanguoshui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.outsource.changnanguoshui.utlis.WebUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开始更新界面
 *
 * @author xiaolei
 */
public class BirthdayActivity extends Activity
{

    @BindView(R.id.birthday_webview)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        ButterKnife.bind(this);
        WebUtils.webSetting(webView, this);
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setBackgroundColor(0);
        webView.loadUrl(Constant.BIRTHDAY_WEBVIEW + SpUtils.getParam(this, Constant.USER_ID, ""));
    }

    @OnClick(R.id.birthday_button)
    public void onViewClicked()
    {
        finish();
    }
}
