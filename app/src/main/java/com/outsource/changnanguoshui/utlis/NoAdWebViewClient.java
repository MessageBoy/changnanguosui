package com.outsource.changnanguoshui.utlis;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by BrainWang on 05/01/2016.
 */
public class NoAdWebViewClient extends WebViewClient
{
    private String homeurl;
    private Context context;

    public NoAdWebViewClient(Context context, String homeurl)
    {
        this.context = context;
        this.homeurl = homeurl;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url)
    {
        url = url.toLowerCase();
        if (!url.contains(homeurl))
        {
            if (!ADFilterTool.hasAd(context, url))
            {
                return super.shouldInterceptRequest(view, url);
            } else
            {
                return new WebResourceResponse(null, null, null);
            }
        } else
        {
            return super.shouldInterceptRequest(view, url);
        }


    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, String url)
    {
        if (url == null) return false;

        try
        {
            if (url.startsWith("weixin://") //微信
                    || url.startsWith("alipays://") //支付宝
                    || url.startsWith("mailto://") //邮件
                    || url.startsWith("tel://")//电话
                    || url.startsWith("dianping://")//大众点评
                //其他自定义的scheme
                    )
            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
                return true;
            }
        } catch (Exception e)
        { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
            return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
        }

        //处理http和https开头的url
        wv.loadUrl(url);
        return true;
    }
}