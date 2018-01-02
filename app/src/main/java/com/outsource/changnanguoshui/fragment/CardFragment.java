package com.outsource.changnanguoshui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.outsource.changnanguoshui.bean.AmapBean;
import com.outsource.changnanguoshui.bean.GetPunchSetBean;
import com.outsource.changnanguoshui.bean.WeekBean;
import com.outsource.changnanguoshui.utlis.DateUtils;
import com.outsource.changnanguoshui.utlis.GenericsCallback;
import com.outsource.changnanguoshui.utlis.JsonGenerics;
import com.outsource.changnanguoshui.utlis.SpUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.outsource.changnanguoshui.utlis.SpUtils.getParam;


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
    @BindView(R.id.yidaka)
    TextView yidaka;
    @BindView(R.id.daka)
    TextView daka;
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
    private int state = 0;
    private String isCard;
    private static final int ZHENGCHANG = 1002;
    private static final int YIDAKA = 1004;
    private static final int CHAOCHU = 1003;
    private static final int UPDATE_TIME = 1001;
    private boolean isStop = true;

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

        dateCard.setText(currentDate);

        getData();
        AlertDialog();
        getPunchSet();
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
        if (isCard.equals("flase"))
        {
            if (state != 0)
            {
                if (distance < mapRange)
                {
                    savePunch("1");
                } else
                {
                    showOneDialog((int) (distance - mapRange));
                }
            }
        }
    }

    private boolean getDate(String time)
    {

        String startTime = currentDate + "T" + time.substring(0, time.indexOf("-"));
        String endTime = currentDate + "T" + time.substring(time.indexOf("-") + 1, time.length());
        if (DateUtils.isAfter(startTime) && DateUtils.isBefore(endTime))
        {
            return true;
        }
        state = 0;
        handlers.sendEmptyMessage(YIDAKA);
        return false;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();//销毁地图
        isStop = false;

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
        if (isCard.equals("flase") && state != 0 && distance > mapRange)
        {
            handlers.sendEmptyMessage(CHAOCHU);
        } else if (isCard.equals("flase") && state != 0 && distance < mapRange)
        {
            handlers.sendEmptyMessage(ZHENGCHANG);
        }
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
                .addParams(Constant.USER_ID, getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, getParam(getActivity(), Constant.TOKEN, "").toString())
                .addParams(Constant.ACT, "GetPunchSet")
                .build()
                .execute(new GenericsCallback<GetPunchSetBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(GetPunchSetBean response, int id)
                    {
                        if (response.getStatus() == 1)
                        {
                            mapRange = response.getMap_range();
                            time1_str = response.getTime1_str();
                            time2_str = response.getTime2_str();
                            time3_str = response.getTime3_str();
                            time4_str = response.getTime4_str();
                            getAmap(response.getMap());
                            if (getDate(time1_str))
                            {
                                state = 1;
                                isCard = SpUtils.getParam(getActivity(), currentDate + "state1", "flase").toString();
                                if (isCard.equals("flase"))
                                {
                                    handlers.sendEmptyMessage(ZHENGCHANG);
                                    thread.start();
                                }
                            } else if (getDate(time2_str))
                            {
                                state = 2;
                                isCard = getParam(getActivity(), currentDate + "state2", "flase").toString();
                                if (isCard.equals("flase"))
                                {
                                    handlers.sendEmptyMessage(ZHENGCHANG);
                                    thread.start();
                                }
                            } else if (getDate(time3_str))
                            {
                                state = 3;
                                isCard = getParam(getActivity(), currentDate + "state3", "flase").toString();
                                if (isCard.equals("flase"))
                                {
                                    handlers.sendEmptyMessage(ZHENGCHANG);
                                    thread.start();
                                }
                            } else if (getDate(time4_str))
                            {
                                state = 4;
                                isCard = getParam(getActivity(), currentDate + "state4", "flase").toString();
                                if (isCard.equals("flase"))
                                {
                                    handlers.sendEmptyMessage(ZHENGCHANG);
                                    thread.start();
                                }
                            } else
                            {
                                isCard = "true";
                                state = 0;
                            }
                        }
                    }
                });
    }


    private void savePunch(String range)
    {
        OkHttpUtils
                .post()
                .url(Constant.HTTP_URL)
                .addParams(Constant.USER_ID, getParam(getActivity(), Constant.USER_ID, "").toString())
                .addParams(Constant.TOKEN, getParam(getActivity(), Constant.TOKEN, "").toString())
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
                        SpUtils.setParam(getActivity(), currentDate + "state" + state, "true");
                        handlers.sendEmptyMessage(YIDAKA);
                        state = 0;
                    }
                });
    }

    private void getAmap(String locations)
    {
        OkHttpUtils
                .get()
                .url(Constant.AMAP)
                .addParams("key", "054cde5f482b46a63594e06a13f85dca")
                .addParams("locations", locations)
                .addParams("coordsys", "baidu")
                .addParams("output", "json")
                .build()
                .execute(new GenericsCallback<AmapBean>(new JsonGenerics())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Alert("网络请求出错：" + e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(AmapBean response, int id)
                    {
                        dialog.dismiss();
                        if (response.getStatus().equals("1"))
                        {
                            String[] gps = response.getLocations().split(",");
                            latLng = new LatLng(Double.parseDouble(gps[1]), Double.parseDouble(gps[0]));//22.638475/
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

    private void showOneDialog(int mapRange)
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
                savePunch("0");
                build.dismiss();
            }
        });
    }

    Handler handlers = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case UPDATE_TIME:
                    long time = System.currentTimeMillis();
                    Date data = new Date(time);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    if (tiemCard != null)
                    {
                        tiemCard.setText(simpleDateFormat.format(data));
                    }
                    switch (state)
                    {
                        case 1:
                            getDate(time1_str);
                            break;
                        case 2:
                            getDate(time2_str);
                            break;
                        case 3:
                            getDate(time3_str);
                            break;
                        case 4:
                            getDate(time4_str);
                            break;
                    }

                    break;
                case CHAOCHU:
                    yidaka.setVisibility(View.GONE);
                    daka.setVisibility(View.VISIBLE);
                    tiemCard.setVisibility(View.VISIBLE);
                    punchClock.setBackgroundResource(R.mipmap.chaochu);
                    break;
                case ZHENGCHANG:
                    punchClock.setBackgroundResource(R.mipmap.card_blue);
                    yidaka.setVisibility(View.GONE);
                    daka.setVisibility(View.VISIBLE);
                    tiemCard.setVisibility(View.VISIBLE);
                    break;
                case YIDAKA:
                    punchClock.setBackgroundResource(R.mipmap.yidaka);
                    yidaka.setVisibility(View.VISIBLE);
                    daka.setVisibility(View.GONE);
                    tiemCard.setVisibility(View.GONE);
                    break;
            }


        }
    };
    Thread thread = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            while (isStop)
            {
                try
                {
                    Thread.sleep(1000);
                    handlers.sendEmptyMessage(UPDATE_TIME);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    });


}
