package com.outsource.changnanguoshui.activity;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.application.MyApplication;
import com.outsource.changnanguoshui.bean.GetPartyInfoBean;
import com.outsource.changnanguoshui.bean.GetPunchListBean;
import com.outsource.changnanguoshui.utlis.CircleTransform;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/13.
 */

public class MemberInformationActivity extends BaseActivity implements OnDateSetListener
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
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.history_list)
    RecyclerView historyList;
    MyAdapter adapter;
    private String user_id;
    List<GetPunchListBean.ListBean> mData;
    TimePickerDialog tiemDialog;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_member_information);
    }

    @Override
    protected void initData()
    {
        tiemDialog = new TimePickerDialog.Builder()
                .setTitleStringId("请选择月份")//标题
                .setWheelItemTextSelectorColor(ContextCompat.getColor(this, R.color.red))//当前文本颜色
                .setType(Type.YEAR_MONTH)
                .setCallBack(this)
                .build();

        title.setText(getIntent().getStringExtra("title"));
        mData = new ArrayList<>();
        user_id = getIntent().getStringExtra("user_id");
        historyList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, R.layout.item_history, mData);
        historyList.setAdapter(adapter);
        getData();
        getPunchList(new DateTime().toString("M"));
    }


    @OnClick({R.id.back, R.id.search_ll})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.search_ll:
                tiemDialog.show(getSupportFragmentManager(), "YEAR_MONTH_DAY");
                break;
        }
    }


    class MyAdapter extends CommonBaseAdapter<GetPunchListBean.ListBean>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<GetPunchListBean.ListBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, GetPunchListBean.ListBean item, int position)
        {
            holder.setText(R.id.date_str, item.getDate_str());
            holder.setText(R.id.time1, item.getTime1());
            holder.setText(R.id.time1_status, item.getTime1_status());
            holder.setText(R.id.time2, item.getTime2());
            holder.setText(R.id.time2_status, item.getTime2_status());
            holder.setBackground(R.id.time1_status, getBac(item.getTime1_status()));
            holder.setBackground(R.id.time2_status, getBac(item.getTime2_status()));
        }

    }


    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.ACT, "GetPartyInfo")
                .addParams("user_id", user_id)
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
                            videoTime.setText(TextUtils.isEmpty(response.getVideo_time()) ? "0分钟" : response.getVideo_time() + "分钟");
                            articleCount.setText(response.getArticle_count() + "篇");
                            Picasso.with(MyApplication.getInstance())
                                    .load(Constant.DOMAIN_NAME + response.getPic_url())
                                    .placeholder(R.mipmap.male_head)
                                    .transform(new CircleTransform())
                                    .into(infoIcon);
                        }
                    }
                });
    }

    private void getPunchList(String month)
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.ACT, "GetPunchList")
                .addParams("user_id", user_id)
                .addParams("month", month)
                .build()
                .execute(new GenericsCallback<GetPunchListBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(GetPunchListBean response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            mData.addAll(response.getList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private int getBac(String status)
    {
        int bac = R.drawable.green_bg;
        switch (status)
        {
            case "正常":
                bac = R.drawable.green_bg;
                break;
            case "迟到":
                bac = R.drawable.chidao_bg;
                break;
            case "请假":
                bac = R.drawable.qingjia_bg;
                break;
            case "漏签":
                bac = R.drawable.louqina_bg;
                break;
            case "早退":
                bac = R.drawable.zaotui_bg;
                break;
        }
        return bac;
    }

    //选择时间回调
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds)
    {
        getPunchList(new DateTime(millseconds).toString("M"));
    }
}
