package com.outsource.changnanguoshui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.bean.CheckApkUpgradeBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 显示更新提示
 *
 * @author Administrator
 */
public class ShowUpdateActivity extends Activity
{

    private CheckApkUpgradeBean mData;
    @BindView(R.id.version_name)
    TextView versionName;
    @BindView(R.id.updata_message)
    TextView updataMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        ButterKnife.bind(this);
        initData();
    }


    private void initData()
    {
        mData = (CheckApkUpgradeBean) getIntent().getSerializableExtra("mData");
        versionName.setText("最新版本：" + mData.getVer());
        updataMessage.setText(mData.getIntro());

    }

    /**
     * 开始更新操作
     */
    public void doUpdate()
    {
        Intent intent = new Intent(ShowUpdateActivity.this, doUpdateActivity.class);
        intent.putExtra("url", mData.getDownurl());
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.updata_confirm, R.id.updata_cancel})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.updata_confirm:
                doUpdate();
                break;
            case R.id.updata_cancel:
                finish();
                break;
        }
    }


}
