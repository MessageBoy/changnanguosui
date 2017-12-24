package com.outsource.changnanguoshui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BackHandledFragment;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.outsource.changnanguoshui.utlis.WebUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/4.
 */

public class VoteFragment extends BackHandledFragment
{
    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {

    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_vote;
    }

    @Override
    protected void initData()
    {
        WebUtils.webSetting(webView);
        webView.loadUrl(Constant.VOTE_URL + SpUtils.getParam(getActivity(), Constant.USER_ID, ""));
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
