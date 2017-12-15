package com.outsource.changnanguoshui.fragment.onlineLearn;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.onlineLearn.OnlineAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/5.
 */
public class OnlineLearnFragment extends BaseFragment
{
    OnlineAdapter onlineAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recycleOnline;
    int page = 1;

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
        recycleOnline.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleOnline.addItemDecoration(new ItemDivider().setDividerWith(2).setDividerColor(R.color.div));
        onlineAdapter = new OnlineAdapter(getActivity(), setOnlineData());
        recycleOnline.setAdapter(onlineAdapter);
        getData();
    }


    private List<String> setOnlineData()
    {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            data.add("1");
        }
        return data;
    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetStudyList")
                .addParams("type_id", "1")
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<String>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {

                    }

                    @Override
                    public void onResponse(String response, int id)
                    {

                    }
                });
    }
}
