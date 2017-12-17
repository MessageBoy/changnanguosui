package com.outsource.changnanguoshui.activity.ShuiQiHuDong;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.ConsultMsgBean;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.PhoneValidation;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/5.
 */

public class ConsultMsgActivity extends BaseActivity {

    int category_id;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_content)
    EditText editContent;
    private String but_text;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_consult_msg);
    }

    @Override
    protected void initData() {
        title.setText(getIntent().getStringExtra("activityTitle"));
        category_id = getIntent().getIntExtra("category_id", 1);
        editContent.setHint(getIntent().getStringExtra("content_hint"));
        but_text = getIntent().getStringExtra("button_msg");
        submit.setText(but_text);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @OnClick({R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (TextUtils.isEmpty(editPhone.getText().toString())) {
                    Alert("手机号不能为空");
                } else if (PhoneValidation.isMobileNO(editPhone.getText().toString()) == false) {
                    Alert("请输入正确的手机号");
                } else if (TextUtils.isEmpty(editContent.getText().toString())) {
                    Alert("提交内容不能为空");
                } else {
                    submitData();
                }

                break;
        }
    }


    private void submitData() {
        OkHttpUtils
                .post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getApplicationContext(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getApplicationContext(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "AddInform")
                .addParams("category_id", "" + category_id)
                .addParams("title", editPhone.getText().toString())
                .addParams("content", editContent.getText().toString())
                .build()
                .execute(new GenericsCallback<ConsultMsgBean>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络连接异常");
                    }
                    @Override
                    public void onResponse(ConsultMsgBean response, int id) {
                        Alert(response.getMsg());
                        if(response.getStatus()==1){
                            finish();
                        }
                    }
                });
        }
}
