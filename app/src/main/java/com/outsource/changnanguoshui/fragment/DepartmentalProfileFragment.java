package com.outsource.changnanguoshui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/13.
 */

public class DepartmentalProfileFragment extends BaseFragment
{
    @BindView(R.id.content_dp)
    TextView contentDp;
    private String mData;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            mData = bundle.getString("mData");
        }
    }

    public static Fragment newInstance(String mData)
    {
        Bundle bundle = new Bundle();
        bundle.putString("mData", mData);
        DepartmentalProfileFragment fragment = new DepartmentalProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        contentDp.setText(TextUtils.isEmpty(mData) ? "" : mData);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_departmental_profile;
    }

    @Override
    protected void initData()
    {

    }


}
