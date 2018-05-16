package com.wu.yuanhao.db.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Yuanhao on 2018/4/26.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar mActionBar = getSupportActionBar();
        // 隐藏标题栏
        if (mActionBar != null) {
            mActionBar.hide();
        }
        // 可以在日志里看到是哪个Activity在活动
        Log.d("BaseActivity", getClass().getSimpleName());
    }

    // 在使用onClick时候调用，可以使代码更清晰
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, BaseActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }
}
