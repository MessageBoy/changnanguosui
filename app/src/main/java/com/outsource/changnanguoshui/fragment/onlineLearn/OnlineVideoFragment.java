package com.outsource.changnanguoshui.fragment.onlineLearn;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.LearningDetailsActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.ConsultMsgBean;
import com.outsource.changnanguoshui.bean.GetStudyListBean;
import com.outsource.changnanguoshui.utlis.DateUtils;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
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
public class OnlineVideoFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, CommonBaseAdapter.onItemClickerListener
{
    MyAdapter adapter;
    @BindView(R.id.swipe_target)
    RecyclerView recycleOnline;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    int page = 1;
    List<GetStudyListBean.ListBean> mData;

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
        recycleOnline.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new MyAdapter(getActivity(), R.layout.item_online_video, mData);
        recycleOnline.setAdapter(adapter);
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
                .addParams(Constant.ACT, "GetStudyList")
                .addParams("type_id", "2")
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<GetStudyListBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                    }

                    @Override
                    public void onResponse(GetStudyListBean response, int id)
                    {
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                        if (response.getStatus() == 1)
                        {
                            if (page == 1)
                                mData.clear();
                            mData.addAll(response.getList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    class MyAdapter extends CommonBaseAdapter<GetStudyListBean.ListBean>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<GetStudyListBean.ListBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, final GetStudyListBean.ListBean item, final int position)
        {
            holder.setText(R.id.title_ov, item.getTitle());
//            holder.setText(R.id.online_but, item.getCategory_name());
            holder.setText(R.id.time_ov, DateUtils.getDate(item.getAdd_time()));
            holder.setImageResource(R.id.state_ov, item.getStatus() == 0 ? R.mipmap.online_file_noover : R.mipmap.hang_the_air);
//            holder.setImageResource(R.id.collection_ov, item.getIs_favorite() == 0 ? R.mipmap.online_sc : R.mipmap.online_sc);
            ImageView img=holder.getView(R.id.collection_ov);

            Drawable drawable1= DrawableCompat.wrap(getResources().getDrawable(R.mipmap.online_sc).mutate());
            if(item.getIs_favorite() == 0) {
                DrawableCompat.setTintList(drawable1, getResources().getColorStateList(R.color.gray));
            }
            img.setBackgroundDrawable(drawable1);
            //收藏
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getIs_favorite() == 0) {
                        collect(item.getId(), position);
                    }
                }
            });

            holder.setImage(R.id.icon_ov, item.getImg_url());
        }
    }

    private void collect(int info_id, final int position){
        OkHttpUtils
                .post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "AddFavorite")
                .addParams("type_id", "1")
                .addParams("info_id", info_id + "")
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
                            Alert("收藏成功");
                            mData.get(position).setIs_favorite(1);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onItemClick(View view, Object data, int position)
    {
        Intent intent = new Intent(getActivity(), LearningDetailsActivity.class);
        intent.putExtra("id", ((GetStudyListBean.ListBean) data).getId());
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
