package com.outsource.changnanguoshui.utlis;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.HeaderAdapter;
import com.outsource.changnanguoshui.bean.GetSlidesBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyViewPager
{
    private Context context;
    private ViewPager viewPager;
    private LinearLayout llHottestIndicator;
    private static final int UPTATE_VIEWPAGER = 0;
    // 设置当前 第几个图片 被选中
    private int autoCurrIndex = 0;
    private ImageView[] mBottomImages;// 底部只是当前页面的小圆点
    private Timer timer = new Timer(); // 为了方便取消定时轮播，将 Timer 设为全局
    private List<GetSlidesBean.ListBean> mData;
    private ArrayList<View> views;
    // 定时轮播图片，需要在主线程里面修改 UI
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case UPTATE_VIEWPAGER:
                    if (msg.arg1 != 0)
                    {
                        viewPager.setCurrentItem(msg.arg1);
                    } else
                    {
                        // false 当从末页调到首页是，不显示翻页动画效果，
                        viewPager.setCurrentItem(msg.arg1, false);
                    }
                    break;
            }
        }
    };

    public MyViewPager(Context context, ViewPager viewPager,
                       LinearLayout llHottestIndicator, List<GetSlidesBean.ListBean> pic)
    {
        this.context = context;
        this.viewPager = viewPager;
        this.llHottestIndicator = llHottestIndicator;
        mData = new ArrayList<>();
        if (pic != null && pic.size() > 0)
            mData.addAll(pic);
        setUpViewPager();
    }

    private void setUpViewPager()
    {

        LayoutInflater mInflater = LayoutInflater.from(context);
        views = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++)
        {
            View view = mInflater.inflate(R.layout.itme_imageview, null);
            ImageView img = (ImageView) view.findViewById(R.id.item_img);
            Picasso.with(context).load(Constant.DOMAIN_NAME + mData.get(i).getPic_url())
                    .into(img);
            views.add(view);
        }

        HeaderAdapter imageAdapter = new HeaderAdapter(views);
        viewPager.setAdapter(imageAdapter);

        // 创建底部指示位置的导航栏
        mBottomImages = new ImageView[views.size()];

        for (int i = 0; i < mBottomImages.length; i++)
        {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    35, 35);
            params.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(params);
            if (i == 0)
            {
                imageView.setBackgroundResource(R.mipmap.indicator_select);
            } else
            {
                imageView
                        .setBackgroundResource(R.mipmap.indicator_not_select);
            }

            mBottomImages[i] = imageView;
            // 把指示作用的原点图片加入底部的视图中
            llHottestIndicator.addView(mBottomImages[i]);

        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            // 图片左右滑动时候，将当前页的圆点图片设为选中状态
            @Override
            public void onPageSelected(int position)
            {
                // 一定几个图片，几个圆点，但注意是从0开始的
                int total = mBottomImages.length;
                for (int j = 0; j < total; j++)
                {
                    if (j == position)
                    {
                        mBottomImages[j]
                                .setBackgroundResource(R.mipmap.indicator_select);
                    } else
                    {
                        mBottomImages[j]
                                .setBackgroundResource(R.mipmap.indicator_not_select);
                    }
                }

                // 设置全局变量，currentIndex为选中图标的 index
                autoCurrIndex = position;
            }

            @Override
            public void onPageScrolled(int i, float v, int i1)
            {
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });

        // 设置自动轮播图片，5s后执行，周期是5s
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                Message message = new Message();
                message.what = UPTATE_VIEWPAGER;
                if (autoCurrIndex == views.size() - 1)
                {
                    autoCurrIndex = -1;
                }
                message.arg1 = autoCurrIndex + 1;
                mHandler.sendMessage(message);
            }
        }, 5000, 5000);
    }
}
