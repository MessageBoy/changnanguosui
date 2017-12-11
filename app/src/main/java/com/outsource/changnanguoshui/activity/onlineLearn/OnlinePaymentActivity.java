package com.outsource.changnanguoshui.activity.onlineLearn;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.TabAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.fragment.onlineLearn.OnlineNotPaymentFragment;
import com.outsource.changnanguoshui.fragment.onlineLearn.OnlinePaymentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/5.
 */

public class OnlinePaymentActivity extends BaseActivity
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
        setContentView(R.layout.activity_online_payment);
    }

    @Override
    protected void initData()
    {
        title.setText("在线缴费");
        mPageTitleList.add("未缴纳党费");
        mPageTitleList.add("已缴纳党费");
        mFragmentList.add(new OnlineNotPaymentFragment().newInstance(1));
        mFragmentList.add(new OnlinePaymentFragment().newInstance(2));
        mAdapter = new TabAdapter(this, getSupportFragmentManager(),
                mFragmentList, mPageTitleList);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //设置fragment缓存数为2，不设置默认为1滑到第三个后再回到第一个fragment会从新加载
        viewPager.setOffscreenPageLimit(2);
    }


    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }
}
