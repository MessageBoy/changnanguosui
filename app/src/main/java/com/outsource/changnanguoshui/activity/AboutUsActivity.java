package com.outsource.changnanguoshui.activity;

import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.outsource.changnanguoshui.utlis.WebUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/16.
 */

public class AboutUsActivity extends BaseActivity
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
        setContentView(R.layout.activity_about_us);
    }

    @Override
    protected void initData()
    {
        title.setText(getIntent().getStringExtra("activityTitle"));
        WebUtils.webSetting(webView, this);
        String webUrl = getIntent().getStringExtra("webUrl");
        Log.e("webUrl", webUrl);
        webView.loadUrl(webUrl + "&user_id=" + SpUtils.getParam(this, Constant.USER_ID, ""));
    }

    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent)
    {
        if (keyCode == keyEvent.KEYCODE_BACK)
        {
            if (webView.canGoBack())
            {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, keyEvent);

    }
}
