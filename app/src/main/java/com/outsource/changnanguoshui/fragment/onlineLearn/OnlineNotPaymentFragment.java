package com.outsource.changnanguoshui.fragment.onlineLearn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.onlineLearn.NotPaymentAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/5.
 */
public class OnlineNotPaymentFragment extends BaseFragment {
    int type;
    NotPaymentAdapter notPaymentAdapter;
    @BindView(R.id.recycle_online)
    RecyclerView recycleOnline;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
    }

    public static Fragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        OnlineNotPaymentFragment fragment = new OnlineNotPaymentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_not_online_payment;
    }

    @Override
    protected void initData() {
        getData();
        recycleOnline.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleOnline.addItemDecoration(new ItemDivider().setDividerColor(R.color.div));
        notPaymentAdapter = new NotPaymentAdapter(getActivity(), setOnlineData());
        recycleOnline.setAdapter(notPaymentAdapter);
    }

    private void getData() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetMyPayLog")
                .addParams("flag", ""+type)
                .build()
                .execute(new GenericsCallback<String>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }
    private List<String> setOnlineData()
    {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            data.add("1");
        }
        return data;
    }

}
