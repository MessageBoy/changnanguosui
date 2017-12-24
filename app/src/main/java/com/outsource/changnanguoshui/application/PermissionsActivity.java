package com.outsource.changnanguoshui.application;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.outsource.changnanguoshui.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/24.
 */

public abstract class PermissionsActivity extends BaseActivity
{


    protected boolean isPermissionGranted(String permissionName, int questCode)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return true;
        }
        //判断是否需要请求允许权限  
        int hasPermision = checkSelfPermission(permissionName);
        if (hasPermision != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{permissionName}, questCode);
            return false;
        }
        return true;
    }

    protected boolean isPermissionsAllGranted(String[] permArray, int questCode)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return true;
        }
        //获得批量请求但被禁止的权限列表  
        List<String> deniedPerms = new ArrayList<String>();
        for (int i = 0; permArray != null && i < permArray.length; i++)
        {
            if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(permArray[i]))
            {
                deniedPerms.add(permArray[i]);
            }
        }
        //进行批量请求  
        int denyPermNum = deniedPerms.size();
        if (denyPermNum != 0)
        {
            requestPermissions(deniedPerms.toArray(new String[denyPermNum]), questCode);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults)
    {
        if (grantResults.length == 0)
        {
            return;
        }
        switch (requestCode)
        {
            case Constant.QUEST_CODE_LOCTION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                {
                    popAlterDialog("位置", "位置信息权限被禁止，将导致定位失败。。是否开启该权限？(步骤：应用信息->权限->'勾选'位置)");
                } else
                {
                    showShortMsg("恭喜，用户已经授予位置权限");
                }
                break;
        
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
                break;
        }
    }

    private void doPermissionAll(String[] permissions, int[] grantResults)
    {
        int grantedPermNum = 0;
        int totalPermissons = permissions.length;
        int totalResults = grantResults.length;
        if (totalPermissons == 0 || totalResults == 0)
        {
            return;
        }
        Map<String, Integer> permResults = new HashMap<String, Integer>();
        //初始化Map容器，用于判断哪些权限被授予  
        for (String perm : Constant.permArray)
        {
            permResults.put(perm, PackageManager.PERMISSION_DENIED);
        }
        //根据授权的数目和请求授权的数目是否相等来判断是否全部授予权限  
        for (int i = 0; i < totalResults; i++)
        {
            permResults.put(permissions[i], grantResults[i]);
            if (permResults.get(permissions[i]) == PackageManager.PERMISSION_GRANTED)
            {
                grantedPermNum++;
            }
            Log.d("Debug", "权限：" + permissions[i] + "-->" + grantResults[i]);
        }
        if (grantedPermNum == totalPermissons)
        {
            //用于授予全部权限  
        } else
        {
            showShortMsg("批量申请权限失败，将会影响正常使用！");
        }
    }

    private void showShortMsg(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void popAlterDialog(final String msgFlg, String msgInfo)
    {
        new AlertDialog
                .Builder(PermissionsActivity.this)
                .setTitle("使用警告")
                .setMessage(msgInfo)
                .setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("设置", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //前往应用详情界面  
                        try
                        {
                            Uri packUri = Uri.parse("package:" + getPackageName());
                            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packUri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            PermissionsActivity.this.startActivity(intent);
                        } catch (Exception e)
                        {
                            showShortMsg("跳转失败");
                        }
                        dialog.dismiss();
                    }
                }).create().show();
    }
}
