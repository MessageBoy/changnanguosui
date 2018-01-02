package com.outsource.changnanguoshui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.outsource.changnanguoshui.ActivityCollector;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.LoginActivity;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.CheckApkUpgradeBean;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.outsource.changnanguoshui.Constant.ABOUT_US;

/**
 * Created by Administrator on 2017/12/17.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.banben)
    TextView banben;
    int vCode = 1;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initData() {
        title.setText("账号维护");
        try {
            PackageInfo packages = getPackageManager().getPackageInfo("com.northdoo.luohu", PackageManager.GET_CONFIGURATIONS);
            vCode = packages.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        banben.setText("当前版本V"+vCode);
    }

    @OnClick({R.id.back, R.id.gydj, R.id.jc_update, R.id.return_login})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.gydj:
                intent=new Intent(getApplicationContext(),AboutUsActivity.class);
                intent.putExtra("activityTitle","关于党建");
                intent.putExtra("webUrl",ABOUT_US);
                startActivity(intent);
                break;
            case R.id.jc_update:
                getData();
                break;
            case R.id.return_login:
                SpUtils.setParam(getApplicationContext(), Constant.IsLogin, false);
                SpUtils.setParam(getApplicationContext(), Constant.TOKEN, "");
                SpUtils.setParam(getApplicationContext(), Constant.USER_ID, "");
                ActivityCollector.finishAll();
                intent = new Intent();
                intent.setClass(SettingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }


    private void getData() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams("plat", 1 + "")
                .addParams(Constant.ACT, "CheckApkUpgrade")
                .build()
                .execute(new GenericsCallback<CheckApkUpgradeBean>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(CheckApkUpgradeBean response, int id) {
                        if (response.getStatus() == 1) {
                            if (vCode < response.getVcode()) {
                                Intent intent = new Intent(SettingActivity.this, ShowUpdateActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("mData", response);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    }
                });

    }

}
