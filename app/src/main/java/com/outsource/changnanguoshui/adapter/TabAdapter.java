package com.outsource.changnanguoshui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class TabAdapter extends FragmentPagerAdapter
{

    private Context mContext;
    private List<Fragment> mFragmentList;
    private List<String> mPageTitleList;

    public TabAdapter(Context context,
                      FragmentManager fm,
                      List<Fragment> fragmentList,
                      List<String> pageTitleList)
    {
        super(fm);
        this.mContext = context;
        this.mFragmentList = fragmentList;
        this.mPageTitleList = pageTitleList;
    }


    @Override
    public Fragment getItem(int position)
    {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mPageTitleList.get(position);
    }


}
