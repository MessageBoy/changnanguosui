package com.outsource.changnanguoshui.activity;

import android.view.View;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/17.
 */

public class AccountMaintenanceActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_account_maintenance);
    }

    @Override
    protected void initData() {
        title.setText("账号维护");
    }

    @OnClick({R.id.back, R.id.change_msg, R.id.change_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.change_msg:
                startActivity(BasicInformationActivity.class);
                break;
            case R.id.change_pwd:
                startActivity(ChangePwdActivity.class);
                break;
        }
    }
}
