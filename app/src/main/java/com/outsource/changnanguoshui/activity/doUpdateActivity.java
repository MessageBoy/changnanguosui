package com.outsource.changnanguoshui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 开始更新界面
 *
 * @author xiaolei
 */
public class doUpdateActivity extends Activity
{

    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.textview)
    TextView textview;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_update);
        ButterKnife.bind(this);
        progress.setMax(100);
        progress.setProgress(0);
        textview.setText("0%");
        downloadFile();
    }


    @OnClick(R.id.cancel)
    public void onViewClicked()
    {
        finish();
    }

    private void downloadFile()
    {
        OkHttpUtils
                .get()
                .url(Constant.DOMAIN_NAME + getIntent().getStringExtra("url"))
                .tag(this)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "先锋e线.apk")
                {
                    @Override
                    public void inProgress(float progres, long total, int id)
                    {
                        super.inProgress(progres, total, id);
                        progress.setProgress((int) (progres * 100));
                        textview.setText((int) (progres * 100) + "%");
                    }

                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Log.e("Exception", e.getMessage());
                    }

                    @Override
                    public void onResponse(File response, int id)
                    {
                        Log.e("Exception", response.getPath());
                        installApk(response);
                    }
                });

    }

    // 安装apk
    protected void installApk(File file)
    {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

}
