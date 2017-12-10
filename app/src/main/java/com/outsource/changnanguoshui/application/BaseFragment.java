package com.outsource.changnanguoshui.application;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment
{
    View view;
    Unbinder unbinder;
    protected Handler handler;
    protected ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        handler = new Handler();
        dialog = null;
        initView(view, savedInstanceState);
        initData();
        this.view = view;
        return view;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract int getLayoutId();


    protected abstract void initData();

    public void startActivity(Class<? extends Activity> activity)
    {
        this.startActivity(activity, null);
    }

    public void startActivity(Class<? extends Activity> activity, Intent intent)
    {
        Intent intent1 = intent == null ? new Intent() : new Intent(intent);
        intent1.setClass(getActivity(), activity);
        this.startActivity(intent1);
    }


    public void Alert(final Object msg)
    {
        this.handler.post(new Runnable()
        {
            public void run()
            {
                Toast.makeText(BaseFragment.this.getActivity(), msg == null ? "" + null : msg.toString(), Toast.LENGTH_SHORT).show();
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
            if (this.dialog == null)
            {
                this.dialog = new ProgressDialog(getActivity());
                this.dialog.setCanceledOnTouchOutside(false);
            }

            this.dialog.setMessage(msg);
            this.dialog.show();
        }

        this.dialog.setMessage(msg);
        this.dialog.show();
    }
}