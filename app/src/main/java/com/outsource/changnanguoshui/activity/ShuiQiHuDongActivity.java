package com.outsource.changnanguoshui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.ShuiQiHuDong.ConsultMsgActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.HuDongBean;
import com.outsource.changnanguoshui.utlis.DateUtils;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.outsource.changnanguoshui.utlis.google.RefreshUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/29.
 */

public class ShuiQiHuDongActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, CommonBaseAdapter.onItemClickerListener
{
    int type = 2;
    @BindView(R.id.swipe_target)
    RecyclerView swipe_target;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    MyAdapter adapter;
    private int page = 1;
    List<HuDongBean.ListBean> data;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tab_add)
    TextView tab_add;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_shuiqihudong);
    }

    @Override
    protected void initData()
    {

        type = getIntent().getIntExtra("position", 2);
        getData();
        data = new ArrayList();
        if (type == 2)
        {
            title.setText("线上答疑");
            tab_add.setText("咨询");
        } else
        {
            title.setText("投诉建议");
            tab_add.setText("投诉");
        }


        swipe_target.setLayoutManager(new LinearLayoutManager(this));
        swipe_target.addItemDecoration(new ItemDivider());
        adapter = new MyAdapter(this, R.layout.item_sui_qi_hu_dong, data);
        swipe_target.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        adapter.setItemListener(this);

    }


    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetInformList")
                .addParams("category_id", type + "")
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<HuDongBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错");
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                    }

                    @Override
                    public void onResponse(HuDongBean response, int id)
                    {
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                        if (response.getStatus() == 1)
                        {
                            if (page == 1)
                                data.clear();
                            data.addAll(response.getList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @OnClick({R.id.back, R.id.tab_add})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.tab_add:
                Intent intent;
                intent = new Intent(getApplicationContext(), ConsultMsgActivity.class);
                intent.putExtra("activityTitle", title.getText().toString());
                intent.putExtra("category_id", type);
                intent.putExtra("content_hint", type == 2 ? "请输入税务咨询信息" : "请输入投诉建议信息");
                intent.putExtra("button_msg", type == 2 ? "提问" : "提交建议");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onLoadMore()
    {
        page++;
        getData();
    }

    @Override
    public void onRefresh()
    {
        page = 1;
        getData();
    }

    @Override
    public void onItemClick(View view, Object data, int position)
    {
        Intent intent = new Intent(this, HuDongDetailsActivity.class);
        intent.putExtra("id", ((HuDongBean.ListBean) data).getInform_id());
        startActivity(intent);
    }

    class MyAdapter extends CommonBaseAdapter<HuDongBean.ListBean>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<HuDongBean.ListBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, HuDongBean.ListBean item, int position)
        {
            holder.setText(R.id.textView, item.getTitle());
            holder.setText(R.id.msg, item.getRow_number() + "");
            holder.setText(R.id.content, item.getContent());
            holder.setText(R.id.data, DateUtils.getDate(item.getAdd_time()));
        }
    }
}
