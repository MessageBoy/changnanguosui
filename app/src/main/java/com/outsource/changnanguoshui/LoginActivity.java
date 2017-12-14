package com.outsource.changnanguoshui;

import android.os.Bundle;
import android.widget.Button;

import com.outsource.changnanguoshui.application.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_submit)
    Button loginSubmit;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_submit)
    public void onViewClicked() {
        startActivity(MainActivity.class);
    }
}
