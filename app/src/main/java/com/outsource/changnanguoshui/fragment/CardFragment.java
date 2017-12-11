package com.outsource.changnanguoshui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.WeekAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.bean.WeekBean;
import com.outsource.changnanguoshui.utlis.DateUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/12/9.
 */

public class CardFragment extends BaseFragment implements AMap.OnMyLocationChangeListener, GeocodeSearch.OnGeocodeSearchListener
{
    @BindView(R.id.date_card)
    TextView dateCard;
    @BindView(R.id.week_list)
    RecyclerView weekList;
    @BindView(R.id.tiem_card)
    TextView tiemCard;
    @BindView(R.id.punch_clock)
    LinearLayout punchClock;
    @BindView(R.id.address_card)
    TextView addressCard;
    @BindView(R.id.map)
    MapView mMapView;
    private List<WeekBean> mData;
    private String[] WeeK = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private WeekAdapter adapter;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private GeocodeSearch geocoderSearch;
    private RegeocodeQuery query;
    private float distance;
    private LatLng latLng;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        mData = new ArrayList<>();
        weekList.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        adapter = new WeekAdapter(getActivity(), mData);
        weekList.setAdapter(adapter);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_card;
    }

    @Override
    protected void initData()
    {
        dateCard.setText(new DateTime().toString("yyyy-MM-dd"));
        tiemCard.setText(new DateTime().toString("HH:mm:SS"));
        getData();

    }

    private void getData()
    {
        Date WeekStart = DateUtils.getWeekStart();
        for (int i = 0; i < 7; i++)
        {
            String day = new DateTime(WeekStart).plusDays(i).toString("d");
            if (new DateTime().toString("d").equals(day))
            {
                mData.add(new WeekBean(WeeK[i], day, true));
            } else
            {
                mData.add(new WeekBean(WeeK[i], day, false));
            }

        }
        setLocation();
    }

    private void setLocation()
    {
        //初始化地图控制器对象
        if (aMap == null)
        {
            aMap = mMapView.getMap();
        }
        latLng = new LatLng(22.639475, 114.049557);//22.638475
        aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(60).
                fillColor(R.color.div).
                strokeColor(R.color.transparent).
                strokeWidth(1));
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.interval(5000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);
        myLocationStyle.strokeColor(0);//设置定位蓝点精度圆圈的边框颜色的方法。
        myLocationStyle.radiusFillColor(0);//设置定位蓝点精度圆圈的填充颜色的方法。
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.animateCamera(CameraUpdateFactory.zoomTo(18));//共分为 17 级，从 3 到 19。数字越大，展示的图面信息越精细。
        aMap.setOnMyLocationChangeListener(this);
        geocoderSearch = new GeocodeSearch(getActivity());
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    @OnClick(R.id.punch_clock)
    public void onViewClicked()
    {
        if (distance < 60)
        {
            Alert("打卡成功");
        } else
        {
            Alert("距离上班地点还有" + (int) (distance - 60) + "米，是否打卡");
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();//销毁地图

    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mMapView != null)
            mMapView.onResume();//重新绘制加载地图
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();  //暂停地图的绘制
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);//保存地图当前的状态
    }

    @Override
    public void onMyLocationChange(Location location)
    {
        distance = AMapUtils.calculateLineDistance(latLng, new LatLng(location.getLatitude(), location.getLongitude()));
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        query = new RegeocodeQuery(new LatLonPoint(location.getLatitude(), location.getLongitude()), 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i)
    {
        if (i == 1000)
        {
            addressCard.setText("您当前的位置：" + regeocodeResult.getRegeocodeAddress().getFormatAddress());
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i)
    {
    }
}
