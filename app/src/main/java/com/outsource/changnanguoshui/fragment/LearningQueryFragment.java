package com.outsource.changnanguoshui.fragment;


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
import com.outsource.changnanguoshui.activity.LearningDetailsActivity;
import com.outsource.changnanguoshui.activity.LearningQueryActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.GetMyStudyListBean;
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
public class LearningQueryFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, CommonBaseAdapter.onItemClickerListener, LearningQueryActivity.onAfterTextChanged
{
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    int page = 1;
    int type = 0;
    String skey = "";
    MyAdapter adapter;
    List<GetMyStudyListBean.ListBean> mData;

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
        LearningQueryFragment fragment = new LearningQueryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_list;
    }

    @Override
    protected void initData()
    {
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new ItemDivider());
        adapter = new MyAdapter(getActivity(), R.layout.item_me_learn, mData);
        recyclerView.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        adapter.setItemListener(this);
        onRefresh();

    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetStudy")
                .addParams("flag", type + "")
                .addParams("skey", skey)
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<GetMyStudyListBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                    }

                    @Override
                    public void onResponse(GetMyStudyListBean response, int id)
                    {
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                        if (page == 1)
                            mData.clear();
                        mData.addAll(response.getList());
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void setDate(String a)
    {
        page = 1;
        skey = a;
        getData();
    }

    class MyAdapter extends CommonBaseAdapter<GetMyStudyListBean.ListBean>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<GetMyStudyListBean.ListBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, GetMyStudyListBean.ListBean item, int position)
        {
            holder.setText(R.id.context, item.getTitle());
            holder.setText(R.id.study_type, item.getStudy_type() == 0 ? "选学" : "必学");
            holder.setText(R.id.learn_time, "已学时间：" + item.getLearn_time());
            holder.setText(R.id.study_time, "需学时间：" + item.getStudy_time());
            holder.setText(R.id.point, "   |   " + item.getPoint() + "分");
            holder.setImageResource(R.id.state_ml, item.getStatus() == 0 ? R.mipmap.online_file_noover : R.mipmap.hang_the_air);
            holder.setImage(R.id.me_learn_img, item.getImg_url());
        }
    }

    @Override
    public void onItemClick(View view, Object data, int position)
    {
        Intent intent = new Intent(getActivity(), LearningDetailsActivity.class);
        intent.putExtra("id", ((GetMyStudyListBean.ListBean) data).getId());
        startActivity(intent);
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
}
