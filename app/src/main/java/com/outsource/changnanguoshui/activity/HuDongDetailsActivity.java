package com.outsource.changnanguoshui.activity;

import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.HuDongDetailsBean;
import com.outsource.changnanguoshui.utlis.DateUtils;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/15.
 */

public class HuDongDetailsActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.data)
    TextView data;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.reply_user)
    TextView replyUser;
    @BindView(R.id.reply_content)
    TextView replyContent;
    @BindView(R.id.reply_time)
    TextView replyTime;
    private String id;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_hudong_details);
    }

    @Override
    protected void initData() {
        title.setText("详情");
        id = getIntent().getStringExtra("id");
        getData();
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    private void getData() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getApplicationContext(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getApplicationContext(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetInformInfo")
                .addParams("id", id)
                .build()
                .execute(new GenericsCallback<HuDongDetailsBean>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(HuDongDetailsBean response, int id) {
                        if (response.getStatus() == 1) {
                            textView.setText(response.getTitle());
                            data.setText(DateUtils.getDate(response.getAdd_time()));
                            content.setText(response.getContent());
                            replyUser.setText(response.getReply_user());
                            replyContent.setText(response.getReply_content());
                            replyTime.setText(DateUtils.getDate(response.getReply_time()));
                        }
                    }
                });
    }
}
