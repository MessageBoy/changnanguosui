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
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.ConsultMsgBean;
import com.outsource.changnanguoshui.bean.MyCollectBean;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.outsource.changnanguoshui.utlis.google.GoogleCircleProgressView;
import com.outsource.changnanguoshui.utlis.google.RefreshUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/24.
 */

public class MyCollectActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, CommonBaseAdapter.onItemClickerListener{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.googleProgress)
    GoogleCircleProgressView googleProgress;
    @BindView(R.id.swipe_target)
    RecyclerView swipe_target;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    MyAdapter adapter;
    private int page = 1;
    List<MyCollectBean.ListBean> data;
    @Override
    protected void initView() {
        setContentView(R.layout.fragment_study);
    }

    @Override
    protected void initData() {
        title.setText("我的收藏");
        getData();
        data = new ArrayList();

        swipe_target.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        swipe_target.addItemDecoration(new ItemDivider());
        adapter = new MyCollectActivity.MyAdapter(getApplicationContext(), R.layout.item_my_collect, data);
        swipe_target.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        adapter.setItemListener(this);

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
                .addParams(Constant.ACT, "GetMyFavorite")
                .addParams("flag", "1")
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<MyCollectBean>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络请求错误");
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                    }

                    @Override
                    public void onResponse(MyCollectBean response, int id) {
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                        if (response.getStatus() == 1) {
                            if (page == 1)
                                data.clear();
                            data.addAll(response.getList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onLoadMore() {
        page++;
        getData();
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData();
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        Intent intent = new Intent(getApplicationContext(), LearningDetailsActivity.class);
        intent.putExtra("id", Integer.parseInt(((MyCollectBean.ListBean) data).getInfo_id()));
        startActivity(intent);
    }

    class MyAdapter extends CommonBaseAdapter<MyCollectBean.ListBean> {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<MyCollectBean.ListBean> data) {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, final MyCollectBean.ListBean item, final int position) {
            TextView cancel_collect=holder.getView(R.id.cancel_collect);
            holder.setText(R.id.context,"     "+item.getTitle());
            cancel_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelCollect(item.getFavorite_id(),position);
                }
            });
        }
    }

    private void cancelCollect(String favorite_id, final int position){
        OkHttpUtils
                .post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getApplicationContext(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getApplicationContext(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "DelMyFavorite")
                .addParams("favorite_id", favorite_id)
                .build()
                .execute(new GenericsCallback<ConsultMsgBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错");
                    }

                    @Override
                    public void onResponse(ConsultMsgBean response, int id)
                    {
                        if (response.getStatus() == 1){
                            Alert("取消成功");
                            data.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}
