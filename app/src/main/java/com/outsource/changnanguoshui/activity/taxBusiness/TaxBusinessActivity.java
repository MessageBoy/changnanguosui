package com.outsource.changnanguoshui.activity.taxBusiness;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.StudyDetailsActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.StudyBean;
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
 * Created by Administrator on 2017/12/5.
 */

public class TaxBusinessActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, CommonBaseAdapter.onItemClickerListener
{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.swipe_target)
    RecyclerView swipe_target;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    MyAdapter adapter;
    private int page = 1;
    List<StudyBean.ListBean> data;


    @Override
    protected void initView()
    {
        setContentView(R.layout.fragment_study);
    }

    @Override
    protected void initData()
    {
        title.setText("风险管理");
        getData();
        data = new ArrayList();

        swipe_target.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        swipe_target.addItemDecoration(new ItemDivider());
        adapter = new MyAdapter(getApplicationContext(), R.layout.item_notice_bulletin, data);
        swipe_target.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        adapter.setItemListener(this);
    }


    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getApplicationContext(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getApplicationContext(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetInfoList")
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<StudyBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                    }

                    @Override
                    public void onResponse(StudyBean response, int id)
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
        Intent intent = new Intent(getApplicationContext(), StudyDetailsActivity.class);
        intent.putExtra("webUrl", Constant.DOMAIN_NAME + ((StudyBean.ListBean) data).getPage_url());
        intent.putExtra("activityTitle", "推送内容");
        startActivity(intent);
    }

    class MyAdapter extends CommonBaseAdapter<StudyBean.ListBean>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<StudyBean.ListBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, StudyBean.ListBean item, int position)
        {
            if (item.getRead_status() == 1)
            {
                holder.setImageResource(R.id.icon_nb, R.mipmap.read_file);
            } else
            {
                holder.setImageResource(R.id.icon_nb, R.mipmap.unread_file);
            }
            holder.setText(R.id.title_nb, item.getTitle());
            holder.setText(R.id.time_nb, DateUtils.getDate(item.getAdd_time()));
        }
    }

}
