package com.outsource.changnanguoshui;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.LoginBen;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.outsource.changnanguoshui.Constant.IsLogin;

public class LoginActivity extends BaseActivity
{


    @BindView(R.id.login_submit)
    Button loginSubmit;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.user_pwd)
    EditText userPwd;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initData()
    {
        Object isLogin = SpUtils.getParam(getApplicationContext(), Constant.IsLogin, false);

        if (isLogin.equals(true))
        {
            startActivity(MainActivity.class);
            finish();
        }
    }

    @OnClick(R.id.login_submit)
    public void onViewClicked()
    {

        if (TextUtils.isEmpty(userName.getText().toString()))
        {
            Alert("账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(userPwd.getText().toString()))
        {
            Alert("密码不能为空");
            return;
        }
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams("username", userName.getText().toString())
                .addParams("userpass", userPwd.getText().toString())
                .addParams("device_type", "Android")
                .addParams("device_id", "1")
                .addParams(Constant.ACT, "Login")
                .build()
                .execute(new GenericsCallback<LoginBen>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("登录异常，请重新登录");
                    }

                    @Override
                    public void onResponse(LoginBen response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            SpUtils.setParam(getApplicationContext(), Constant.TOKEN, response.getToken());
                            SpUtils.setParam(getApplicationContext(), Constant.USER_ID, response.getUser_id());
                            SpUtils.setParam(getApplicationContext(), IsLogin, true);
                            startActivity(MainActivity.class);
                            finish();
                        } else
                        {
                            Alert("登录异常，请重新登录");
                        }
                    }
                });


    }

}
