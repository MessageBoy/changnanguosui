package com.outsource.changnanguoshui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.outsource.changnanguoshui.activity.ShowUpdateActivity;
import com.outsource.changnanguoshui.application.BackHandledFragment;
import com.outsource.changnanguoshui.application.BackHandledInterface;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.StudyBean;
import com.outsource.changnanguoshui.fragment.BusinessFragment;
import com.outsource.changnanguoshui.fragment.HomepageFragment;
import com.outsource.changnanguoshui.fragment.MyFragment;
import com.outsource.changnanguoshui.fragment.StudyFragment;
import com.outsource.changnanguoshui.fragment.VoteFragment;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import okhttp3.Call;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, BackHandledInterface
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

    private BackHandledFragment mBackHandedFragment;
    private boolean hadIntercept;
    long firstTime = 0;
    int vCode = 1;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData()
    {
        try
        {
            PackageInfo packages = getPackageManager().getPackageInfo("com.northdoo.luohu", PackageManager.GET_CONFIGURATIONS);
            vCode = packages.versionCode;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        radioGroup.setOnCheckedChangeListener(this);
        rbHome.setChecked(true);
        getData();
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

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment)
    {
        this.mBackHandedFragment = selectedFragment;
    }

    @Override
    public void onBackPressed()
    {
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed())
        {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000)
                {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                } else
                {
                    System.exit(0);
                }
            } else
            {
                getSupportFragmentManager().popBackStack(); //fragment 出栈  
            }
        }
    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams("plat", 1 + "")
                .addParams(Constant.ACT, "CheckApkUpgrade")
                .build()
                .execute(new GenericsCallback<StudyBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(StudyBean response, int id)
                    {
//                        if (response.getStatus() == 1)
//                        {
//                            if (vCode < Integer.parseInt("1"))
//                            {
                        Intent intent = new Intent(MainActivity.this, ShowUpdateActivity.class);
                        Bundle bundle = new Bundle();
                        intent.putExtras(bundle);
                        startActivity(intent);
//                        }
//                    }
                    }
                });

    }
}
