package com.outsource.changnanguoshui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.StudyDetailsActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
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
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/15.
 */

public class RegulatoryRetrievalFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, CommonBaseAdapter.onItemClickerListener
{
    int type;
    @BindView(R.id.swipe_target)
    RecyclerView recycle;
    MyAdapter adapter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private int page = 1;
    List<StudyBean.ListBean> data;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            type = bundle.getInt("type");
        }
    }

    public static Fragment newInstance(int type)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        RegulatoryRetrievalFragment fragment = new RegulatoryRetrievalFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        data=new ArrayList<>();
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle.addItemDecoration(new ItemDivider());
        adapter = new RegulatoryRetrievalFragment.MyAdapter(getActivity(), R.layout.item_party_building, data);
        recycle.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        adapter.setItemListener(this);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_list;
    }

    @Override
    protected void initData()
    {
        getData();
    }

    private void getData() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetNews")
                .addParams("channel_id", "14")
                .addParams("category_id", ""+type)
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<StudyBean>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络请求出错：" + e.getMessage());
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                    }

                    @Override
                    public void onResponse(StudyBean response, int id) {
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
        Intent intent = new Intent(getActivity(), StudyDetailsActivity.class);
        intent.putExtra("webUrl", Constant.DOMAIN_NAME+((StudyBean.ListBean) data).getPage_url());
        intent.putExtra("activityTitle", "文章内容");
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
            if (TextUtils.isEmpty(item.getImg_url())){
                holder.setVisibility(R.id.party_img,View.GONE);
            }else{
                holder.setVisibility(R.id.party_img,View.VISIBLE);
                holder.setImage(R.id.party_img,item.getImg_url());
            }
            holder.setText(R.id.context,item.getTitle());
            holder.setText(R.id.party_time, DateUtils.getDate(item.getAdd_time()));
        }
    }
}
