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
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.MemberInformationActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.GetDepartmentBean;
import com.outsource.changnanguoshui.utlis.ItemDivider;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/13.
 */

public class MemberInformationFragment extends BaseFragment implements OnLoadMoreListener, OnRefreshListener
{
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    MyAdapter adapter;
    List<GetDepartmentBean.ListBean.UserlistBean> mData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            mData.addAll((List<GetDepartmentBean.ListBean.UserlistBean>) bundle.getSerializable("mData"));
        }
    }

    public static Fragment newInstance(List<GetDepartmentBean.ListBean.UserlistBean> mData)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mData", (Serializable) mData);
        MemberInformationFragment fragment = new MemberInformationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new ItemDivider().setDividerColor(ContextCompat.getColor(getActivity(), R.color.div)));
        adapter = new MyAdapter(getActivity(), R.layout.item_member_information, mData);
        recyclerView.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
       
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
                Intent intent = new Intent(getActivity(), MemberInformationActivity.class);
                intent.putExtra("user_id", ((GetDepartmentBean.ListBean.UserlistBean) data).getUser_id());
                intent.putExtra("title", "成员信息");
                startActivity(intent);
            }
        });
    }


    class MyAdapter extends CommonBaseAdapter<GetDepartmentBean.ListBean.UserlistBean>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<GetDepartmentBean.ListBean.UserlistBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, GetDepartmentBean.ListBean.UserlistBean item, int position)
        {
            holder.setText(R.id.infos_name, item.getReal_name());
            holder.setCircleImage(R.id.infos_icon, item.getPic_url());
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
