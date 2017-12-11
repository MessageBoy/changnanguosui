package com.outsource.changnanguoshui.activity.ShuiQiHuDong;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/5.
 */

public class ConsultMsgActivity extends BaseActivity {

    @BindView(R.id.back)
    Button back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.submit)
    Button submit;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_consult_msg);
    }

    @Override
    protected void initData() {
        title.setText("咨询留言");
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
