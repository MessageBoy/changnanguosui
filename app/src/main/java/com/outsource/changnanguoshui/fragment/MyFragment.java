package com.outsource.changnanguoshui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.AdministrativeManagementActivity;
import com.outsource.changnanguoshui.activity.MemberInformationActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.GetMyBean;
import com.outsource.changnanguoshui.bean.HomeBean;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/4.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.my_list)
    RecyclerView myList;
    String[] title = {"在线缴费", "缴费查询", "在线学习", "我的信息", "线上活动", "设置"};
    int[] icon = {R.mipmap.zxjf_m, R.mipmap.jfcx_m, R.mipmap.zxxx_m, R.mipmap.wdxx_m, R.mipmap.xshd_m, R.mipmap.sz};
    MyAdapter homeAdapter;
    @BindView(R.id.user_head)
    ImageView userHead;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_info)
    TextView userInfo;
    @BindView(R.id.wwcxx)
    TextView wwcxx;
    @BindView(R.id.xxjd)
    TextView xxjd;
    @BindView(R.id.wdsc)
    TextView wdsc;
    Unbinder unbinder;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        myList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        myList.addItemDecoration(new ItemDivider().setDividerWith(2).setDividerColor(ContextCompat.getColor(getActivity(), R.color.div)));
        homeAdapter = new MyAdapter(getActivity(), R.layout.item_personal, setHomeData());
        myList.setAdapter(homeAdapter);
        homeAdapter.setItemListener(new CommonBaseAdapter.onItemClickerListener() {
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

                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initData()
    {
        getData();
    }


    private List<HomeBean> setHomeData() {
        List<HomeBean> data = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            data.add(new HomeBean(icon[i], title[i]));
        }
        return data;
    }

    @OnClick({R.id.user_head, R.id.my_study, R.id.content_query})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.user_head:
                break;
            case R.id.my_study:
                break;
            case R.id.content_query:
                break;
        }
    }

    class MyAdapter extends CommonBaseAdapter<HomeBean>
    {
        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<HomeBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, HomeBean item, int position) {

            holder.setImageResource(R.id.icon_personal, item.getIcon());
            holder.setText(R.id.title_personal, item.getTitle());
        }
    }


    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetMy")
                .build()
                .execute(new GenericsCallback<GetMyBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {

                    }

                    @Override
                    public void onResponse(GetMyBean response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            wwcxx.setText(response.getStudy_unfinish() + "");
                            wdsc.setText(response.getFavorite_num() + "");
                            xxjd.setText(response.getStudy_plan() + "");
                            userName.setText(response.getReal_name());
                        }
                    }
                });
    }
}
