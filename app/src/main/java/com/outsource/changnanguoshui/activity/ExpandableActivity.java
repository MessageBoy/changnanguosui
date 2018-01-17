package com.outsource.changnanguoshui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.application.BaseActivity;
import com.outsource.changnanguoshui.application.MyApplication;
import com.outsource.changnanguoshui.bean.GetDepartmentBean;
import com.outsource.changnanguoshui.utlis.CircleTransform;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class ExpandableActivity extends BaseActivity
{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.listview)
    ExpandableListView listview;

    private MyExpandableListViewAdapter adapter;
    private List<GetDepartmentBean.ListBean> mData;


    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_expandable);
    }

    @Override
    protected void initData()
    {
        title.setText("审批人");
        mData = new ArrayList<>();
        adapter = new MyExpandableListViewAdapter();
        listview.setAdapter(adapter);
        listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int parentPos, int childPos, long l)
            {
                Intent intent = new Intent();
                intent.putExtra("user_id", mData.get(parentPos).getUserlist().get(childPos).getUser_id());
                intent.putExtra("user_name", mData.get(parentPos).getUserlist().get(childPos).getReal_name());
                setResult(RESULT_OK, intent);
                finish();
                return true;
            }
        });

        getData();
    }

    private void getData()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.ACT, "GetDepartment")
                .build()
                .execute(new GenericsCallback<GetDepartmentBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(GetDepartmentBean response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            mData.addAll(response.getList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    @OnClick(R.id.back)
    public void onViewClicked()
    {
        finish();
    }

    private class MyExpandableListViewAdapter extends BaseExpandableListAdapter
    {

        //  获得某个父项的某个子项
        @Override
        public Object getChild(int parentPos, int childPos)
        {
            return mData.get(parentPos).getUserlist().get(childPos);
        }

        //  获得父项的数量
        @Override
        public int getGroupCount()
        {

            return mData.size();
        }

        //  获得某个父项的子项数目
        @Override
        public int getChildrenCount(int parentPos)
        {

            return mData.get(parentPos).getUserlist().size();
        }

        //  获得某个父项
        @Override
        public Object getGroup(int parentPos)
        {
            return mData.get(parentPos);
        }

        //  获得某个父项的id
        @Override
        public long getGroupId(int parentPos)
        {
            return parentPos;
        }

        //  获得某个父项的某个子项的id
        @Override
        public long getChildId(int parentPos, int childPos)
        {
            return childPos;
        }

        //  按函数的名字来理解应该是是否具有稳定的id，这个函数目前一直都是返回false，没有去改动过
        @Override
        public boolean hasStableIds()
        {
            return false;
        }

        //  获得父项显示的view
        @Override
        public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup)
        {
            if (view == null)
            {
                LayoutInflater inflater = (LayoutInflater) ExpandableActivity
                        .this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.parent_item, null);
            }
            TextView text = (TextView) view.findViewById(R.id.parent_title);
            text.setText(mData.get(parentPos).getDep_name());
            return view;
        }

        //  获得子项显示的view
        @Override
        public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup)
        {
            if (view == null)
            {
                LayoutInflater inflater = (LayoutInflater) ExpandableActivity
                        .this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.child_item, null);
            }
            ImageView icon = (ImageView) view.findViewById(R.id.child_icon);
            TextView text = (TextView) view.findViewById(R.id.child_title);
            text.setText(mData.get(parentPos).getUserlist().get(childPos).getReal_name());
            Picasso.with(MyApplication.getInstance())
                    .load(Constant.DOMAIN_NAME + mData.get(parentPos).getUserlist().get(childPos).getPic_url())
                    .transform(new CircleTransform())
                    .into(icon);
            return view;
        }

        //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
        @Override
        public boolean isChildSelectable(int i, int i1)
        {
            return true;
        }
    }
}