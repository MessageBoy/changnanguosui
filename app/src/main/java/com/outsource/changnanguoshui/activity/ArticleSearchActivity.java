package com.outsource.changnanguoshui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.CommonBaseAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.application.BaseViewHolder;
import com.outsource.changnanguoshui.bean.GetSearchNewsBean;
import com.outsource.changnanguoshui.utlis.DateUtils;
import com.outsource.changnanguoshui.utlis.DribSearchView;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.ItemDivider;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.google.RefreshUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/17.
 */

public class ArticleSearchActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, CommonBaseAdapter.onItemClickerListener
{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.editview)
    EditText editText;
    @BindView(R.id.dribSearchView)
    DribSearchView dribSearchView;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private InputMethodManager imm;
    private boolean isState = false;
    int page = 1;
    MyAdapter adapter;
    List<GetSearchNewsBean.ListBean> mData;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_article_search);
    }

    @Override
    protected void initData()
    {
        title.setText("文章搜索");
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ItemDivider());
        adapter = new MyAdapter(this, R.layout.item_party_building, mData);
        recyclerView.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        adapter.setItemListener(this);
        onRefresh();
        dribSearchView.setOnClickSearchListener(new DribSearchView.OnClickSearchListener()
        {
            @Override
            public void onClickSearch()
            {
                dribSearchView.changeLine();
            }
        });
        dribSearchView.setOnChangeListener(new DribSearchView.OnChangeListener()
        {
            @Override
            public void onChange(DribSearchView.State state)
            {

                switch (state)
                {
                    case LINE:
                        isState = true;
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
                        editText.setVisibility(View.VISIBLE);
                        editText.setFocusable(true);
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        title.setVisibility(View.GONE);
                        break;
                    case SEARCH:
                        isState = false;
                        editText.setVisibility(View.GONE);
                        title.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                page = 1;
                getData();
            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked()
    {
        if (isState)
        {
            editText.setText("");
            dribSearchView.changeSearch();
        } else
        {
            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {
            if (isState)
            {
                editText.setText("");
                dribSearchView.changeSearch();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.ACT, "GetSearchNews")
                .addParams("skey", editText.getText().toString().trim())
                .addParams("page", page + "")
                .build()
                .execute(new GenericsCallback<GetSearchNewsBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                    }

                    @Override
                    public void onResponse(GetSearchNewsBean response, int id)
                    {
                        RefreshUtils.isRefresh(swipeToLoadLayout);
                        if (page == 1)
                            mData.clear();
                        mData.addAll(response.getList());
                        adapter.notifyDataSetChanged();
                    }
                });
    }


    class MyAdapter extends CommonBaseAdapter<GetSearchNewsBean.ListBean>
    {

        public MyAdapter(Context context, @LayoutRes int itemLayoutId, List<GetSearchNewsBean.ListBean> data)
        {
            super(context, itemLayoutId, data);
        }

        @Override
        public void bindViewData(BaseViewHolder holder, GetSearchNewsBean.ListBean item, int position)
        {
            holder.setText(R.id.context, item.getTitle());
            holder.setText(R.id.party_time, DateUtils.getDate(item.getAdd_time()));
            holder.setImage(R.id.party_img, item.getImg_url());
            holder.setVisibility(R.id.party_img, TextUtils.isEmpty(item.getImg_url()) ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(View view, Object data, int position)
    {
        Intent intent = new Intent(this, StudyDetailsActivity.class);
        intent.putExtra("activityTitle", "文章详情");
        intent.putExtra("webUrl", Constant.DOMAIN_NAME + ((GetSearchNewsBean.ListBean) data).getPage_url());
        startActivity(intent);
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
}
