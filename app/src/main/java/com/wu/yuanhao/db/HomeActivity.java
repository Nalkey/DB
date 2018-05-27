package com.wu.yuanhao.db;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.wu.yuanhao.db.util.MyLog;
import com.wu.yuanhao.db.util.MyWeather;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mDbBtn;
    private ImageButton mMapBtn;
    private ImageButton mSetBtn;
    private ImageView mAirCondImg;
    private TextView mTemperTv;
    private TextView mLocationTv;
    private TextView mAirCondTv;
    private TextView mAqiTv;
    private TextView mPollutionTv;
    public LocationClient mLocationClient;
    public LocationClientOption mLocationClientOpt;
    public String mPosition;
    public HeWeather mHeWeather;
    public Now mWeatherInfo;
    public AirNow mAQI;
    public float mFontSize = 18;
    // 在消息队列中实现对控件的更改
    public static final int UPDATE_WEATHER = 1;
    public static final int UPDATE_AQI =2;
    private final WeatherHandler mWeaHandler = new WeatherHandler(this);
    private final AqiHandler mAqiHandler = new AqiHandler(this);

    static class WeatherHandler extends Handler {
        private WeakReference<HomeActivity> mActivity;

        public WeatherHandler(HomeActivity activity) {
            mActivity = new WeakReference<HomeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HomeActivity activity = mActivity.get();
            if(activity != null) {
                switch (msg.what) {
                    case UPDATE_WEATHER:
                        Now weatherInfo = (Now) msg.obj;
                        InputStream input = activity.getResources().openRawResource(R.raw.condition_code);
                        BufferedReader br = new BufferedReader(new InputStreamReader(input));
                        CSVReader reader = new CSVReader(br, '|');
                        String[] next = {};
                        if (!weatherInfo.getStatus().equals("ok")) {
                            Toast.makeText(activity, "天气服务不可用", Toast.LENGTH_SHORT).show();
                            activity.mAirCondImg.setBackgroundResource(R.drawable.w999);
                        } else {
                            // 读取raw/condition_code.txt，匹配code，获得MyWeather对象
                            try {
                                do {
                                    next = reader.readNext();
                                    if(weatherInfo.getNow().getCond_code().equals(next[0])) {
                                        // 当找到Cond_code时，构建mMyWeather，填入Cond_code,CondZh,CondEn,Image
                                        Resources res = activity.getResources();
                                        int code = res.getIdentifier(next[3], "drawable", activity.getPackageName());
                                        MyWeather mMyWeather = new MyWeather(next[0], next[1], next[2], code);

                                        // 将mMyWeather传递到home_activity.xml上
                                        mMyWeather.setLocation(weatherInfo.getBasic().getLocation());
                                        activity.mAirCondImg.setBackgroundResource(mMyWeather.getImage());
                                        activity.mTemperTv.setText(mMyWeather.getCode());
                                        activity.mLocationTv.setText(mMyWeather.getLocation());
                                        activity.mAirCondTv.setText(mMyWeather.getCondZh());
                                        //mAqiTv.setText(mMyWeather.getAQI());
                                        //mPollutionTv.setText(mMyWeather.getPollution());
                                        break;
                                    } else {
                                        continue;
                                    }
                                } while (next != null);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (br != null) {
                                    try {
                                        br.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    static class AqiHandler extends Handler {
        private WeakReference<HomeActivity> mActivity;

        public AqiHandler(HomeActivity activity) {
            mActivity = new WeakReference<HomeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HomeActivity activity = mActivity.get();
            if(activity != null) {
                switch (msg.what) {
                    case UPDATE_AQI:
                        AirNow aqiInfo = (AirNow) msg.obj;
                        if (!aqiInfo.getStatus().equals("ok")) {
                            Toast.makeText(activity, "空气质量服务不可用", Toast.LENGTH_SHORT).show();
                        } else {
                            activity.mAqiTv.setText(aqiInfo.getAir_now_city().getAqi());
                            activity.mPollutionTv.setText(aqiInfo.getAir_now_city().getMain());
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new HomeLocationListener());
        setContentView(R.layout.home_layout);
        Toolbar toolbar = findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);
        mDbBtn = findViewById(R.id.btn_db);
        mMapBtn = findViewById(R.id.btn_map);
        mSetBtn = findViewById(R.id.btn_settings);
        mDbBtn.setOnClickListener(this);
        mMapBtn.setOnClickListener(this);
        mSetBtn.setOnClickListener(this);
        Intent mIntent = getIntent();
        mFontSize = mIntent.getFloatExtra("FontSize", 18);
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
                Intent mIntent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(mIntent);
                MyLog.d("Intent", "Map Button");
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
                dialog.setTitle("关于");
                dialog.setMessage("作者：Yuanhao WU\n邮箱：254138148@qq.com");
                dialog.show();
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
    public class HomeLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (TextUtils.isEmpty(location.getCity())) {
                mPosition = "beijing";
                Toast.makeText(HomeActivity.this, "定位功能无法使用", Toast.LENGTH_SHORT).show();
                MyLog.d("TAG", "Location cannot be used. " + mPosition);
            } else {
                mPosition = location.getDistrict() + "." + location.getCity();
                MyLog.d("TAG", "Location: " + mPosition);
            }

            // 初始化天气插件
            mAirCondImg = findViewById(R.id.iv_air_cond);
            mTemperTv = findViewById(R.id.tv_temp);
            mLocationTv = findViewById(R.id.tv_location);
            mAirCondTv = findViewById(R.id.tv_air_cond);
            mAqiTv = findViewById(R.id.tv_aqi);
            mPollutionTv = findViewById(R.id.tv_pollution);
            mTemperTv.setTextSize(mFontSize);
            mLocationTv.setTextSize(mFontSize);
            mAirCondTv.setTextSize(mFontSize);
            mAqiTv.setTextSize(mFontSize);
            mPollutionTv.setTextSize(mFontSize);
            // 配置和风，获取天气信息
            String mHeWeatherUserID = HomeActivity.this.getString(R.string.heweather_userid);
            String mHeWeatherAK = HomeActivity.this.getString(R.string.heweather_ak);
            HeConfig.init(mHeWeatherUserID, mHeWeatherAK);
            HeConfig.switchToFreeServerNode();
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
            mHeWeather = new HeWeather();
            HeWeather.getWeatherNow(HomeActivity.this, mPosition,
                    new HeWeather.OnResultWeatherNowBeanListener() {
                @Override
                public void onError(Throwable e) {
                    MyLog.d("HeWeather", "Now.onError: ", e);
                }

                @Override
                public void onSuccess(List<Now> weatherObject) {
                    mWeatherInfo = weatherObject.get(0);
                    MyLog.d("HeWeather", "Now.onSuccess: " + new Gson().toJson(weatherObject));

                    Message weatherMsg = new Message();
                    weatherMsg.what = UPDATE_WEATHER;
                    weatherMsg.obj = mWeatherInfo;
                    mWeaHandler.sendMessage(weatherMsg);
                }
            });
        /*
         * @param context  上下文
         * @param location (如果不添加此参数,SDK会根据GPS联网定位,根据当前经纬度查询)所查询的地区，可通过该地区名称、ID、Adcode、IP和经纬度进行查询
         *                 经纬度格式：纬度,经度（英文,分隔，十进制格式，北纬东经为正，南纬西经为负)
         *                 AirNow的location跟Now的不一样！！！是City -> Air Station
         * @param lang     多语言，默认为简体中文，其他语言请参照多语言对照表
         * @param unit     单位选择，公制（m）或英制（i），默认为公制单位
         * @param listener 网络访问回调接口
         */
            HeWeather.getAirNow(HomeActivity.this, mPosition, Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                    new HeWeather.OnResultAirNowBeansListener() {
                @Override
                public void onError(Throwable e) {
                    MyLog.d("HeWeather", "AirNow.onError: ", e);
                }

                @Override
                public void onSuccess(List<AirNow> aqiObject) {
                    mAQI = aqiObject.get(0);
                    MyLog.d("HeWeather", "AirNow.onSuccess: " + new Gson().toJson(aqiObject));

                    Message aqiMsg = new Message();
                    aqiMsg.what = UPDATE_AQI;
                    aqiMsg.obj = mAQI;
                    mAqiHandler.sendMessage(aqiMsg);
                }
            });
        }
    }
}
