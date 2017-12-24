package com.outsource.changnanguoshui.activity;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.GetMyStudyPlanBean;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/17.
 */

public class LearningProgressActivity extends BaseActivity implements OnDateSetListener
{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.year_plan)
    TextView yearPlan;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.total_integral)
    TextView totalIntegral;
    @BindView(R.id.completed_integral)
    TextView completedIntegral;
    MyAdapter adapter;
    List<GetMyStudyPlanBean.ListBean> mData;
    TimePickerDialog tiemDialog;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_earning_progress);
        tiemDialog = new TimePickerDialog.Builder()
                .setTitleStringId("请选择年份")//标题
                .setWheelItemTextSelectorColor(ContextCompat.getColor(this, R.color.red))//当前文本颜色
                .setType(Type.YEAR)
                .setCallBack(this)
                .build();
    }

    @Override
    protected void initData()
    {
        title.setText("学习进度");
        year.setText(new DateTime().toString("yyyy"));
        mData = new ArrayList<>();
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.addItemDecoration(new ItemDivider().setDividerWith(2));
        adapter = new MyAdapter(this, R.layout.item_progress, mData);
        recycleview.setAdapter(adapter);
        getData();

    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetMyStudyPlan")
                .addParams("syear", year.getText().toString())
                .build()
                .execute(new GenericsCallback<GetMyStudyPlanBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(GetMyStudyPlanBean response, int id)
                    {

                        mData.clear();
                        mData.addAll(response.getList());
                        adapter.notifyDataSetChanged();
                        yearPlan.setText(response.getSyear() + "年学习计划");
                        totalIntegral.setText("总计划：" + response.getPlan_point() + "积分");
                        completedIntegral.setText("已完成：" + response.getFinish_point() + "积分");
                    }
                });
    }


    @OnClick({R.id.back, R.id.choice_year})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.choice_year:
                tiemDialog.show(getSupportFragmentManager(), "YEAR_MONTH_DAY");
                break;
        }
    }

    class MyAdapter extends CommonBaseAdapter<GetMyStudyPlanBean.ListBean>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<GetMyStudyPlanBean.ListBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, GetMyStudyPlanBean.ListBean item, int position)
        {
            holder.setText(R.id.year_month, item.getLearn_month());
            holder.setText(R.id.get_integral, "学习：" + item.getCount_point() + "积分");
            holder.setText(R.id.target_integral, "目标：" + item.getPoint() + "积分");
        }
    }

    //选择时间回调
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds)
    {
        year.setText(new DateTime(millseconds).toString("yyyy"));
        getData();
    }
}
