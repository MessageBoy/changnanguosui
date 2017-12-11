package com.outsource.changnanguoshui;

import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.fragment.BusinessFragment;
import com.outsource.changnanguoshui.fragment.HomepageFragment;
import com.outsource.changnanguoshui.fragment.MyFragment;
import com.outsource.changnanguoshui.fragment.StudyFragment;
import com.outsource.changnanguoshui.fragment.VoteFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener
{
    HomepageFragment homepageFragment;
    StudyFragment studyFragment;
    VoteFragment voteFragment;
    BusinessFragment businessFragment;
    MyFragment myFragment;

    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;


    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData()
    {
        radioGroup.setOnCheckedChangeListener(this);
        rbHome.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId)
        {
            case R.id.rb_home:
                if (homepageFragment == null)
                {
                    homepageFragment = new HomepageFragment();
                    fTransaction.add(R.id.fragment, homepageFragment);
                } else
                {
                    fTransaction.show(homepageFragment);
                }
                break;
            case R.id.rb_study:
                if (studyFragment == null)
                {
                    studyFragment = new StudyFragment();
                    fTransaction.add(R.id.fragment, studyFragment);
                } else
                {
                    fTransaction.show(studyFragment);
                }
                break;
            case R.id.rb_vote:
                if (voteFragment == null)
                {
                    voteFragment = new VoteFragment();
                    fTransaction.add(R.id.fragment, voteFragment);
                } else
                {
                    fTransaction.show(voteFragment);
                }

                break;
            case R.id.rb_business:
                if (businessFragment == null)
                {
                    businessFragment = new BusinessFragment();
                    fTransaction.add(R.id.fragment, businessFragment);
                } else
                {
                    fTransaction.show(businessFragment);
                }
                break;
            case R.id.rb_my:
                if (myFragment == null)
                {
                    myFragment = new MyFragment();
                    fTransaction.add(R.id.fragment, myFragment);
                } else
                {
                    fTransaction.show(myFragment);
                }
                break;
        }
        fTransaction.commit();
    }

    // 隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction)
    {
        if (homepageFragment != null)
            fragmentTransaction.hide(homepageFragment);
        if (studyFragment != null)
            fragmentTransaction.hide(studyFragment);
        if (voteFragment != null)
            fragmentTransaction.hide(voteFragment);
        if (businessFragment != null)
            fragmentTransaction.hide(businessFragment);
        if (myFragment != null)
            fragmentTransaction.hide(myFragment);
    }

    
}
