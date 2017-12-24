package com.outsource.changnanguoshui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.outsource.changnanguoshui.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    private OkHttpClient client = new OkHttpClient();

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            progress.setProgress(progress.getProgress() + ((Integer) msg.obj));
            Log.e("setProgress", progress.getProgress() + "");
            int max = progress.getMax();
            int len = progress.getProgress();
            textview.setText((int) (((len + 0.1) / max) * 100) + "%");
        }

    };


    private boolean hasCancle = false; // 是否点了取消

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_update);
        ButterKnife.bind(this);

        progress.setProgress(0);
        textview.setText("0%");
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    loadDatas();
                } catch (Exception e)
                {
                }
            }
        }.start();
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

    private void loadDatas()
    {
        // 封装请求
        Log.e("http", getIntent().getStringExtra("url"));
        Request request = new Request.Builder()
                // 下载地址
                .url(getIntent().getStringExtra("url")).build();
        // 发送异步请求
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        finish();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                // 将返回结果转化为流，并写入文件
                int len;
                byte[] buf = new byte[1024];
                InputStream inputStream = response.body().byteStream();
                long size = response.body().contentLength();
                Log.e("setMaxsize", (int) size + "");
                progress.setMax((int) size);
                // 可以在这里自定义路径
                final File file1 = new File(getSDPath(), "com_northdoo_futain.apk");

                FileOutputStream fileOutputStream = new FileOutputStream(file1);

                while ((len = inputStream.read(buf)) != -1)
                {
                    if (!hasCancle)
                    {
                        fileOutputStream.write(buf, 0, len);
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.obj = len;
                        handler.sendMessage(message);
                    } else
                    {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        inputStream.close();
                        return;
                    }
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
                if (size > 0)
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.setDataAndType(Uri.fromFile(file1), "application/vnd.android.package-archive");
//                            startActivity(intent);
//                            android.os.Process.killProcess(android.os.Process.myPid());

                            installApk(file1);
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            //判断是否是AndroidN以及更高的版本
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                            {
//                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                Uri contentUri = FileProvider.getUriForFile(doUpdateActivity.this, "com.northdoo_oa.gysjtl.fileProvider", file1);
//                                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//                            } else
//                            {
//                                intent.setDataAndType(Uri.fromFile(file1), "application/vnd.android.package-archive");
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            }
//                            startActivity(intent);
//                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    });
                } else
                {
                    finish();
                }
            }
        });
    }

    public File getSDPath()
    {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir;
    }


    @OnClick(R.id.cancel)
    public void onViewClicked()
    {
        hasCancle = true;
        finish();
    }
}
