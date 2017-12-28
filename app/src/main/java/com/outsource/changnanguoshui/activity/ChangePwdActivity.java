package com.outsource.changnanguoshui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.SuccessBean;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by Administrator on 2017/12/17.
 */

public class ChangePwdActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.dqmm_am)
    EditText dqmmAm;
    @BindView(R.id.xmm_am)
    EditText xmmAm;
    @BindView(R.id.zcsrmm_am)
    EditText zcsrmmAm;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_pwd);
    }

    @Override
    protected void initData() {
        title.setText("密码修改");
    }

    private void modifyPassword() {
        post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "EditMyPass")
                .addParams("old_pass", dqmmAm.getText().toString())
                .addParams("new_pass", xmmAm.getText().toString())
                .addParams("new_pass2", zcsrmmAm.getText().toString())
                .build()
                .execute(new GenericsCallback<SuccessBean>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(SuccessBean response, int id) {
                        Alert(response.getMsg());
                        if (response.getStatus() == 1) {
                            dqmmAm.setText("");
                            xmmAm.setText("");
                            zcsrmmAm.setText("");
                        }
                    }
                });
    }

    @OnClick({R.id.back, R.id.xgmm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.xgmm:
                modifyPassword();
                break;
        }
    }
}
