package com.outsource.changnanguoshui.application;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity
{
    protected Handler handler;
    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        dialog = null;
        // 初始化ui

        initView();
        ButterKnife.bind(this);
        // 初始化数据
        initData();

    }

    /**
     * 初始化ui
     **/
    protected abstract void initView();

    /**
     * 初始化数据
     **/
    protected abstract void initData();


    public void startActivity(Class<? extends Activity> activity)
    {
        this.startActivity(activity, (Intent) null);
    }

    public void startActivity(Class<? extends Activity> activity, Intent intent)
    {
        Intent intent1 = intent == null ? new Intent() : new Intent(intent);
        intent1.setClass(this, activity);
        this.startActivity(intent1);
    }


    public void Alert(final Object msg)
    {
        this.handler.post(new Runnable()
        {
            public void run()
            {
                Toast.makeText(BaseActivity.this.getApplicationContext(), msg == null ? "" + null : msg.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AlertDialog()
    {
        this.AlertDialog("正在加载..");
    }

    public void AlertDialog(String msg)
    {
        if (this.dialog == null)
        {

            this.dialog = new ProgressDialog(this);
            this.dialog.setCanceledOnTouchOutside(false);
        }

        this.dialog.setMessage(msg);
        this.dialog.show();
    }
}