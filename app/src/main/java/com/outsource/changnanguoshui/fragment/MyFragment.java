package com.outsource.changnanguoshui.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.AdministrativeManagementActivity;
import com.outsource.changnanguoshui.activity.MemberInformationActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.HomeBean;
import com.outsource.changnanguoshui.utlis.ItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/4.
 */

public class MyFragment extends BaseFragment
{
    @BindView(R.id.my_list)
    RecyclerView myList;
    String[] title = {"在线缴费", "缴费查询", "在线学习", "我的信息", "线上活动", "设置"};
    int[] icon = {R.mipmap.zxjf_m, R.mipmap.jfcx_m, R.mipmap.zxxx_m, R.mipmap.wdxx_m, R.mipmap.xshd_m, R.mipmap.sz};
    MyAdapter homeAdapter;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        myList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        myList.addItemDecoration(new ItemDivider().setDividerWith(2).setDividerColor(ContextCompat.getColor(getActivity(), R.color.div)));
        homeAdapter = new MyAdapter(getActivity(), R.layout.item_personal, setHomeData());
        myList.setAdapter(homeAdapter);
        homeAdapter.setItemListener(new CommonBaseAdapter.onItemClickerListener()
        {
            @Override
            public void onItemClick(View view, Object data, int position)
            {
                Intent intent;
                switch (position)
                {
                    case 3:
                        startActivity(MemberInformationActivity.class);
                        break;
                    case 4:
                        intent = new Intent(getActivity(), AdministrativeManagementActivity.class);
                        intent.putExtra("position", Constant.Two);
                        startActivity(intent);
                        break;
                    case 5:
                        try
                        {
                            intent = new Intent();
                            ComponentName cn = new ComponentName("com.geostar.futian", "com.geostar.futian.activity.SplashActivity");
                            intent.setComponent(cn);
                            intent.putExtra("userid", "chenfang");
                            intent.putExtra("password", "888888");
                            intent.setAction("android.intent.action.MAIN");
                            startActivity(intent);
                        } catch (Exception e)
                        {
                            Log.e("11", e.getMessage());
                            Alert(e.getMessage());
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_my;
    }

    @Override
    protected void initData()
    {

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

    class MyAdapter extends CommonBaseAdapter<HomeBean>
    {
        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<HomeBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, HomeBean item, int position)
        {

            holder.setImageResource(R.id.icon_personal, item.getIcon());
            holder.setText(R.id.title_personal, item.getTitle());
        }
    }
}
