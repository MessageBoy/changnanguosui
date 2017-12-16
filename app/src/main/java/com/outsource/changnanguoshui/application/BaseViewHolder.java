package com.outsource.changnanguoshui.application;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/12/8.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder
{
    private final SparseArray<View> sparseArray;

    public BaseViewHolder(View itemView)
    {
        super(itemView);
        sparseArray = new SparseArray<>(8);
    }

    public <T extends View> T getView(int viewId)
    {
        View view = sparseArray.get(viewId);
        if (view == null)
        {
            view = itemView.findViewById(viewId);
            sparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public BaseViewHolder setText(int viewId, String text)
    {
        TextView tv = getView(viewId);
        if (tv != null)
        {
            tv.setText(text);
        }
        return this;
    }

    public BaseViewHolder setImageResource(int viewId, int icon)
    {
        ImageView tv = getView(viewId);
        if (tv != null)
        {
            tv.setImageResource(icon);
        }
        return this;
    }

    public BaseViewHolder setImage(int viewId, String url)
    {
        ImageView tv = getView(viewId);
        if (tv != null)
        {
            Picasso.with(MyApplication.getInstance())
                    .load(Constant.DOMAIN_NAME + url)
                    .placeholder(R.mipmap.male_head)
                    .into(tv);
        }
        return this;
    }
}
