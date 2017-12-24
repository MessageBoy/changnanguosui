package com.outsource.changnanguoshui.utlis;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.outsource.changnanguoshui.R;

import java.io.File;


// 自定义popuwindows
public class SelectPicPopupWindow extends PopupWindow implements
        OnClickListener
{
    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    Context mContext;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;

    @SuppressWarnings("deprecation")
    public SelectPicPopupWindow(Context mContext, View parent)
    {
        this.mContext = mContext;
        View view = View.inflate(mContext, R.layout.popwin_photo, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in_2));

        setWidth(LayoutParams.FILL_PARENT);
        setHeight(LayoutParams.FILL_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        btn_take_photo = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        btn_pick_photo = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        btn_cancel = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        btn_cancel.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.item_popupwindows_camera:
                try
                {
                    // 拍照我们用Action为MediaStore.ACTION_IMAGE_CAPTURE，
                    // 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                    // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    // ((Activity) mContext).startActivityForResult(intent, 1);
                    if (isSdcardExisting())
                    {
                        Intent cameraIntent = new Intent(
                                "android.media.action.IMAGE_CAPTURE");
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                getImageUri());
                        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                        ((Activity) mContext).startActivityForResult(cameraIntent,
                                CAMERA_REQUEST_CODE);
                    } else
                    {
                        Toast.makeText(v.getContext(), "请插入sd卡", Toast.LENGTH_LONG)
                                .show();
                    }
                    dismiss();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.item_popupwindows_Photo:
                try
                {
                    // 选择照片的时候也一样，我们用Action为Intent.ACTION_GET_CONTENT，
                    // 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    galleryIntent.setType("image/*");
                    ((Activity) mContext).startActivityForResult(galleryIntent,
                            IMAGE_REQUEST_CODE);
                    dismiss();
                } catch (ActivityNotFoundException e)
                {

                }
                break;
            case R.id.item_popupwindows_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    private Uri getImageUri()
    {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }

    private boolean isSdcardExisting()
    {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED))
        {
            return true;
        } else
        {
            return false;
        }
    }
}
