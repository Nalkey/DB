package com.wu.yuanhao.db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button mDbBtn = (Button) findViewById(R.id.db_btn);
        Button mSetBtn = (Button) findViewById(R.id.settings_btn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.db_btn:
                // TODO
                break;
            case R.id.settings_btn:
                // TODO
                break;
            default:
                break;
        }
    }
}
