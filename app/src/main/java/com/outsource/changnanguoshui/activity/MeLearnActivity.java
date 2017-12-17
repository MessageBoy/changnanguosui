package com.outsource.changnanguoshui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.TabAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.fragment.MeLearnFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/10.
 */

public class MeLearnActivity extends BaseActivity
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

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_party_building);
    }

    @Override
    protected void initData()
    {
        title.setText("我的学习记录");
        mPageTitleList.add("全部");
        mPageTitleList.add("未完成");
        mPageTitleList.add("已完成");
        mFragmentList.add(new MeLearnFragment().newInstance(0));
        mFragmentList.add(new MeLearnFragment().newInstance(1));
        mFragmentList.add(new MeLearnFragment().newInstance(2));
        mAdapter = new TabAdapter(this, getSupportFragmentManager(),
                mFragmentList, mPageTitleList);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.getTabAt(getIntent().getIntExtra("position", 0)).select();
    }


    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }
}

