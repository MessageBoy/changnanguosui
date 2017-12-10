package com.outsource.changnanguoshui.utlis;

import android.widget.ListView;

/**
 * Created by thinkpad on 2016/10/18.
 */

public class NoScrollListView extends ListView
{


    public NoScrollListView(android.content.Context context, android.util.AttributeSet attrs){
        super(context, attrs);
    }

    /**
     * 设置不滚动 
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}

