package com.outsource.changnanguoshui.activity;

import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/10.
 */

public class VoteActivity extends BaseActivity
{
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void initView()
    {

        setContentView(R.layout.activity_vote);
    }

    @Override
    protected void initData()
    {
        title.setText("投票");

    }

    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }
}
