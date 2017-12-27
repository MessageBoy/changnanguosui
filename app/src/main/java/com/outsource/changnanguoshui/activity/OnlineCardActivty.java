package com.outsource.changnanguoshui.activity;

import android.Manifest;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.PermissionsActivity;
import com.outsource.changnanguoshui.fragment.CardFragment;
import com.outsource.changnanguoshui.fragment.HistoryFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/24.
 */

public class OnlineCardActivty extends PermissionsActivity implements RadioGroup.OnCheckedChangeListener
{
    CardFragment cardFragment;
    HistoryFragment historyFragment;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rb_card)
    RadioButton rbCard;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @Override
    protected void initView()
    {
        setContentView(R.layout.fragment_online_card);
        isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION,
                Constant.QUEST_CODE_LOCTION);
    }

    @Override
    protected void initData()
    {
        title.setText("无线签到");
        radioGroup.setOnCheckedChangeListener(this);
        rbCard.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
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

    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }
    
    
}
