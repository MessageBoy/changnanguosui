package com.outsource.changnanguoshui.utlis;

import android.content.Context;
import android.content.res.Resources;

import com.outsource.changnanguoshui.R;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ADFilterTool
{
    public static boolean hasAd(Context context, String url)
    {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
        for (String adUrl : adUrls)
        {
            if (url.contains(adUrl))
            {
                return true;
            }
        }
        return false;
    }
}
