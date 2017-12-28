package com.outsource.changnanguoshui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.bean.GetMyInfo;
import com.outsource.changnanguoshui.bean.SuccessBean;
import com.outsource.changnanguoshui.utlis.BitmapToBase64Util;
import com.outsource.changnanguoshui.utlis.CircleTransform;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SelectPicPopupWindow;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/17.
 */

public class BasicInformationActivity extends BaseActivity
{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.icon_am)
    ImageView iconAm;
    @BindView(R.id.xing_am)
    EditText xingAm;
    @BindView(R.id.ming_am)
    EditText mingAm;
    @BindView(R.id.email_am)
    EditText emailAm;
    @BindView(R.id.phone_am)
    EditText phoneAm;
    @BindView(R.id.company_am)
    EditText companyAm;
    @BindView(R.id.branch_am)
    EditText branchAm;

    private static final String IMAGE_FILE_NAME = "header.jpg";
    private Bitmap bmp;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_basic_information);
    }

    @Override
    protected void initData()
    {
        title.setText("基本信息");
        getData();
    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetMyInfo")
                .build()
                .execute(new GenericsCallback<GetMyInfo>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(GetMyInfo response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            Picasso.with(BasicInformationActivity.this)
                                    .load(Constant.DOMAIN_NAME + response.getPic_url())
                                    .placeholder(R.mipmap.male_head)
                                    .transform(new CircleTransform())
                                    .into(iconAm);
                            xingAm.setText(response.getReal_name());
                            mingAm.setText(response.getSex());
                            phoneAm.setText(response.getMobile());
                            emailAm.setText(response.getEmail());
                            companyAm.setText(response.getDep_name());
                            branchAm.setText(response.getDeplist().get(0).getDep_name());
                        }
                    }
                });
    }


    @OnClick({R.id.back, R.id.xgxx,R.id.icon_am})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.icon_am:
                new SelectPicPopupWindow(BasicInformationActivity.this, view);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.xgxx:
                modifyInfo();
                break;
        }
    }

    private void modifyInfo()
    {
        String imgbase64="";
        if (bmp != null) {
            BitmapToBase64Util bitBase64 = new BitmapToBase64Util();
            imgbase64 = bitBase64.bitmapToBase64(bmp);
        }
        OkHttpUtils
                .post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(this, Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(this, Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "SaveMyInfo")
                .addParams("pic_path", imgbase64)
                .addParams("real_name", xingAm.getText().toString())
                .addParams("sex", mingAm.getText().toString())
                .addParams("id_card", "")
                .addParams("birthday", "")
                .addParams("telephone", "")
                .addParams("mobile", phoneAm.getText().toString())
                .addParams("email", emailAm.getText().toString())
                .build()
                .execute(new GenericsCallback<SuccessBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(SuccessBean response, int id)
                    {
                        Alert(response.getMsg());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG)
                                .show();
                    }
                    break;

                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }


    private void showResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            bmp = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(getResources(), bmp);
            iconAm.setImageDrawable(drawable);
        }
    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }

}
