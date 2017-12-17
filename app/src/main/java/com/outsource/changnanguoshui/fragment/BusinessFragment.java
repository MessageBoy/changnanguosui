package com.outsource.changnanguoshui.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/4.
 */

public class BusinessFragment extends BaseFragment {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.context_ld)
    WebView webView;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        title.setText("业务办理");
        back.setVisibility(View.GONE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(Constant.BUSINESS_TRANSACT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_study_details;
    }

    @Override
    protected void initData() {

    }
}
