package com.wu.yuanhao.db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MapActivity extends AppCompatActivity {

    public LocationClient mLocationClient;
    private TextView mPosTv;
    private MapView mMapMv;
    private BaiduMap mBaiduMap;
    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(new MapLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_layout);
        mPosTv = (TextView) findViewById(R.id.tv_position);
        mMapMv = (MapView) findViewById(R.id.mv_map);
        mBaiduMap = mMapMv.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        requestLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapMv.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapMv.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mMapMv.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
    }

    private void requestLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void navigateTo(BDLocation location) {
        // isFirstLocate防止多次调用animateMapStatus，将地图移动到当前位置只需要第一次定位时候调用一次
        if(isFirstLocate) {
            // TODO: Release版要解注释
            //LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            LatLng ll = new LatLng(39.863975, 116.397388);
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            mBaiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        // TODO: Release版要解注释
        //locationBuilder.latitude(location.getLatitude());
        //locationBuilder.longitude(location.getLongitude());
        locationBuilder.latitude(39.863975);
        locationBuilder.longitude(116.397388);
        MyLocationData locationData = locationBuilder.build();
        mBaiduMap.setMyLocationData(locationData);
    }

    public class MapLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            navigateTo(location);
            StringBuilder curPos = new StringBuilder();
            curPos.append("国家：").append(location.getCountry()).append("\n");
            curPos.append("省：").append(location.getProvince()).append("\n");
            curPos.append("市：").append(location.getCity()).append("\n");
            curPos.append("区：").append(location.getDistrict()).append("\n");
            curPos.append("街：").append(location.getStreet()).append("\n");
            mPosTv.setText(curPos);
        }
    }
}
