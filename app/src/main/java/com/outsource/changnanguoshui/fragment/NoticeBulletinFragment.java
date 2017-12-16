package com.outsource.changnanguoshui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.NoticeBulletinBean;
import com.outsource.changnanguoshui.utlis.ItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/8.
 */
public class NoticeBulletinFragment extends BaseFragment
{
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<NoticeBulletinBean> mData = new ArrayList<>();

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new ItemDivider().setDividerColor(ContextCompat.getColor(getActivity(), R.color.div)));
        adapter = new MyAdapter(getActivity(), R.layout.item_notice_bulletin, mData);
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
        getData();
    }

    private void getData()
    {
        for (int i = 0; i < 9; i++)
            mData.add(new NoticeBulletinBean());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    class MyAdapter extends CommonBaseAdapter<NoticeBulletinBean>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<NoticeBulletinBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, NoticeBulletinBean item, int position)
        {
//            holder.setImageResource(R.id.icon_nb, item);
//            holder.setText(R.id.time_nb, );
//            holder.setText(R.id.title_nb, );
        }
    }

}
