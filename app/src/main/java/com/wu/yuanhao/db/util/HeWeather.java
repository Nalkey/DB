package com.wu.yuanhao.db.util;

import android.graphics.drawable.Drawable;

public class HeWeather {
    private int mCode;
    private String mCondZh;
    private String mCondEn;
    private Drawable mImage;

    public HeWeather(int mCode, String mCondZh, String mCondEn, Drawable mImage) {
        this.mCode = mCode;
        this.mCondZh = mCondZh;
        this.mCondEn = mCondEn;
        this.mImage = mImage;
    }

    public int getCode() {
        return mCode;
    }

    public String getCondZh() {
        return mCondZh;
    }

    public String getmCondEn() {
        return mCondEn;
    }

    public Drawable getImage() {
        return mImage;
    }
}
