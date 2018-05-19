package com.wu.yuanhao.db.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Yuanhao on 2018/5/19.
 */

public class HttpUtil {
    public static void sendOkHttpReq(String address, okhttp3.Callback callback) {

        OkHttpClient mClient = new OkHttpClient();
        Request mRequest = new Request.Builder().url(address).build();
        mClient.newCall(mRequest).enqueue(callback);
    }
}
