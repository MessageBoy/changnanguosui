package com.outsource.changnanguoshui.utlis;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.outsource.changnanguoshui.LoginActivity;

/**
 * Created by Administrator on 2017/12/28.
 */

public class LogoutDialog
{
    public static void popAlterDialog(final Context context)
    {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("此功能需登录后才能使用，是否登录")
                .setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("登录", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        dialog.dismiss();
                    }
                }).create().show();
    }
}
