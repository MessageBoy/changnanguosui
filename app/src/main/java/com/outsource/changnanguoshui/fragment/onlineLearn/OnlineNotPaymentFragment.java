package com.outsource.changnanguoshui.fragment.onlineLearn;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.AddPayOrderBean;
import com.outsource.changnanguoshui.bean.OnlinePayMentBen;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import org.joda.time.DateTime;

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
public class OnlineNotPaymentFragment extends BaseFragment implements OnDateSetListener {
    int type;
    @BindView(R.id.recycle_online)
    RecyclerView recycleOnline;
    MyAdapter adapter;
    List<OnlinePayMentBen.ListBean> data;
    @BindView(R.id.payment_submit)
    Button paymentSubmit;
    Map<Object, Object> map;
    double str = 0.00;
    String months = "";
    @BindView(R.id.year)
    TextView year;
    private IWXAPI iwxapi;

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
    public void onResume() {
        super.onResume();
        String yearT = year.getText().toString();
        getData(yearT.substring(0, yearT.length() - 1));
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        data = new ArrayList<>();
        map = new HashMap<>();
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

        year.setText(new DateTime().toString("yyyy") + "年");
    }

    private void getData(String year) {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetMyPayLog")
                .addParams("flag", "" + type)
                .addParams("syear", year)
                .build()
                .execute(new GenericsCallback<OnlinePayMentBen>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络请求出错");
                    }

                    @Override
                    public void onResponse(OnlinePayMentBen response, int id) {
                        data.clear();
                        if (response.getStatus() == 1) {
                            data.addAll(response.getList());
                            if (type == 1) {
                                paymentSubmit.setVisibility(View.VISIBLE);
                            }
                        } else {
                            paymentSubmit.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private AlertDialog.Builder setPositiveButton(
            AlertDialog.Builder builder) {
        return builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                postData();

            }
        });
    }

    private AlertDialog.Builder setNegativeButton(
            AlertDialog.Builder builder) {
        return builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                str = 0.00;
            }
        });
    }


    private void postData() {
        AlertDialog();
        Log.e("USER_ID", SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString());
        Log.e("TOKEN", SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString());
        Log.e("ACT", months);
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
                .execute(new GenericsCallback<AddPayOrderBean>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络请求出错");
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(AddPayOrderBean response, int id) {
                        dialog.dismiss();

                        if (response.getStatus() == 1) {
                            toWXPay(response);
                        }
                        str = 0;
                        months = "";
                    }
                });
    }

    private void toWXPay(final AddPayOrderBean response) {
        iwxapi = WXAPIFactory.createWXAPI(getActivity(), null); //初始化微信api
        iwxapi.registerApp("wx20d82208bc65f133"); //注册appid  appid可以在开发平台获取

        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = response.getAppid();
                request.partnerId = response.getPartnerid();
                request.prepayId = response.getPrepayid();
                request.packageValue = response.get_package();
                request.nonceStr = response.getNoncestr();
                request.timeStamp = response.getTimestamp();
                request.sign = response.getSign();
                iwxapi.sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    @OnClick({R.id.search_year, R.id.payment_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_year:
                showTiemDialog();
                break;
            case R.id.payment_submit:
                for (int i = 0; i < data.size(); i++) {
                    boolean is = data.get(i).isCheckBox();
                    if (is) {
                        str = data.get(i).getAmount() + str;
                        months = data.get(i).getPay_month() + ";" + months;
                    }
                }
                if (str <= 0.00 || "".equals(months)) {
                    Alert("请勾选需要缴费的月份");
                } else {
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
                break;
        }
    }

    private void showTiemDialog() {
        TimePickerDialog tiemDialog = new TimePickerDialog.Builder()
                .setTitleStringId("请选择月份")//标题
                .setWheelItemTextSelectorColor(ContextCompat.getColor(getActivity(), R.color.red))//当前文本颜色
                .setType(Type.YEAR)
                .setCallBack(this)
                .build();
        tiemDialog.show(getActivity().getSupportFragmentManager(), "YEAR_MONTH_DAY");
    }


    //选择时间回调
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        getData(new DateTime(millseconds).toString("yyyy"));
        year.setText(new DateTime(millseconds).toString("yyyy") + "年");
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
            CheckBox checkbox = holder.getView(R.id.checkbox);
            //给CheckBox设置事件监听
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        data.get(position).setCheckBox(true);
                    } else {
                        data.get(position).setCheckBox(false);
                    }
                }
            });
        }
    }
}
