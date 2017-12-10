package com.outsource.changnanguoshui.fragment.ShuiQiHuDong;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/5.
 */
public class LZReportFragment extends BaseFragment {
    int type;
    Unbinder unbinder;
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
        LZReportFragment fragment = new LZReportFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report;
    }

    @Override
    protected void initData() {
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
}
