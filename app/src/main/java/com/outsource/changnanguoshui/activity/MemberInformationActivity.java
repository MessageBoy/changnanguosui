package com.outsource.changnanguoshui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.application.MyApplication;
import com.outsource.changnanguoshui.bean.GetPartyInfoBean;
import com.outsource.changnanguoshui.utlis.CircleTransform;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/13.
 */

public class MemberInformationActivity extends BaseActivity
{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.info_icon)
    ImageView infoIcon;
    @BindView(R.id.info_name)
    TextView infoName;
    @BindView(R.id.video_time)
    TextView videoTime;
    @BindView(R.id.article_count)
    TextView articleCount;
    @BindView(R.id.point)
    TextView point;
    @BindView(R.id.position)
    TextView position;
    @BindView(R.id.grjf)
    TextView grjf;
    @BindView(R.id.party_job)
    TextView partyJob;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.party_dep_name)
    TextView partyDepName;
    @BindView(R.id.party_birthday)
    TextView partyBirthday;
    @BindView(R.id.duty)
    TextView duty;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_member_information);
    }

    @Override
    protected void initData()
    {
        title.setText(getIntent().getStringExtra("title"));
        getData();
    }


    @OnClick(R.id.back)
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;

        }
    }


    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.ACT, "GetPartyInfo")
                .addParams(Constant.USER_ID, getIntent().getStringExtra("user_id"))
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .build()
                .execute(new GenericsCallback<GetPartyInfoBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(GetPartyInfoBean response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            infoName.setText(response.getReal_name());
                            position.setText(response.getParty_job());
                            point.setText(response.getPoint() + "");
                            videoTime.setText(TextUtils.isEmpty(response.getVideo_time()) ? "0钟" : response.getVideo_time() + "钟");
                            articleCount.setText(response.getArticle_count() + "篇");
                            Picasso.with(MyApplication.getInstance())
                                    .load(Constant.DOMAIN_NAME + response.getPic_url())
                                    .placeholder(R.mipmap.male_head)
                                    .transform(new CircleTransform())
                                    .into(infoIcon);
                            partyJob.setText(response.getDep_name());
                            sex.setText(response.getSex());
                            age.setText(response.getAge());
                            partyDepName.setText(response.getParty_dep_name());
                            partyBirthday.setText(response.getParty_birthday());
                            duty.setText(response.getDuty());
                        }
                    }
                });
    }

}
