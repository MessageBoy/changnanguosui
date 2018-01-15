package com.outsource.changnanguoshui;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.LoginBen;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;

import static com.outsource.changnanguoshui.Constant.TOKEN;

public class LoginActivity extends BaseActivity
{


    @BindView(R.id.login_submit)
    Button loginSubmit;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.user_pwd)
    EditText userPwd;
    private static final int MSG_SET_ALIAS = 1001;
    private String TAG = MainActivity.class.getName();
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
                            SpUtils.setParam(LoginActivity.this, TOKEN, response.getToken());
                            SpUtils.setParam(LoginActivity.this, Constant.USER_ID, response.getUser_id());
                            SpUtils.setParam(LoginActivity.this, Constant.IsLogin, true);
                            //注册极光推送的别名
                            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, response.getUser_id().toString()));
                            startActivity(MainActivity.class);
                            finish();
                        } else
                        {
                            Alert("登录异常，请重新登录");
                        }
                    }
                });
    }

    private final Handler mHandler = new Handler()
    {//专门用了一个Handler对象处理别名的注册问题
        @Override
        public void handleMessage(android.os.Message msg)
        {
            super.handleMessage(msg);
            Log.d(TAG, "设置极光推送的别名-mHandler2");
            JPushInterface.setAliasAndTags(getApplicationContext(),
                    (String) msg.obj, null, mAliasCallback);
        }
    };

    //注册别名回调
    private final TagAliasCallback mAliasCallback = new TagAliasCallback()
    {
        @Override
        public void gotResult(int code, String alias, Set<String> tags)
        {
            String logs;
            switch (code)
            {
                case 0:
                    logs = "极光推送别名设置成功";
                    Log.i(TAG, logs);
                    break;
                case 6002:
                    logs = "极光推送别名设置失败，60秒后重试";
                    Log.i(TAG, logs);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e(TAG, logs);
                    break;
            }
        }
    };
}
