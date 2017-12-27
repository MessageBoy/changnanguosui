package com.outsource.changnanguoshui.activity.ShuiQiHuDong;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.TabAdapter;
import com.outsource.changnanguoshui.application.BackHandledFragment;
import com.outsource.changnanguoshui.application.BackHandledInterface;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.fragment.BusinessTransactFragment;
import com.outsource.changnanguoshui.fragment.ShuiQiHuDong.ShuiQiHuDongFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/5.
 */

public class ShuiQiHDActivity extends BaseActivity implements BackHandledInterface
{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_add)
    TextView tabAdd;
    private List<String> mPageTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TabAdapter mAdapter;
    private BackHandledFragment mBackHandedFragment;
    int position;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_tax_interaction);
    }

    @Override
    protected void initData() {
        title.setText("税企互动");
        mPageTitleList.add("在线答疑");
        mPageTitleList.add("税收业务");
        mPageTitleList.add("举报建议");
        mFragmentList.add(new ShuiQiHuDongFragment().newInstance(2));
        mFragmentList.add(new BusinessTransactFragment());
        mFragmentList.add(new ShuiQiHuDongFragment().newInstance(1));
        mAdapter = new TabAdapter(this, getSupportFragmentManager(),
                mFragmentList, mPageTitleList);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //设置fragment缓存数为2，不设置默认为1滑到第三个后再回到第一个fragment会从新加载
        viewPager.setOffscreenPageLimit(2);
        position=getIntent().getIntExtra("position", 0);
        tabLayout.getTabAt(position).select();
        if(position==0){
            tabAdd.setText("新建咨询");
            tabAdd.setVisibility(View.VISIBLE);
        }else if(position==1){
            tabAdd.setVisibility(View.GONE);
        }else{
            tabAdd.setText("廉政举报");
            tabAdd.setVisibility(View.VISIBLE);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int posit) {
                position=posit;
                if(position==0){
                    tabAdd.setText("新建咨询");
                    tabAdd.setVisibility(View.VISIBLE);
                }else if(position==1){
                    tabAdd.setVisibility(View.GONE);
                }else{
                    tabAdd.setText("廉政举报");
                    tabAdd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.back, R.id.tab_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tab_add:
                Intent intent;
                intent = new Intent(getApplicationContext(), ConsultMsgActivity.class);
                if(position==0) {
                    intent.putExtra("activityTitle", "在线答疑");
                    intent.putExtra("category_id", 2);
                    intent.putExtra("content_hint", "请输入税务提问信息");
                    intent.putExtra("button_msg", "提问");
                    startActivity(intent);
                }else{
                    intent.putExtra("activityTitle", "举报建议");
                    intent.putExtra("category_id", 1);
                    intent.putExtra("content_hint", "请输入举报建议信息");
                    intent.putExtra("button_msg", "提交建议");
                    startActivity(intent);
                }
                break;
        }
    }
    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment)
    {
        this.mBackHandedFragment = selectedFragment;
    }
    @Override
    public void onBackPressed()
    {
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed())
        {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            {
                finish();
            } else
            {
                getSupportFragmentManager().popBackStack(); //fragment 出栈  
            }
        }
    }
}
