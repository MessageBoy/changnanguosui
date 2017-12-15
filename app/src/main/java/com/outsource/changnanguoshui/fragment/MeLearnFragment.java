package com.outsource.changnanguoshui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.utlis.ItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/5.
 */
public class MeLearnFragment extends BaseFragment {
    int type;
    Unbinder unbinder;
    @BindView(R.id.recycle_online)
    RecyclerView recycleOnline;
    MyAdapter adapter;
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
        MeLearnFragment fragment = new MeLearnFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_online_learn;
    }

    @Override
    protected void initData() {
        recycleOnline.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleOnline.addItemDecoration(new ItemDivider().setDividerColor(R.color.div));
        adapter = new MyAdapter(getActivity(), R.layout.item_me_learn, setOnlineData());
        recycleOnline.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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


    private List<String> setOnlineData()
    {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            data.add("1");
        }
        return data;
    }

}
