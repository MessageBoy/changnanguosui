package com.outsource.changnanguoshui.adapter.taxBusiness;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.outsource.changnanguoshui.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.MyViewHolder>
{
    Context context;
    List<String> mData;
    private OnItemClickLitener mOnItemClickLitener;

    public BusinessAdapter(Context context, List<String> mData)
    {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_risk_push, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
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

        public MyViewHolder(View itemView)
        {
            super(itemView);

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
