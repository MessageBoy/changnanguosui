package com.outsource.changnanguoshui.activity;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/16.
 */

public class StudyDetailsActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.context_ld)
    WebView webView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_study_details);
    }

    @Override
    protected void initData() {
        title.setText(getIntent().getStringExtra("activityTitle"));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        String webUrl = getIntent().getStringExtra("webUrl");
        webView.loadUrl(webUrl);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

}
