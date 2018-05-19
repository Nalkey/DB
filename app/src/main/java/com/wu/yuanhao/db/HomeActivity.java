package com.wu.yuanhao.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.wu.yuanhao.db.util.MyLog;

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
    private ImageButton mSetBtn = null;
    private HeConfig mHeConfig = null;
    private HeWeather mHeWeather = null;
    private Now mWeatherInfo;
    private AirNow mAQI;
    private boolean mWeatherStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        Toolbar toolbar = findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);
        mDbBtn = findViewById(R.id.btn_db);
        mSetBtn = findViewById(R.id.btn_map);
        mDbBtn.setOnClickListener(this);
        mSetBtn.setOnClickListener(this);

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
        mHeWeather.getWeatherNow(this, "CN101010100", new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable e) {
                MyLog.v("TAG", "onError: ", e);
            }

            @Override
            public void onSuccess(List<Now> dataObject) {
                mWeatherInfo = dataObject.get(0);
                if(mWeatherInfo.getStatus().equals("ok")) {
                    mWeatherStatus = true;
                } else {
                    mWeatherStatus = false;
                }
                MyLog.v("TAG", "onSuccess: " + new Gson().toJson(dataObject));
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
        mHeWeather.getAirNow(this, "CN101010100", new HeWeather.OnResultAirNowBeansListener() {
            @Override
            public void onError(Throwable e) {
                MyLog.v("TAG", "onError: ", e);
            }

            @Override
            public void onSuccess(List<AirNow> dataObject) {
                mAQI = dataObject.get(0);
                if(mWeatherInfo.getStatus().equals("ok")) {
                    mWeatherStatus = true;
                } else {
                    mWeatherStatus = false;
                }
                MyLog.v("TAG", "onSuccess: " + new Gson().toJson(dataObject));
            }
        });
        if(mWeatherStatus == false) {
            Toast.makeText(this, "天气服务不可用", Toast.LENGTH_SHORT).show();
            // TODO: 载入图片
        } else {
            // TODO: 载入气象信息在屏幕最下
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_db:
                // TODO
                Toast.makeText(this, "DB", Toast.LENGTH_SHORT).show();
                MyLog.v("TAG", "DB Button");
                break;
            case R.id.btn_map:
                // TODO
                Toast.makeText(this, "Set", Toast.LENGTH_SHORT).show();
                MyLog.v("TAG", "Map Button");
                break;
            case R.id.btn_settings:
                // TODO
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

}
