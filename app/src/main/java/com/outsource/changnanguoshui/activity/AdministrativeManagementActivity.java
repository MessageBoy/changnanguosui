package com.outsource.changnanguoshui.activity;

import android.Manifest;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.TabAdapter;
import com.outsource.changnanguoshui.application.PermissionsActivity;
import com.outsource.changnanguoshui.fragment.NoticeBulletinFragment;
import com.outsource.changnanguoshui.fragment.OnlineActivityFragment;
import com.outsource.changnanguoshui.fragment.OnlineCardFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/8.
 */

public class AdministrativeManagementActivity extends PermissionsActivity
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
        isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION,
                Constant.QUEST_CODE_LOCTION);
    }

    @Override
    protected void initData()
    {
        title.setText("行政管理");
        mPageTitleList.add("通知公告");
        mPageTitleList.add("在线打卡");
        mPageTitleList.add("线上活动");
        mFragmentList.add(new NoticeBulletinFragment());
        mFragmentList.add(new OnlineCardFragment());
        mFragmentList.add(new OnlineActivityFragment());
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
