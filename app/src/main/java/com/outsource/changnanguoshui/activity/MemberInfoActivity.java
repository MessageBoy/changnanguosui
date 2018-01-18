package com.outsource.changnanguoshui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.TabAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.GetDepartmentBean;
import com.outsource.changnanguoshui.fragment.DepartmentalProfileFragment;
import com.outsource.changnanguoshui.fragment.MemberInformationFragment;
import com.outsource.changnanguoshui.fragment.OrganizationalFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/13.
 */

public class MemberInfoActivity extends BaseActivity
{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private List<String> mPageTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TabAdapter mAdapter;
    private GetDepartmentBean.ListBean mData;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_party_building);
    }

    @Override
    protected void initData()
    {
        mData = (GetDepartmentBean.ListBean) getIntent().getSerializableExtra("mData");
        title.setText("组织信息");
        mPageTitleList.add("组织简介");
        mPageTitleList.add("组织架构");
        mPageTitleList.add("党员信息");
        mFragmentList.add(new DepartmentalProfileFragment().newInstance(mData.getContent()));
        mFragmentList.add(new OrganizationalFragment().newInstance(mData.getDep_pic()));
        mFragmentList.add(new MemberInformationFragment().newInstance(mData.getUserlist()));
        mAdapter = new TabAdapter(this, getSupportFragmentManager(),
                mFragmentList, mPageTitleList);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
    }


    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }
}
