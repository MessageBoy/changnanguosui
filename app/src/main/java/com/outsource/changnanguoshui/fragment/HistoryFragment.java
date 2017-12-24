package com.outsource.changnanguoshui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.GetPunchListBean;
import com.outsource.changnanguoshui.utlis.DateUtils;
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
 * Created by Administrator on 2017/12/9.
 */

public class HistoryFragment extends BaseFragment implements OnDateSetListener
{
    @BindView(R.id.tab_layout)
    LinearLayout tab_layout;
    @BindView(R.id.history_list)
    RecyclerView historyList;
    MyAdapter adapter;
    List<GetPunchListBean.ListBean> mData;
    TimePickerDialog tiemDialog;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        mData = new ArrayList<>();
        historyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyList.addItemDecoration(new ItemDivider());
        adapter = new HistoryFragment.MyAdapter(getActivity(), R.layout.item_text, mData);
        historyList.setAdapter(adapter);

    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_history;
    }

    @Override
    protected void initData()
    {
        tab_layout.setVisibility(View.GONE);
        tiemDialog = new TimePickerDialog.Builder()
                .setTitleStringId("请选择月份")//标题
                .setWheelItemTextSelectorColor(ContextCompat.getColor(getActivity(), R.color.red))//当前文本颜色
                .setType(Type.YEAR_MONTH)
                .setCallBack(this)
                .build();
        getData(new DateTime().toString("yyyy-MM"));
    }


    @OnClick(R.id.search_ll)
    public void onViewClicked()
    {
        tiemDialog.show(getActivity().getSupportFragmentManager(), "YEAR_MONTH_DAY");
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
            holder.setText(R.id.time, DateUtils.getDate(item.getAdd_time()));
//            holder.setText(R.id.date_str, item.getDate_str());
//            holder.setText(R.id.time1, item.getTime1());
//            holder.setText(R.id.time1_status, item.getTime1_status());
//            holder.setText(R.id.time2, item.getTime2());
//            holder.setText(R.id.time2_status, item.getTime2_status());
//            holder.setBackground(R.id.time1_status, getBac(item.getTime1_status()));
//            holder.setBackground(R.id.time2_status, getBac(item.getTime2_status()));
        }

    }

    private void getData(String month)
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.ACT, "GetPunchHistory")
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams("token", month)
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
        mData.clear();
        adapter.notifyDataSetChanged();
        getData(new DateTime(millseconds).toString("yyyy-MM"));
    }
}
