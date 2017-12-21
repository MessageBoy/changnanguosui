package com.outsource.changnanguoshui.fragment.onlineLearn;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.ConsultMsgBean;
import com.outsource.changnanguoshui.bean.OnlinePayMentBen;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
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
    Map<Object,Object> map;
    double str=0.00;
    String months="";
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
        map=new HashMap<>();
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
                        } else {
                            paymentSubmit.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onItemClick(View view, Object data, int position) {


    }

    private AlertDialog.Builder setPositiveButton(
            AlertDialog.Builder builder)
    {
        return builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {

                postData();

            }
        });
    }

    private AlertDialog.Builder setNegativeButton(
            AlertDialog.Builder builder)
    {
        return builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                str=0.00;
            }
        });
    }

    @OnClick(R.id.payment_submit)
    public void onViewClicked() {

                for (int i=0;i<data.size();i++){
                   boolean is=data.get(i).isCheckBox();
                    if(is){
                        str=data.get(i).getAmount()+str;
                        months=data.get(i).getPay_month()+";"+months;
                    }
                }
                if(str<=0.00 || "".equals(months)){
                    Alert("请勾选需要缴费的月份");
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("确认缴纳党费")
                            .setMessage("共选择缴纳党费：" + str + "元");
                    //为AlertDialog.Builder添加“确定”按钮
                    setPositiveButton(builder);
                    //为AlertDialog.Builder添加“取消”按钮
                    setNegativeButton(builder)
                            .create()
                            .show();
                }
    }

    private void postData() {
        AlertDialog();
        OkHttpUtils
                .post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "AddPayOrder")
                .addParams("price", "" + str)
                .addParams("payment_id", "" + 2)
                .addParams("pay_month", months)
                .build()
                .execute(new GenericsCallback<ConsultMsgBean>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络请求出错");
                    }
                    @Override
                    public void onResponse(ConsultMsgBean response, int id) {
                        Alert(response.getMsg());
                        if (response.getStatus() == 1) {
                           getData();
                        }
                    }
                });
    }


    class MyAdapter extends CommonBaseAdapter<OnlinePayMentBen.ListBean> {
        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<OnlinePayMentBen.ListBean> data) {
            super(context, itemLayoutId, data);
        }
        @Override
        public void bindViewData(BaseViewHolder holder, OnlinePayMentBen.ListBean item, final int position) {
            if (type == 0) {
                holder.setVisibility(R.id.checkbox, View.GONE);
            } else {
                holder.setVisibility(R.id.checkbox, View.VISIBLE);
            }
            holder.setText(R.id.money_number, item.getAmount() + "");
            holder.setText(R.id.year_date, item.getPay_month());
            CheckBox checkbox=holder.getView(R.id.checkbox);
            //给CheckBox设置事件监听
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        data.get(position).setCheckBox(true);
                    }else{
                        data.get(position).setCheckBox(false);
                    }
                }
            });
        }
    }
}
