package com.outsource.changnanguoshui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.OnlineActivityActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.utlis.ItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/8.
 */

public class OnlineActivityFragment extends BaseFragment
{
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<String> mData;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new ItemDivider().setDividerColor(R.color.div));
        adapter = new MyAdapter(getActivity(), R.layout.item_activity, mData);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(new CommonBaseAdapter.onItemClickerListener()
        {
            @Override
            public void onItemClick(View view, Object data, int position)
            {
                startActivity(OnlineActivityActivity.class);
            }
        });

    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_list;
    }

    @Override
    protected void initData()
    {
        for (int i = 0; i < 4; i++)
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

}
