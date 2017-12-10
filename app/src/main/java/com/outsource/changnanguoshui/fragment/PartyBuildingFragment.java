package com.outsource.changnanguoshui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseFragment;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PartyBuildingFragment extends BaseFragment
{
    int type;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            type = bundle.getInt("type");
        }
    }

    public static Fragment newInstance(int type)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        PartyBuildingFragment fragment = new PartyBuildingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {

    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_party_building;
    }

    @Override
    protected void initData()
    {

    }
}
