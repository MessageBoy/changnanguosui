package com.outsource.changnanguoshui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.GetMyInfo;
import com.outsource.changnanguoshui.bean.SuccessBean;
import com.outsource.changnanguoshui.utlis.CircleTransform;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by Administrator on 2017/12/17.
 */

public class AccountMaintenanceActivity extends BaseActivity
{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.icon_am)
    ImageView iconAm;
    @BindView(R.id.xing_am)
    EditText xingAm;
    @BindView(R.id.ming_am)
    EditText mingAm;
    @BindView(R.id.email_am)
    EditText emailAm;
    @BindView(R.id.phone_am)
    EditText phoneAm;
    @BindView(R.id.company_am)
    EditText companyAm;
    @BindView(R.id.dqmm_am)
    EditText dqmmAm;
    @BindView(R.id.xmm_am)
    EditText xmmAm;
    @BindView(R.id.zcsrmm_am)
    EditText zcsrmmAm;
    @BindView(R.id.branch_am)
    EditText branchAm;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_account_maintenance);
    }

    @Override
    protected void initData()
    {
        title.setText("账号维护");
        getData();
    }


    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetMyInfo")
                .build()
                .execute(new GenericsCallback<GetMyInfo>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(GetMyInfo response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            Picasso.with(AccountMaintenanceActivity.this)
                                    .load(Constant.DOMAIN_NAME + response.getPic_url())
                                    .placeholder(R.mipmap.male_head)
                                    .transform(new CircleTransform())
                                    .into(iconAm);
                            xingAm.setText(response.getReal_name());
                            mingAm.setText(response.getSex());
                            phoneAm.setText(response.getMobile());
                            emailAm.setText(response.getEmail());
                            companyAm.setText(response.getDep_name());
                            branchAm.setText(response.getDeplist().get(0).getDep_name());
                        }
                    }
                });
    }


    @OnClick({R.id.back, R.id.xgxx, R.id.xgmm})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.xgxx:
                modifyInfo();
                break;
            case R.id.xgmm:
                modifyPassword();
                break;
        }
    }

    private void modifyPassword()
    {
        post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "EditMyPass")
                .addParams("old_pass", dqmmAm.getText().toString())
                .addParams("new_pass", xmmAm.getText().toString())
                .addParams("new_pass2", zcsrmmAm.getText().toString())
                .build()
                .execute(new GenericsCallback<SuccessBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(SuccessBean response, int id)
                    {
                        Alert(response.getMsg());
                        if (response.getStatus() == 1)
                        {
                            dqmmAm.setText("");
                            xmmAm.setText("");
                            zcsrmmAm.setText("");
                        }
                    }
                });
    }

    private void modifyInfo()
    {
        OkHttpUtils
                .post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "SaveMyInfo")
                .addParams("pic_path", "")
                .addParams("dep_id", "")
                .addParams("real_name", xingAm.getText().toString())
                .addParams("sex", mingAm.getText().toString())
                .addParams("id_card", "")
                .addParams("birthday", "")
                .addParams("telephone", "")
                .addParams("mobile", phoneAm.getText().toString())
                .addParams("email", emailAm.getText().toString())
                .build()
                .execute(new GenericsCallback<SuccessBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(SuccessBean response, int id)
                    {
                        Alert(response.getMsg());
                        if (response.getStatus() == 1)
                        {
                            dqmmAm.setText("");
                            xmmAm.setText("");
                            zcsrmmAm.setText("");
                        }
                    }
                });
    }
}
