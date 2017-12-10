package com.outsource.changnanguoshui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.TabAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.fragment.PartyBuildingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PartyBuildingActivity extends BaseActivity
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
        title.setText("党建风采");
        mPageTitleList.add("三会一课");
        mPageTitleList.add("彻底落实十九大精神");
        mPageTitleList.add("两学一做");
        mFragmentList.add(new PartyBuildingFragment().newInstance(1));
        mFragmentList.add(new PartyBuildingFragment().newInstance(2));
        mFragmentList.add(new PartyBuildingFragment().newInstance(3));
        mAdapter = new TabAdapter(this, getSupportFragmentManager(),
                mFragmentList, mPageTitleList);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //设置fragment缓存数为2，不设置默认为1滑到第三个后再回到第一个fragment会从新加载
        viewPager.setOffscreenPageLimit(1);
    }


    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }
}
