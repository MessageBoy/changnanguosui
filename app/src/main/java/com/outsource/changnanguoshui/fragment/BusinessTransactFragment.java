package com.outsource.changnanguoshui.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/16.
 */

public class BusinessTransactFragment extends BaseFragment {

    @BindView(R.id.context_ld)
    WebView webView;
    @BindView(R.id.bar)
    RelativeLayout bar;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bar.setVisibility(View.GONE);
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
