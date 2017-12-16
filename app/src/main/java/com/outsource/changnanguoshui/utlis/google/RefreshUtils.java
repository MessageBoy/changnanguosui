package com.outsource.changnanguoshui.utlis.google;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

/**
 * Created by Administrator on 2017/12/16.
 */

public class RefreshUtils
{
    public static void isRefresh(SwipeToLoadLayout swipeToLoadLayout)
    {
        if (swipeToLoadLayout.isRefreshing())
        {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (swipeToLoadLayout.isLoadingMore())
        {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }
}
