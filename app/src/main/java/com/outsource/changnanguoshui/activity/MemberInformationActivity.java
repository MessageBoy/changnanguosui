package com.outsource.changnanguoshui.activity;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.application.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/13.
 */

public class MemberInformationActivity extends BaseActivity
{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.info_icon)
    ImageView infoIcon;
    @BindView(R.id.info_name)
    TextView infoName;
    @BindView(R.id.position)
    TextView position;
    @BindView(R.id.grjf)
    TextView grjf;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.history_list)
    RecyclerView historyList;
    MyAdapter adapter;

    List<String> mData;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_member_information);
    }

    @Override
    protected void initData()
    {
        title.setText("成员信息");
        mData = new ArrayList<>();
        historyList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, R.layout.item_history, mData);
        historyList.setAdapter(adapter);
        for (int i = 0; i < 13; i++)
            mData.add("");

    }


    @OnClick({R.id.back, R.id.search_ll})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.search_ll:
                Alert("选择日期");
                break;
        }
    }


    class MyAdapter extends CommonBaseAdapter<String>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<String> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, String item, int position)
        {
        }
    }
}
