package com.outsource.changnanguoshui.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.TabAdapter;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.fragment.LearningQueryFragment;
import com.outsource.changnanguoshui.utlis.DribSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/17.
 */

public class LearningQueryActivity extends BaseActivity
{
    @BindView(R.id.editview)
    EditText editText;
    @BindView(R.id.dribSearchView)
    DribSearchView dribSearchView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private List<String> mPageTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TabAdapter mAdapter;
    private boolean isState = false;
    onAfterTextChanged afterTextChanged;
    private int position = 0;
    private InputMethodManager imm;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_learning_query);
    }

    @Override
    protected void initData()
    {
        title.setText("内容查询");
        mPageTitleList.add("未学");
        mPageTitleList.add("已学");
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
        mFragmentList.add(new LearningQueryFragment().newInstance(0));
        mFragmentList.add(new LearningQueryFragment().newInstance(1));
        mAdapter = new TabAdapter(this, getSupportFragmentManager(),
                mFragmentList, mPageTitleList);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(getIntent().getIntExtra("position", 0)).select();
        viewPager.setOffscreenPageLimit(1);
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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int positions)
            {
                position = positions;
                afterTextChanged = (LearningQueryFragment) mFragmentList.get(position);
                afterTextChanged.setDate(editText.getText().toString().trim());
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

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
                afterTextChanged = (LearningQueryFragment) mFragmentList.get(position);
                afterTextChanged.setDate(editText.getText().toString().trim());
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

    public interface onAfterTextChanged
    {
        void setDate(String a);
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
}
