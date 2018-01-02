package com.outsource.changnanguoshui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.outsource.changnanguoshui.utlis.WebUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/13.
 */
//implements OnLoadMoreListener, OnRefreshListener
public class OrganizationalFragment extends BaseFragment
{
    //    @BindView(R.id.swipe_target)
//    RecyclerView recyclerView;
//    @BindView(R.id.swipeToLoadLayout)
//    SwipeToLoadLayout swipeToLoadLayout;
//    MyAdapter adapter;
//    List<GetDepartmentBean.ListBean> mData = new ArrayList<>();
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            url = bundle.getString("mData");
        }
    }

    public static Fragment newInstance(String mData)
    {
        Bundle bundle = new Bundle();
        bundle.putString("mData", mData);
        OrganizationalFragment fragment = new OrganizationalFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
//        swipeToLoadLayout.setOnRefreshListener(this);
//        swipeToLoadLayout.setOnLoadMoreListener(this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.addItemDecoration(new ItemDivider().setDividerColor(ContextCompat.getColor(getActivity(), R.color.div)));
//        adapter = new MyAdapter(getActivity(), R.layout.item_member_info, mData);
//        recyclerView.setAdapter(adapter);
        relativeLayout.setVisibility(View.GONE);
        WebUtils.webSetting(webView, getActivity());
        webView.loadUrl(Constant.DOMAIN_NAME + url + "?user_id=" + SpUtils.getParam(getActivity(), Constant.USER_ID, ""));
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_vote;
    }

    @Override
    protected void initData()
    {
//        adapter.setItemListener(new CommonBaseAdapter.onItemClickerListener()
//        {
//            @Override
//            public void onItemClick(View view, Object data, int position)
//            {
//                Intent intent = new Intent(getActivity(), MemberInfoActivity.class);
//                intent.putExtra("mData", ((GetDepartmentBean.ListBean) data));
//                startActivity(intent);
//            }
//        });
    }

//
//    class MyAdapter extends CommonBaseAdapter<GetDepartmentBean.ListBean>
//    {
//
//        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<GetDepartmentBean.ListBean> data)
//        {
//            super(context, itemLayoutId, data);
//        }
//
//        @Override
//        public void bindViewData(BaseViewHolder holder, GetDepartmentBean.ListBean item, int position)
//        {
//            holder.setText(R.id.department_name, item.getDep_name());
//        }
//    }
//
//    @Override
//    public void onLoadMore()
//    {
//        swipeToLoadLayout.setLoadingMore(false);
//    }
//
//    @Override
//    public void onRefresh()
//    {
//        swipeToLoadLayout.setRefreshing(false);
//    }
}
