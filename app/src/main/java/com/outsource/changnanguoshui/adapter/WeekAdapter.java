package com.outsource.changnanguoshui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.bean.WeekBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.MyViewHolder>
{
    Context context;
    List<WeekBean> mData;

    public WeekAdapter(Context context, List<WeekBean> mData)
    {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_week, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        holder.day.setText(mData.get(position).getDay());
        holder.week.setText(mData.get(position).getWeek());
        if (mData.get(position).isSameDay())
        {
            holder.day.setBackgroundResource(R.mipmap.selected_date);
            holder.day.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.week.setTextColor(ContextCompat.getColor(context, R.color.text));
        } else
        {
            holder.day.setBackgroundResource(0);
            holder.day.setTextColor(ContextCompat.getColor(context, R.color.title));
            holder.week.setTextColor(ContextCompat.getColor(context, R.color.title));
        }
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView day;
        TextView week;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.day);
            week = (TextView) itemView.findViewById(R.id.week);
        }
    }


}
