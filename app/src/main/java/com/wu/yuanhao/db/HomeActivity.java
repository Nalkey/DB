package com.wu.yuanhao.db;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.wu.yuanhao.db.util.MyLog;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mDbBtn = null;
    private ImageView mSetBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);
        mDbBtn = (ImageView) findViewById(R.id.db_btn);
        mSetBtn = (ImageView) findViewById(R.id.settings_btn);
        mDbBtn.setOnClickListener(this);
        mSetBtn.setOnClickListener(this);
        // TODO:需要做成根据手机屏幕定义图片宽高
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.db_btn:
                // TODO
                Toast.makeText(this, "DB", Toast.LENGTH_SHORT).show();
                MyLog.v("TAG", "DB Button");
                break;
            case R.id.settings_btn:
                // TODO
                Toast.makeText(this, "Set", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
