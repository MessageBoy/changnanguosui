package com.outsource.changnanguoshui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.LeaveBean;
import com.outsource.changnanguoshui.bean.SuccessBean;
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
 * Created by Administrator on 2018/1/15.
 */

public class LeaveFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener
{
    @BindView(R.id.swipe_target)
    RecyclerView swipe_target;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    MyAdapter adapter;
    int page = 1;
    List<LeaveBean.ListBean> data;
    String type = "0";
    AlertDialog myDialog;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            type = bundle.getString("type");
        }
    }

    public static Fragment newInstance(String type)
    {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        LeaveFragment fragment = new LeaveFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {

    }

    @Override
    public void onResume()
    {
        super.onResume();
        getData();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_list;
    }

    @Override
    protected void initData()
    {
        data = new ArrayList();
        swipe_target.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipe_target.addItemDecoration(new ItemDivider());
        adapter = new MyAdapter(getActivity(), R.layout.item_leave, data);
        swipe_target.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetVacateList")
                .addParams("flag", type)
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<LeaveBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                    }

                    @Override
                    public void onResponse(LeaveBean response, int id)
                    {
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                        try
                        {
                            if (page == 1)
                                data.clear();
                            data.addAll(response.getList());
                            adapter.notifyDataSetChanged();
                        } catch (Exception e)
                        {
                            e.getMessage();
                        }

                    }
                });
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


    class MyAdapter extends CommonBaseAdapter<LeaveBean.ListBean>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<LeaveBean.ListBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, final LeaveBean.ListBean item, int position)
        {

            if (type.equals("0"))
            {
                holder.setVisibility(R.id.tongyi, View.GONE);
                holder.setVisibility(R.id.butongyi, View.GONE);
            } else
            {
                holder.setVisibility(R.id.memo_ll, View.GONE);
                holder.setVisibility(R.id.verify, View.GONE);
            }
            if (item.getVerify() == 0)
            {
                holder.setTextColor(R.id.verify, Color.parseColor("#335be2"));
            } else if (item.getVerify() == 1)
            {
                holder.setTextColor(R.id.verify, Color.parseColor("#23ac00"));
            } else if (item.getVerify() == 2)
            {
                holder.setTextColor(R.id.verify, Color.parseColor("#e13531"));

            }
            holder.setText(R.id.verify, item.getVerify_str());
            holder.setText(R.id.real_name, item.getReal_name());
            holder.setText(R.id.intro, item.getIntro());
            holder.setText(R.id.memo, item.getMemo());
            holder.setText(R.id.add_time, DateUtils.getDates(item.getAdd_time()));
            holder.setText(R.id.begin_time, DateUtils.getDate(item.getBegin_time()));
            holder.setText(R.id.end_time, DateUtils.getDate(item.getEnd_time()));
            holder.setOnClick(R.id.tongyi, new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    show(item.getVacate_id(), "1");
                }
            });
            holder.setOnClick(R.id.butongyi, new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    show(item.getVacate_id(), "2");
                }
            });
        }
    }

    public void show(final String vacate_id, final String flag)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.newPassword);
        //填充对话框的布局
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_layout, null);
        //初始化控件
        TextView dialogBut = (TextView) inflate.findViewById(R.id.dialog_but);
        final EditText dialogEdit = (EditText) inflate.findViewById(R.id.dialog_edit);
        dialogBut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (flag.equals("2") && TextUtils.isEmpty(dialogEdit.getText().toString()))
                {
                    Alert("请填写备注说明");
                    return;
                }
                postVacate(vacate_id, flag, dialogEdit.getText().toString());
            }
        });
        myDialog = builder.create();
        myDialog.setView(inflate);
        Window window = myDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);//底部出现
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        myDialog.show();
    }

    private void postVacate(String vacate_id, String flag, String intro)
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "SetVacate")
                .addParams("vacate_id", vacate_id)
                .addParams("flag", flag)
                .addParams("intro", intro)
                .build()
                .execute(new GenericsCallback<SuccessBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                        myDialog.dismiss();
                    }

                    @Override
                    public void onResponse(SuccessBean response, int id)
                    {
                        myDialog.dismiss();
                        Alert(response.getMsg());
                        if (response.getStatus() == 1)
                        {
                            getData();
                        }
                    }
                });
    }

}
