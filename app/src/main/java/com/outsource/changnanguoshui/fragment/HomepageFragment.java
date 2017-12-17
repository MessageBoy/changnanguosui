package com.outsource.changnanguoshui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.activity.AdministrativeManagementActivity;
import com.outsource.changnanguoshui.activity.ArticleSearchActivity;
import com.outsource.changnanguoshui.activity.PartyBuildingActivity;
import com.outsource.changnanguoshui.activity.PersonnelManagementActivity;
import com.outsource.changnanguoshui.activity.RegulatoryRetrievalActivity;
import com.outsource.changnanguoshui.activity.ShuiQiHuDong.ConsultMsgActivity;
import com.outsource.changnanguoshui.activity.ShuiQiHuDong.ShuiQiHDActivity;
import com.outsource.changnanguoshui.activity.StudyDetailsActivity;
import com.outsource.changnanguoshui.activity.onlineLearn.OnlineLearnActivity;
import com.outsource.changnanguoshui.activity.onlineLearn.OnlinePaymentActivity;
import com.outsource.changnanguoshui.activity.taxBusiness.TaxBusinessActivity;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.HomeBean;
import com.outsource.changnanguoshui.bean.StudyBean;
import com.outsource.changnanguoshui.utlis.DateUtils;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/4.
 */

public class HomepageFragment extends BaseFragment implements CommonBaseAdapter.onItemClickerListener
{
    @BindView(R.id.time_home)
    TextView timeHome;
    @BindView(R.id.modular_list)
    RecyclerView modularList;
    @BindView(R.id.information_list)
    RecyclerView informationList;
    HomepageFragment.MyAdapter adapter;
    private int page = 1;
    List<StudyBean.ListBean> data;
    String[] title = {"党员信息", "党建风采", "在线党费", "在线学习", "风险推送", "法规检索", "通知公告", "在线打卡", "线上活动", "线上答疑", "业务办理", "举报建议"};
    int[] icon = {R.mipmap.dyxx, R.mipmap.djfc, R.mipmap.zxdf, R.mipmap.zxxx, R.mipmap.fxts, R.mipmap.wjcx, R.mipmap.tzgg, R.mipmap.zxdk, R.mipmap.xshd, R.mipmap.sqhd, R.mipmap.lzjb, R.mipmap.zhwh};
    HomeAdapter homeAdapter;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        timeHome.setText(DateUtils.getSystemTime());
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initData()
    {
        getData();
        data = new ArrayList();
        modularList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        informationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        informationList.addItemDecoration(new ItemDivider());
        homeAdapter = new HomeAdapter(getActivity(), R.layout.item_home, setHomeData());
        modularList.setAdapter(homeAdapter);
        adapter = new HomepageFragment.MyAdapter(getActivity(), R.layout.item_party_building, data);
        informationList.setAdapter(adapter);
        adapter.setItemListener(this);
        homeAdapter.setItemListener(new CommonBaseAdapter.onItemClickerListener()
        {
            @Override
            public void onItemClick(View view, Object data, int position)
            {
                Intent intent;
                switch (position)
                {
                    case 0:
                        intent = new Intent(getActivity(), PersonnelManagementActivity.class);
                        intent.putExtra("position", Constant.ZERO);
                        startActivity(intent);
                        break;
                    case 1:
                        startActivity(PartyBuildingActivity.class);
                        break;
                    case 2:
                        startActivity(OnlinePaymentActivity.class);
                        break;
                    case 3:
                        startActivity(OnlineLearnActivity.class);
                        break;
                    case 4:
                        startActivity(TaxBusinessActivity.class);
                        break;
                    case 5:
                        startActivity(RegulatoryRetrievalActivity.class);
                        break;
                    case 6:
                        intent = new Intent(getActivity(), AdministrativeManagementActivity.class);
                        intent.putExtra("position", Constant.ZERO);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(getActivity(), AdministrativeManagementActivity.class);
                        intent.putExtra("position", Constant.ONE);
                        startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(getActivity(), AdministrativeManagementActivity.class);
                        intent.putExtra("position", Constant.Two);
                        startActivity(intent);
                        break;
                    case 9:
                        startActivity(ShuiQiHDActivity.class);
                        break;
                    case 10:
                        intent = new Intent(getActivity(), StudyDetailsActivity.class);
                        intent.putExtra("webUrl", Constant.BUSINESS_TRANSACT);
                        intent.putExtra("activityTitle", "业务办理");
                        startActivity(intent);
                        break;
                    case 11:
                        intent = new Intent(getActivity(), ConsultMsgActivity.class);
                        intent.putExtra("activityTitle", "举报建议");
                        intent.putExtra("category_id", "1");
                        intent.putExtra("content_hint", "请输入举报建议信息");
                        intent.putExtra("button_msg", "提交建议");
                        startActivity(intent);
                        break;

                }
            }
        });
    }


    private void getData() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetNews")
                .addParams("channel_id", "22")
                .addParams("category_id", "11")
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<StudyBean>(new JsonGenerics()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(StudyBean response, int id) {
                        if (response.getStatus() == 1) {
                            if (page == 1)
                                data.clear();
                            data.addAll(response.getList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        Intent intent = new Intent(getActivity(), StudyDetailsActivity.class);
        intent.putExtra("webUrl", Constant.DOMAIN_NAME+((StudyBean.ListBean) data).getPage_url());
        intent.putExtra("activityTitle", "资讯内容");
        startActivity(intent);
    }

    class MyAdapter extends CommonBaseAdapter<StudyBean.ListBean> {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<StudyBean.ListBean> data) {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, StudyBean.ListBean item, int position) {

            if (TextUtils.isEmpty(item.getImg_url())){
                holder.setVisibility(R.id.party_img,View.GONE);
            }else{
                holder.setVisibility(R.id.party_img,View.VISIBLE);
                holder.setImage(R.id.party_img, item.getImg_url());
            }
            holder.setText(R.id.context, item.getTitle());
            holder.setText(R.id.party_time, DateUtils.getDate(item.getAdd_time()));
        }
    }

    @OnClick({R.id.time_home, R.id.search_home})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.time_home:
                break;
            case R.id.search_home:
                startActivity(ArticleSearchActivity.class);
                break;
        }
    }

    private List<HomeBean> setHomeData()
    {
        List<HomeBean> data = new ArrayList<>();
        for (int i = 0; i < title.length; i++)
        {
            data.add(new HomeBean(icon[i], title[i]));
        }
        return data;
    }

    class HomeAdapter extends CommonBaseAdapter<HomeBean>
    {

        public HomeAdapter(Context context, @LayoutRes int itemLayoutId, List<HomeBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, HomeBean item, int position)
        {
            holder.setImageResource(R.id.icon_home, item.getIcon());
            holder.setText(R.id.title_home, item.getTitle());

        }
    }

}
