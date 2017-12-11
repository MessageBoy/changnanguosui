package com.outsource.changnanguoshui.fragment.onlineLearn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.onlineLearn.OnlineAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/5.
 */
public class OnlineLearnFragment extends BaseFragment {
    int type;
    Unbinder unbinder;
    OnlineAdapter onlineAdapter;
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
        OnlineLearnFragment fragment = new OnlineLearnFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_online_learn;
    }

    @Override
    protected void initData() {
        recycleOnline.setLayoutManager(new LinearLayoutManager(getActivity()));
        onlineAdapter = new OnlineAdapter(getActivity(), setOnlineData());
        recycleOnline.setAdapter(onlineAdapter);
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
