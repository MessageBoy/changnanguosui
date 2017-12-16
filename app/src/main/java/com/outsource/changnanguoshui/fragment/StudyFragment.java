package com.outsource.changnanguoshui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.StudyBean;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/4.
 */

public class StudyFragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    MyAdapter adapter;
    Unbinder unbinder;
    private int page=1;
    List<StudyBean.ListBean> data;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        title.setText("今日税闻");
        back.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_study;
    }

    @Override
    protected void initData() {
        data=new ArrayList();
        getData();

        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle.addItemDecoration(new ItemDivider().setDividerColor(R.color.div));
        adapter = new StudyFragment.MyAdapter(getActivity(), R.layout.item_party_building, data);
        recycle.setAdapter(adapter);
    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetNews")
                .addParams("channel_id", "22")
                .addParams("channel_id", "category_id")
                .addParams("type_id", "1")
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<StudyBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {

                    }

                    @Override
                    public void onResponse(StudyBean response, int id)
                    {
                        if (response.getStatus()==1){
                            data =response.getList();

                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class MyAdapter extends CommonBaseAdapter<StudyBean.ListBean> {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<StudyBean.ListBean> data) {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, StudyBean.ListBean item, int position) {

        }


    }

}
