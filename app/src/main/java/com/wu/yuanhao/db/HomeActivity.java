package com.wu.yuanhao.db;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.google.gson.Gson;
import com.wu.yuanhao.db.util.MyLog;

import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mDbBtn = null;
    private ImageButton mMapBtn = null;
    private ImageButton mSetBtn = null;
    public LocationClient mLocationClient;
    public LocationClientOption mLocationClientOpt;
    public String mPosition;
    public HeConfig mHeConfig;
    public HeWeather mHeWeather;
    public Now mWeatherInfo;
    public AirNow mAQI;
    public boolean mWeatherStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.home_layout);
        Toolbar toolbar = findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);
        mDbBtn = findViewById(R.id.btn_db);
        mMapBtn = findViewById(R.id.btn_map);
        mSetBtn = findViewById(R.id.btn_settings);
        mDbBtn.setOnClickListener(this);
        mMapBtn.setOnClickListener(this);
        mSetBtn.setOnClickListener(this);
        MyLog.d("DBG", "onCreate");

        // 配置LocationClient
        mLocationClientOpt = new LocationClientOption();
        mLocationClientOpt.setIsNeedAddress(true);
        mLocationClientOpt.setIgnoreKillProcess(false);
        mLocationClient.setLocOption(mLocationClientOpt);
        // 定位结果回调给上面注册的MyLocationListener
        mLocationClient.start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_db:
                // TODO
                Toast.makeText(this, "DB", Toast.LENGTH_SHORT).show();
                MyLog.d("TAG", "DB Button");
                break;
            case R.id.btn_map:
                // TODO
                Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show();
                MyLog.d("TAG", "Map Button");
                break;
            case R.id.btn_settings:
                // TODO
                Toast.makeText(this, "Set", Toast.LENGTH_SHORT).show();
                MyLog.d("TAG", "Set Button");
                break;
            default:
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(this, "Yuanhao WU", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLog.d("DBG", "onDestroy");
    }

    // 获取当前定位，后期如果有其他activity调用可单独定义为一个类
    // 若无法取得定位，默认定位北京海淀区
    // 1.由于定位是异步的，和风等待定位结果才能获得location
    // 2.这样正好符合天气信息插件不影响主要功能的使用
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if(TextUtils.isEmpty(location.getCity())) {
                mPosition = "haidian,beijing";
                Toast.makeText(HomeActivity.this, "定位功能无法使用", Toast.LENGTH_SHORT).show();
            } else {
                mPosition = location.getDistrict() + "." + location.getCity();
                MyLog.d("TAG", "Location: " + mPosition);
            }

            // 获取天气信息
            String mHeWeatherUserID = this.getString(R.string.heweather_userid);
            String mHeWeatherAK = this.getString(R.string.heweather_ak);
            mHeConfig.init(mHeWeatherUserID, mHeWeatherAK);
            mHeConfig.switchToFreeServerNode();
        /*
         * 实况天气
         * 实况天气即为当前时间点的天气状况以及温湿风压等气象指数，具体包含的数据：体感温度、
         * 实测温度、天气状况、风力、风速、风向、相对湿度、大气压强、降水量、能见度等。
         * @param context  上下文
         * @param location 地址，支持location=chaoyang,beijing等，暂时先用auto_ip，但准确率取决于服务节点
         * @param lang     多语言，默认为简体中文
         * @param unit     单位选择，公制（m）或英制（i），默认为公制单位
         * @param listener 网络访问回调接口
         */
            mHeWeather =new HeWeather();
            mHeWeather.getWeatherNow(this, "haidian,beijing", new HeWeather.OnResultWeatherNowBeanListener() {
                @Override
                public void onError(Throwable e) {
                    MyLog.d("TAG", "onError: ", e);
                }

                @Override
                public void onSuccess(List<Now> dataObject) {
                    mWeatherInfo = dataObject.get(0);
                    if(mWeatherInfo.getStatus().equals("ok")) {
                        mWeatherStatus = true;
                    } else {
                        mWeatherStatus = false;
                    }
                    MyLog.d("TAG", "onSuccess: " + new Gson().toJson(dataObject));
                }
            });
        /*
         * @param context  上下文
         * @param location (如果不添加此参数,SDK会根据GPS联网定位,根据当前经纬度查询)所查询的地区，可通过该地区名称、ID、Adcode、IP和经纬度进行查询
         *                 经纬度格式：纬度,经度（英文,分隔，十进制格式，北纬东经为正，南纬西经为负)
         * @param lang     多语言，默认为简体中文，其他语言请参照多语言对照表
         * @param unit     单位选择，公制（m）或英制（i），默认为公制单位
         * @param listener 网络访问回调接口
         */
            mHeWeather.getAirNow(this, Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultAirNowBeansListener() {
                @Override
                public void onError(Throwable e) {
                    MyLog.d("TAG", "onError: ", e);
                }

                @Override
                public void onSuccess(List<AirNow> dataObject) {
                    mAQI = dataObject.get(0);
                    if(mWeatherInfo.getStatus().equals("ok")) {
                        mWeatherStatus = true;
                    } else {
                        mWeatherStatus = false;
                    }
                    MyLog.d("TAG", "onSuccess: " + new Gson().toJson(dataObject));
                }
            });
            if(mWeatherStatus == false) {
                Toast.makeText(this, "天气服务不可用", Toast.LENGTH_SHORT).show();
                // TODO: 载入图片
            } else {
                // TODO: 载入气象信息在屏幕最下
            }
        }
    }
}
