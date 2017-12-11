package com.outsource.changnanguoshui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/8.
 */

public class OnlineCardFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener
{

    CardFragment cardFragment;
    HistoryFragment historyFragment;

    @BindView(R.id.rb_card)
    RadioButton rbCard;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {

    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_online_card;
    }


    @Override
    protected void initData()
    {
        radioGroup.setOnCheckedChangeListener(this);
        rbCard.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        FragmentTransaction fTransaction = getChildFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId)
        {

            case R.id.rb_card:
                if (cardFragment == null)
                {
                    cardFragment = new CardFragment();
                    fTransaction.add(R.id.fragment, cardFragment);
                } else
                {
                    fTransaction.show(cardFragment);
                }
                break;
            case R.id.rb_history:
                if (historyFragment == null)
                {
                    historyFragment = new HistoryFragment();
                    fTransaction.add(R.id.fragment, historyFragment);
                } else
                {
                    fTransaction.show(historyFragment);
                }
                break;
        }
        fTransaction.commit();
    }

    // 隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction)
    {

        if (cardFragment != null)
            fragmentTransaction.hide(cardFragment);
        if (historyFragment != null)
            fragmentTransaction.hide(historyFragment);
    }


}
