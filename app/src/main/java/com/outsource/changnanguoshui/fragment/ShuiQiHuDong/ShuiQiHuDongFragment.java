package com.outsource.changnanguoshui.fragment.ShuiQiHuDong;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.HuDongDetailsActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
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
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/5.
 */
public class ShuiQiHuDongFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, CommonBaseAdapter.onItemClickerListener {
    int type;
    @BindView(R.id.swipe_target)
    RecyclerView swipe_target;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    MyAdapter adapter;
    private int page = 1;
    List<HuDongBean.ListBean> data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
    }

    public static Fragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        ShuiQiHuDongFragment fragment = new ShuiQiHuDongFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initData() {
        getData();
        data = new ArrayList();
        swipe_target.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipe_target.addItemDecoration(new ItemDivider());
        adapter = new MyAdapter(getActivity(), R.layout.item_sui_qi_hu_dong, data);
        swipe_target.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        adapter.setItemListener(this);
    }

    private void getData() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetInformList")
                .addParams("category_id", type+"")
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<HuDongBean>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络请求出错");
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                    }

                    @Override
                    public void onResponse(HuDongBean response, int id) {
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
        Intent intent = new Intent(getActivity(), HuDongDetailsActivity.class);
        intent.putExtra("id",((HuDongBean.ListBean) data).getInform_id());
        startActivity(intent);
    }

    class MyAdapter extends CommonBaseAdapter<HuDongBean.ListBean> {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<HuDongBean.ListBean> data) {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, HuDongBean.ListBean item, int position) {
            holder.setText(R.id.textView,item.getTitle());
            holder.setText(R.id.msg,item.getRow_number()+"");
            holder.setText(R.id.content,item.getContent());
            holder.setText(R.id.data, DateUtils.getDate(item.getAdd_time()));
        }
    }
}
