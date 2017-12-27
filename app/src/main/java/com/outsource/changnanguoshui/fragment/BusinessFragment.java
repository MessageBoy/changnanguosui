package com.outsource.changnanguoshui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BackHandledFragment;
import com.outsource.changnanguoshui.utlis.WebUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/4.
 */

public class BusinessFragment extends BackHandledFragment {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.context_ld)
    WebView webView;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_study_details;
    }

    @Override
    protected void initData() {
        title.setText("业务办理");
        back.setVisibility(View.GONE);
        WebUtils.webSetting(webView);
        webView.loadUrl(Constant.BUSINESS_TRANSACT);
    }

    @Override
    public boolean onBackPressed() {
        if (webView.canGoBack())
        {
            webView.goBack();
            Log.v("webView.goBack()", "webView.goBack()");
            return true;
        } else
        {
            Log.v("Conversatio退出", "Conversatio退出");
            return false;
        }
    }
}
