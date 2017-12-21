package com.outsource.changnanguoshui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.MemberInfoActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.GetDepartmentBean;
import com.outsource.changnanguoshui.utlis.ItemDivider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/13.
 */

public class OrganizationalFragment extends BaseFragment implements OnLoadMoreListener, OnRefreshListener
{
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    MyAdapter adapter;
    List<GetDepartmentBean.ListBean> mData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            mData.addAll((List<GetDepartmentBean.ListBean>) bundle.getSerializable("mData"));
        }
    }

    public static Fragment newInstance(List<GetDepartmentBean.ListBean> mData)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mData", (Serializable) mData);
        OrganizationalFragment fragment = new OrganizationalFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
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
                Intent intent = new Intent(getActivity(), MemberInfoActivity.class);
                intent.putExtra("mData", ((GetDepartmentBean.ListBean) data));
                startActivity(intent);
            }
        });
    }


    class MyAdapter extends CommonBaseAdapter<GetDepartmentBean.ListBean>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<GetDepartmentBean.ListBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, GetDepartmentBean.ListBean item, int position)
        {
            holder.setText(R.id.department_name, item.getDep_name());
        }
    }

    @Override
    public void onLoadMore()
    {
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh()
    {
        swipeToLoadLayout.setRefreshing(false);
    }
}
