package com.outsource.changnanguoshui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.GetDepartmentBean;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/13.
 */

public class PersonnelManagementActivity extends BaseActivity implements CommonBaseAdapter.onItemClickerListener
{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<GetDepartmentBean.ListBean> mData;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_list);
    }

    @Override
    protected void initData()
    {
        title.setText("党员信息");
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ItemDivider());
        adapter = new MyAdapter(this, R.layout.item_member_info, mData);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        getData();
    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.ACT, "GetDepartment")
                .build()
                .execute(new GenericsCallback<GetDepartmentBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(GetDepartmentBean response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            mData.addAll(response.getList());
                            adapter.notifyDataSetChanged();
                        }
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

    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }

    @Override
    public void onItemClick(View view, Object data, int position)
    {
        Intent intent = new Intent(this, MemberInfoActivity.class);
        intent.putExtra("mData", ((GetDepartmentBean.ListBean) data));
        startActivity(intent);
    }
    //    @BindView(R.id.title)
//    TextView title;
//    @BindView(R.id.tab_layout)
//    TabLayout tabLayout;
//    @BindView(R.id.view_pager)
//    ViewPager viewPager;
//    private List<String> mPageTitleList = new ArrayList<>();
//    private List<Fragment> mFragmentList = new ArrayList<>();
//    private TabAdapter mAdapter;
//
//    @Override
//    protected void initView()
//    {
//        setContentView(R.layout.activity_party_building);
//    }
//
//    @Override
//    protected void initData()
//    {
//        title.setText("人员管理");
//        mPageTitleList.add("党员信息");
//        mPageTitleList.add("账号维护");
//        mFragmentList.add(new MemberInfoFragment());
//        mFragmentList.add(new AccountMaintenanceFragment());
//        mAdapter = new TabAdapter(this, getSupportFragmentManager(),
//                mFragmentList, mPageTitleList);
//        viewPager.setAdapter(mAdapter);
//        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setOffscreenPageLimit(1);
//        tabLayout.getTabAt(getIntent().getIntExtra("position", 0)).select();
//    }
//
//


}
