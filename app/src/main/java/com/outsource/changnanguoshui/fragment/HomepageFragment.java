package com.outsource.changnanguoshui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.PartyBuildingActivity;
import com.outsource.changnanguoshui.activity.ShuiQiHuDong.ShuiQiHDActivity;
import com.outsource.changnanguoshui.activity.onlineLearn.OnlineLearnActivity;
import com.outsource.changnanguoshui.activity.onlineLearn.OnlinePaymentActivity;
import com.outsource.changnanguoshui.activity.taxBusiness.TaxBusinessActivity;
import com.outsource.changnanguoshui.adapter.HomeAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.bean.HomeBean;
import com.outsource.changnanguoshui.utlis.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/4.
 */

public class HomepageFragment extends BaseFragment
{
    @BindView(R.id.time_home)
    TextView timeHome;
    @BindView(R.id.modular_list)
    RecyclerView modularList;
    @BindView(R.id.information_list)
    RecyclerView informationList;

    String[] title = {"党员信息", "党建风采", "在线党费", "在线学习", "风险推送", "文件查询", "通知公告", "在线打卡", "线上活动", "税企互动", "廉政举报", "账号维护"};
    int[] icon = {R.mipmap.dyxx, R.mipmap.djfc, R.mipmap.zxdf, R.mipmap.zxxx, R.mipmap.fxts, R.mipmap.wjcx, R.mipmap.tzgg, R.mipmap.zxdk, R.mipmap.xshd, R.mipmap.sqhd, R.mipmap.lzjb, R.mipmap.zhwh};
    HomeAdapter homeAdapter;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        timeHome.setText(DateUtils.getSystemTime());
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initData()
    {
        modularList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        informationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeAdapter = new HomeAdapter(getActivity(), setHomeData());
        modularList.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                switch (position)
                {
                    case 1:
                        startActivity(PartyBuildingActivity.class);
                        break;
                    case 2:
                        startActivity(OnlinePaymentActivity.class);
                        break;
                    case 3:
                        startActivity(OnlineLearnActivity.class);
                        break;
                    case 4:
                        startActivity(TaxBusinessActivity.class);
                        break;
                    case 5:
                        startActivity(TaxBusinessActivity.class);
                        break;
                    case 9:
                        startActivity(ShuiQiHDActivity.class);
                        break;
                    case 10:
                        startActivity(ShuiQiHDActivity.class);
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position)
            {

            }
        });
    }


    @OnClick({R.id.time_home, R.id.search_home})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.time_home:
                break;
            case R.id.search_home:
                break;
        }
    }

    private List<HomeBean> setHomeData()
    {
        List<HomeBean> data = new ArrayList<>();
        for (int i = 0; i < title.length; i++)
        {
            data.add(new HomeBean(icon[i], title[i]));
        }
        return data;
    }
}
