package com.wu.yuanhao.db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Retrofit;

public class DbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_layout);

        String url = this.getString(R.string.dbURL);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
    }
}
