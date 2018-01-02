package com.outsource.changnanguoshui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BackHandledFragment;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.outsource.changnanguoshui.utlis.WebUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/16.
 */

public class BusinessTransactFragment extends BackHandledFragment
{

    @BindView(R.id.context_ld)
    WebView webView;
    @BindView(R.id.bar)
    RelativeLayout bar;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        bar.setVisibility(View.GONE);
        WebUtils.webSetting(webView, getActivity());
        webView.loadUrl(Constant.BUSINESS_TRANSACT + "&user_id=" + SpUtils.getParam(getActivity(), Constant.USER_ID, ""));
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_study_details;
    }

    @Override
    protected void initData()
    {

    }

    @Override
    public boolean onBackPressed()
    {
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
