package com.outsource.changnanguoshui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/10.
 */

public class VoteRankingFragment extends BaseFragment
{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<String> mData;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter(getActivity(), R.layout.item_vote_ranking, mData);
        recyclerView.setAdapter(adapter);
       

    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_list;
    }

    @Override
    protected void initData()
    {
        for (int i = 0; i < 5; i++)
            mData.add("");
    }


    class MyAdapter extends CommonBaseAdapter<String>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<String> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, String item, int position)
        {
            if (position == 0)
                holder.setImageResource(R.id.ranking, R.mipmap.one);
            else if (position == 1)
                holder.setImageResource(R.id.ranking, R.mipmap.two);
            else if (position == 2)
                holder.setImageResource(R.id.ranking, R.mipmap.three);
            else
                holder.setImageResource(R.id.ranking, 0);

        }
    }
}
