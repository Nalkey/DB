package com.wu.yuanhao.db.util;

/**
 * Created by Yuanhao on 2018/5/22.
 */

public class MyWeather {
    private String mCode;
    private String mCondZh;
    private String mCondEn;
    private int mImage;
    private String mLocation;

    private String mAQI;
    private String mPollution;

    public MyWeather(String mCode, String mCondZh, String mCondEn, int mImage) {
        this.mCode = mCode;
        this.mCondZh = mCondZh;
        this.mCondEn = mCondEn;
        this.mImage = mImage;
    }

    public String getCode() {
        return mCode + "℃";
    }

    public String getCondZh() {
        return mCondZh;
    }

    public String getCondEn() {
        return mCondEn;
    }

    public int getImage() {
        return mImage;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getAQI() {
        return "AQI:" + mAQI;
    }

    public void setAQI(String mAQI) {
        this.mAQI = mAQI;
    }

    public String getPollution() {
        return "主要污染物" + mPollution;
    }

    public void setPollution(String mPollution) {
        this.mPollution = mPollution;
    }

}
