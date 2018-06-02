package com.wu.yuanhao.db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import retrofit2.Retrofit;

public class DbActivity extends AppCompatActivity {

    TextView mDbNameTv;
    TextView mDbGenderTv;
    EditText mDbNameEt;
    RadioGroup mDbGenderRb;
    Button mDbQueryBtn;
    TextView mDbResultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_layout);

        mDbNameTv = findViewById(R.id.tv_name);
        mDbGenderTv = findViewById(R.id.tv_gender);
        mDbNameEt = findViewById(R.id.et_name);
        mDbGenderRb = findViewById(R.id.rg_gender);
        mDbQueryBtn = findViewById(R.id.btn_query);
        mDbResultTv = findViewById(R.id.tv_query_result);

        String url = this.getString(R.string.dbURL);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
    }
}
