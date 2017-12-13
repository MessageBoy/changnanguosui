package com.outsource.changnanguoshui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.MemberInfoActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.utlis.ItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/13.
 */

public class MemberInfoFragment extends BaseFragment
{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<String> mData = new ArrayList<>();

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new ItemDivider().setDividerColor(ContextCompat.getColor(getActivity(), R.color.div)));
        adapter = new MyAdapter(getActivity(), R.layout.item_member_info, mData);
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
        adapter.setItemListener(new CommonBaseAdapter.onItemClickerListener()
        {
            @Override
            public void onItemClick(View view, Object data, int position)
            {
                startActivity(MemberInfoActivity.class);
            }
        });
        getData();
    }

    private void getData()
    {
        for (int i = 0; i < 9; i++)
            mData.add("");
        recyclerView.getAdapter().notifyDataSetChanged();
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
//            holder.setImageResource(R.id.icon_nb, item);
//            holder.setText(R.id.time_nb, );
//            holder.setText(R.id.title_nb, );
        }
    }
}
