package com.outsource.changnanguoshui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.TabAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.GetCategorysBean;
import com.outsource.changnanguoshui.fragment.PartyBuildingFragment;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
        getData();
    }


    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }

    private void getData() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams("channel_id", "" + 16)
                .addParams(Constant.ACT, "GetCategorys")
                .build()
                .execute(new GenericsCallback<GetCategorysBean>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(GetCategorysBean response, int id) {
                        if (response.getStatus() == 1) {
                            for (GetCategorysBean.ListBean data : response.getList()) {
                                mPageTitleList.add(data.getTitle());
                                mFragmentList.add(new PartyBuildingFragment().newInstance(data.getId()));
                            }
                            mAdapter = new TabAdapter(PartyBuildingActivity.this, getSupportFragmentManager(),
                                    mFragmentList, mPageTitleList);
                            viewPager.setAdapter(mAdapter);
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    }
                });
    }

}
