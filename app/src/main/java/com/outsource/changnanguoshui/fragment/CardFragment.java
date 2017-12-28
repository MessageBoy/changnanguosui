package com.outsource.changnanguoshui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.outsource.changnanguoshui.Constant;
import com.outsource.changnanguoshui.R;
import com.outsource.changnanguoshui.adapter.WeekAdapter;
import com.outsource.changnanguoshui.application.BaseFragment;
import com.outsource.changnanguoshui.bean.GetPunchSetBean;
import com.outsource.changnanguoshui.bean.WeekBean;
import com.outsource.changnanguoshui.utlis.DateUtils;
import com.outsource.changnanguoshui.utlis.GPSUtil;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


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
    int mapRange = 50;//定位范围，数字，单位是米
    String time1_str = "8:00-9:05";//上午上班打卡时间段  格式：8:00-9:05
    String time2_str = "11:55-12:49";// 上午下班打卡时间段
    String time3_str = "12:50-13:35";//  下午上班打卡时间段
    String time4_str = "16:55-18:00";// 下午下班打卡时间段
    String currentDate;//当前日期
    String intro = "";

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
        currentDate = new DateTime().toString("yyyy-MM-dd");
        tiemCard.setText(new DateTime().toString("HH:mm:SS"));
        dateCard.setText(currentDate);
        getPunchSet();
        getData();
        setLocation();


    }

    private void getData()
    {
        DateTime WeekStart = new DateTime().dayOfWeek().withMinimumValue();
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

    }

    private void setLocation()
    {
        //初始化地图控制器对象
        if (aMap == null)
        {
            aMap = mMapView.getMap();
        }

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
        if (DateUtils.isBefore(currentDate + "T" + time2_str.substring(0, time2_str.indexOf("-"))))
        {
            getDate(time1_str);
        } else if (DateUtils.isBefore(currentDate + "T" + time3_str.substring(0, time2_str.indexOf("-"))))
        {
            getDate(time2_str);
        } else if (DateUtils.isBefore(currentDate + "T" + time4_str.substring(0, time2_str.indexOf("-"))))
        {
            getDate(time3_str);
        } else
        {
            getDate(time4_str);
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
            addressCard.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i)
    {
    }

    private void getPunchSet()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetPunchSet")
                .build()
                .execute(new GenericsCallback<GetPunchSetBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(GetPunchSetBean response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            String[] map = response.getMap().split(",");
                            double[] gps = GPSUtil.bd09_To_Gcj02(Double.valueOf(map[1]), Double.valueOf(map[0]));
                            mapRange = response.getMap_range();
                            time1_str = response.getTime1_str();
                            time2_str = response.getTime2_str();
                            time3_str = response.getTime3_str();
                            time4_str = response.getTime4_str();
                            latLng = new LatLng(gps[0], gps[1]);//22.638475
                            aMap.addCircle(new CircleOptions()
                                    .center(latLng)
                                    .radius(mapRange)
                                    .fillColor(R.color.div)
                                    .strokeColor(R.color.transparent)
                                    .strokeWidth(1));
                        }
                    }
                });
    }

    private void getDate(String time)
    {
        if (distance < mapRange)
        {
            String startTime = currentDate + "T" + time.substring(0, time.indexOf("-"));
            String endTime = currentDate + "T" + time.substring(time.indexOf("-") + 1, time.length());
            if (DateUtils.isAfter(startTime) && DateUtils.isAfter(endTime))
            {
                savePunch("1");
            } else
            {
                showOneDialog("1", 0);

            }
        } else
        {
            showOneDialog("0", (int) (distance - mapRange));
        }

    }


    private void savePunch(String range)
    {
        OkHttpUtils
                .post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, SpUtils.getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, SpUtils.getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "SavePunch")
                .addParams("add_time", new DateTime().toString("yyyy-MM-dd HH:mm:ss"))
                .addParams("range", range)
                .addParams("intro", intro)
                .build()
                .execute(new GenericsCallback<GetPunchSetBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(GetPunchSetBean response, int id)
                    {
                        Alert(response.getMsg());
                    }
                });
    }

    private void showOneDialog(final String range, int mapRange)
    {
        final AlertDialog build = new AlertDialog.Builder(getActivity()).create();
        View view = getActivity().getLayoutInflater().inflate(R.layout.splash_dialog, null);
        build.setView(view);
        build.show();
        Button cancel = (Button) view.findViewById(R.id.cancel);
        Button confirm = (Button) view.findViewById(R.id.confirm);
        final EditText message = (EditText) view.findViewById(R.id.message);
        TextView sj = (TextView) view.findViewById(R.id.sj);
        TextView fw = (TextView) view.findViewById(R.id.fw);
        sj.setText("签到时间：" + new DateTime().toString("HH:mm:ss"));
        fw.setText(mapRange > 0 ? "距离考勤范围还有：" + mapRange + "米" : "已在考勤范围内");
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                build.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intro = message.getText().toString();
                if (TextUtils.isEmpty(message.getText().toString()))
                {
                    Alert("请填写备注");
                    return;
                }
                savePunch(range);
                build.dismiss();
            }
        });
    }
}
