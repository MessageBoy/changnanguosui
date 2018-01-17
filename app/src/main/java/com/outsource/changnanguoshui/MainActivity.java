package com.outsource.changnanguoshui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.outsource.changnanguoshui.activity.BirthdayActivity;
import com.outsource.changnanguoshui.activity.ShowUpdateActivity;
import com.outsource.changnanguoshui.application.BackHandledFragment;
import com.outsource.changnanguoshui.application.BackHandledInterface;
import com.outsource.changnanguoshui.application.PermissionsActivity;
import com.outsource.changnanguoshui.bean.CheckApkUpgradeBean;
import com.outsource.changnanguoshui.bean.GetIndexInfoNumBean;
import com.outsource.changnanguoshui.fragment.BusinessFragment;
import com.outsource.changnanguoshui.fragment.HomepageFragment;
import com.outsource.changnanguoshui.fragment.MyFragment;
import com.outsource.changnanguoshui.fragment.StudyFragment;
import com.outsource.changnanguoshui.utlis.BadgeView;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.outsource.changnanguoshui.fragment.HomepageFragment.formatBadgeNumber;

public class MainActivity extends PermissionsActivity implements RadioGroup.OnCheckedChangeListener, BackHandledInterface
{
    HomepageFragment homepageFragment;
    StudyFragment studyFragment;
    //    VoteFragment voteFragment;
    BusinessFragment businessFragment;
    MyFragment myFragment;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    int position = 0;
    @BindView(R.id.rb_study)
    RadioButton rbStudy;
    @BindView(R.id.rb_business)
    RadioButton rbBusiness;
    @BindView(R.id.rb_my)
    RadioButton rbMy;
    @BindView(R.id.num)
    View num;
    private BackHandledFragment mBackHandedFragment;
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
        isPermissionsAllGranted(Constant.permArray,
                Constant.QUEST_CODE_ALL);
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
        switch (checkedId)
        {
            case R.id.rb_home:
                position = 0;
                hideAllFragment(fTransaction);
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
                position = 1;
                hideAllFragment(fTransaction);
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
                Intent intent = new Intent(MainActivity.this, BirthdayActivity.class);
                startActivity(intent);
                switch (position)
                {
                    case 0:
                        rbHome.setChecked(true);
                        break;
                    case 1:
                        rbStudy.setChecked(true);
                        break;
                    case 2:
                        rbBusiness.setChecked(true);
                        break;
                    case 3:
                        rbMy.setChecked(true);
                        break;
                }
                break;
            case R.id.rb_business:
                position = 2;
                hideAllFragment(fTransaction);
                businessFragment = new BusinessFragment();
                fTransaction.add(R.id.fragment, businessFragment);

                break;
            case R.id.rb_my:
                position = 3;
                hideAllFragment(fTransaction);
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
//        if (voteFragment != null)
//            fragmentTransaction.hide(voteFragment);
        if (businessFragment != null)
            fragmentTransaction.remove(businessFragment);
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
                .execute(new GenericsCallback<CheckApkUpgradeBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(CheckApkUpgradeBean response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            if (vCode < response.getVcode())
                            {
                                Intent intent = new Intent(MainActivity.this, ShowUpdateActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("mData", response);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    }
                });

    }

    @OnClick(R.id.rb_vote)
    public void onViewClicked()
    {

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        GetIndexInfoNum();
    }

    private void GetIndexInfoNum()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetIndexInfoNum")
                .build()
                .execute(new GenericsCallback<GetIndexInfoNumBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(GetIndexInfoNumBean response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            if (homepageFragment != null)
                                homepageFragment.setNum(response.getList());
                            BadgeView badgeView = new BadgeView(MainActivity.this);
                            badgeView.setTargetView(num);
                            badgeView.setBadgeMargin(45, 5, 0, 0);
                            badgeView.setTextSize(9);
                            badgeView.setText(formatBadgeNumber(response.getList().get(8).getInfo_num()));
                        }

                    }
                });
    }

}
