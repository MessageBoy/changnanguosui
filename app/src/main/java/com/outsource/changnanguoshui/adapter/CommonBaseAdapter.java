package com.outsource.changnanguoshui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.outsource.changnanguoshui.application.BaseViewHolder;

import java.util.List;

public abstract class CommonBaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>
{
    protected List<T> data;
    protected int itemLayoutId;
    protected Context mContext;
    private onItemClickerListener mListener;

    public CommonBaseAdapter(Context context, @LayoutRes int itemLayoutId, List<T> data)
    {
        this.itemLayoutId = itemLayoutId;
        this.mContext = context;
        this.data = data;
    }


    /**
     * 增加点击监听
     */
    public void setItemListener(onItemClickerListener mListener)
    {
        this.mListener = mListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //这里使用3个参数的方法
        View view = LayoutInflater.from(mContext).inflate(itemLayoutId, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position)
    {

        bindViewData(holder, data.get(position), position);
        holder.itemView.setOnClickListener(getOnClickListener(position));
    }

    private View.OnClickListener getOnClickListener(final int position)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mListener != null && v != null)
                {
                    mListener.onItemClick(v, data.get(position), position);
                }
            }
        };
    }

    @Override
    public int getItemCount()
    {
        return this.data.size();
    }

    public abstract void bindViewData(BaseViewHolder holder, T item, int position);

    public interface onItemClickerListener
    {
        void onItemClick(View view, Object data, int position);
    }
}
