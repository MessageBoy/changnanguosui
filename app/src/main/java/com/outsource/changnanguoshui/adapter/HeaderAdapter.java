package com.outsource.changnanguoshui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class HeaderAdapter extends PagerAdapter
{

    private ArrayList<View> pic;

    public HeaderAdapter(ArrayList<View> pic)
    {
        this.pic = pic;

    }

    @Override
    public int getCount()
    {
        return pic.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(pic.get(position));
        return pic.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(pic.get(position));
    }

}