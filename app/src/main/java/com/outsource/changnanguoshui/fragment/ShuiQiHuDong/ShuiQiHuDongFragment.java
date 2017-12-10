package com.outsource.changnanguoshui.fragment.ShuiQiHuDong;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.ShuiQiHuDong.ConsultMsgActivity;
import com.outsource.changnanguoshui.adapter.ShuiQiHuDong.SuiQiAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/5.
 */
public class ShuiQiHuDongFragment extends BaseFragment {
    int type;
    Unbinder unbinder;
    SuiQiAdapter siQiAdapter;
    @BindView(R.id.recycle_online)
    RecyclerView recycleOnline;
    @BindView(R.id.add_zixun)
    Button addZixun;

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
        ShuiQiHuDongFragment fragment = new ShuiQiHuDongFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sui_qi;
    }

    @Override
    protected void initData() {
        recycleOnline.setLayoutManager(new LinearLayoutManager(getActivity()));
        siQiAdapter = new SuiQiAdapter(getActivity(), setOnlineData());
        recycleOnline.setAdapter(siQiAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private List<String> setOnlineData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("1");
        }
        return data;
    }

    @OnClick(R.id.add_zixun)
    public void onViewClicked() {

        startActivity(ConsultMsgActivity.class);

    }
}
