package com.wu.yuanhao.db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
