package com.wu.yuanhao.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.wu.yuanhao.db.util.MyLog;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mDbBtn = null;
    private ImageButton mSetBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        Toolbar toolbar = findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);
        mDbBtn = findViewById(R.id.btn_db);
        mSetBtn = findViewById(R.id.btn_settings);
        mDbBtn.setOnClickListener(this);
        mSetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_db:
                // TODO
                Toast.makeText(this, "DB", Toast.LENGTH_SHORT).show();
                MyLog.v("TAG", "DB Button");
                break;
            case R.id.btn_settings:
                // TODO
                Toast.makeText(this, "Set", Toast.LENGTH_SHORT).show();
                MyLog.v("TAG", "Set Button");
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
                Toast.makeText(this, "Yuanhao WU", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}
