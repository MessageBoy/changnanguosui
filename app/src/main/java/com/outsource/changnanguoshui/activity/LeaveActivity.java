package com.outsource.changnanguoshui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.TabAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/15.
 */

public class LeaveActivity extends BaseActivity
{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.wyqj)
    TextView wyqj;
    private List<String> mPageTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TabAdapter mAdapter;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_party_building);
    }


    @Override
    protected void initData()
    {
        title.setText("请假管理");
        wyqj.setVisibility(View.VISIBLE);
        mPageTitleList.add("我的请假");
        mPageTitleList.add("请假审批");
        mFragmentList.add(new LeaveFragment().newInstance("0"));
        mFragmentList.add(new LeaveFragment().newInstance("1"));
        mAdapter = new TabAdapter(this, getSupportFragmentManager(),
                mFragmentList, mPageTitleList);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @OnClick({R.id.back, R.id.wyqj})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.wyqj:
                startActivity(IwantLeaveActivty.class);
                break;
        }
    }
}
