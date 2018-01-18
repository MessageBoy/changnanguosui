package com.outsource.changnanguoshui.activity;

import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.outsource.changnanguoshui.utlis.WebUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/10.
 */

public class OnlineActivityActivity extends BaseActivity
{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webview)
    WebView webview;
//    @BindView(R.id.tab_layout)
//    TabLayout tabLayout;
//    @BindView(R.id.view_pager)
//    ViewPager viewPager;
//    private List<String> mPageTitleList = new ArrayList<>();
//    private List<Fragment> mFragmentList = new ArrayList<>();
//    private TabAdapter mAdapter;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_online_activity);
    }

    @Override
    protected void initData()
    {
        title.setText("主题活动");
//        mPageTitleList.add("活动详情");
//        mPageTitleList.add("投票列表");
//        mPageTitleList.add("投票排行");
//        mFragmentList.add(new ActivityDetailsFragment());
//        mFragmentList.add(new VotingListFragment());
//        mFragmentList.add(new VoteRankingFragment());
//        mAdapter = new TabAdapter(this, getSupportFragmentManager(),
//                mFragmentList, mPageTitleList);
//        viewPager.setAdapter(mAdapter);
//        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setOffscreenPageLimit(2);
        WebUtils.webSetting(webview, this);
        webview.loadUrl(Constant.VOTE_URL + SpUtils.getParam(this, Constant.USER_ID, ""));
    }


    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent)
    {
        if (keyCode == keyEvent.KEYCODE_BACK)
        {
            if (webview.canGoBack())
            {
                webview.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, keyEvent);

    }
}

