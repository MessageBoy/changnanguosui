package com.outsource.changnanguoshui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.SuccessBean;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/1/15.
 */

public class IwantLeaveActivty extends BaseActivity implements OnDateSetListener
{
    TimePickerDialog tiemDialog;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.wyqj)
    TextView wyqj;
    @BindView(R.id.kssj)
    TextView kssj;
    @BindView(R.id.jssj)
    TextView jssj;
    @BindView(R.id.qjyy)
    EditText qjyy;
    @BindView(R.id.spr)
    TextView spr;
    String to_user_id;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_i_want_leave);
    }

    @Override
    protected void initData()
    {
        showTiemDialog();
    }


    @OnClick({R.id.back, R.id.kssj_ll, R.id.jssj_ll, R.id.spr_ll, R.id.submit})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.kssj_ll:
                tiemDialog.show(this.getSupportFragmentManager(), "kssj");
                break;
            case R.id.jssj_ll:
                tiemDialog.show(this.getSupportFragmentManager(), "jssj");
                break;
            case R.id.spr_ll:
                Intent intent = new Intent(this, ExpandableActivity.class);
                startActivityForResult(intent, 123);
                break;
            case R.id.submit:
                postLeave();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK && requestCode == 123 && data != null)
        {
            to_user_id = data.getStringExtra("user_id");
            spr.setText(data.getStringExtra("user_name"));
        }
    }

    private void showTiemDialog()
    {
        tiemDialog = new TimePickerDialog.Builder()
                .setTitleStringId("请选择时间")//标题
                .setWheelItemTextSelectorColor(ContextCompat.getColor(this, R.color.red))//当前文本颜色
                .setType(Type.ALL)
                .setCallBack(this)
                .build();
    }

    //选择时间回调
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds)
    {

        switch (timePickerView.getTag())
        {
            case "kssj":
                kssj.setText(new DateTime(millseconds).toString("yyyy-MM-dd HH:mm:ss"));
                break;
            case "jssj":
                jssj.setText(new DateTime(millseconds).toString("yyyy-MM-dd HH:mm:ss"));
                break;
        }
    }

    private void postLeave()
    {

        String begin_time = kssj.getText().toString();
        if (TextUtils.isEmpty(begin_time))
        {
            Alert("请选择开始时间");
            return;
        }
        String end_time = jssj.getText().toString();
        if (TextUtils.isEmpty(end_time))
        {
            Alert("请选择结束时间");
            return;
        }
        String intro = qjyy.getText().toString();
        if (TextUtils.isEmpty(intro))
        {
            Alert("请输入请假原因");
            return;
        }

        if (TextUtils.isEmpty(to_user_id))
        {
            Alert("请选择审批人");
            return;
        }
        AlertDialog();
        OkHttpUtils
                .post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "SaveVacate")
                .addParams("begin_time", begin_time)
                .addParams("intro", intro)
                .addParams("to_user_id", to_user_id)
                .addParams("end_time", end_time)
                .build()
                .execute(new GenericsCallback<SuccessBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        dialog.dismiss();
                        Alert("网络请求出错");
                    }

                    @Override
                    public void onResponse(SuccessBean response, int id)
                    {
                        dialog.dismiss();
                        Alert(response.getMsg());
                        if (response.getStatus() == 1)
                        {
                            finish();
                        }
                    }
                });
    }

}
