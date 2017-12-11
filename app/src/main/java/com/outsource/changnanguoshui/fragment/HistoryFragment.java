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
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/9.
 */

public class HistoryFragment extends BaseFragment
{
    @BindView(R.id.history_list)
    RecyclerView historyList;
    MyAdapter adapter;

    List<String> mData;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        mData = new ArrayList<>();
        historyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HistoryFragment.MyAdapter(getActivity(), R.layout.item_history, mData);
        historyList.setAdapter(adapter);

    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_history;
    }

    @Override
    protected void initData()
    {
        for (int i = 0; i < 13; i++)
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
        }
    }

    @OnClick(R.id.search_ll)
    public void onViewClicked()
    {
        Alert("选择日期");
    }
}
