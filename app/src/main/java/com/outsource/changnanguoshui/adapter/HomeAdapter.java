package com.outsource.changnanguoshui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.bean.HomeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
{
    Context context;
    List<HomeBean> mData;
    private OnItemClickLitener mOnItemClickLitener;

    public HomeAdapter(Context context, List<HomeBean> mData)
    {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        holder.icon.setImageResource(mData.get(position).getIcon());
        holder.title.setText(mData.get(position).getTitle());
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_home);
            icon = (ImageView) itemView.findViewById(R.id.icon_home);
        }
    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
