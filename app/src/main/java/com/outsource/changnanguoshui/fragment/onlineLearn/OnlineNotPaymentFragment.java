package com.outsource.changnanguoshui.fragment.onlineLearn;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.OnlinePayMentBen;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/5.
 */
public class OnlineNotPaymentFragment extends BaseFragment implements CommonBaseAdapter.onItemClickerListener {
    int type;
    @BindView(R.id.recycle_online)
    RecyclerView recycleOnline;
    MyAdapter adapter;
    List<OnlinePayMentBen.ListBean> data;
    @BindView(R.id.payment_submit)
    Button paymentSubmit;
    Unbinder unbinder;

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
        OnlineNotPaymentFragment fragment = new OnlineNotPaymentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        getData();
        data = new ArrayList<>();

        if (type == 0) {
            paymentSubmit.setVisibility(View.GONE);
        }
        recycleOnline.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleOnline.addItemDecoration(new ItemDivider());
        adapter = new MyAdapter(getActivity(), R.layout.item_not_already_payment, data);
        recycleOnline.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_not_online_payment;
    }

    @Override
    protected void initData() {

    }

    private void getData() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetMyPayLog")
                .addParams("flag", "" + type)
                .build()
                .execute(new GenericsCallback<OnlinePayMentBen>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络请求出错");
                    }

                    @Override
                    public void onResponse(OnlinePayMentBen response, int id) {
                        if (response.getStatus() == 1) {
                            data.addAll(response.getList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onItemClick(View view, Object data, int position) {

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

    class MyAdapter extends CommonBaseAdapter<OnlinePayMentBen.ListBean> {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<OnlinePayMentBen.ListBean> data) {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, OnlinePayMentBen.ListBean item, int position) {
            if (type == 0) {
                holder.setVisibility(R.id.checkbox, View.GONE);
            } else {
                holder.setVisibility(R.id.checkbox, View.VISIBLE);
            }
            holder.setText(R.id.money_number, item.getAmount() + "");
            holder.setText(R.id.year_date, item.getPay_month());
        }
    }

}
