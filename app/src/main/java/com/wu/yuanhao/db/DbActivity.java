package com.wu.yuanhao.db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wu.yuanhao.db.util.MyButton;
import com.wu.yuanhao.db.util.MyEditText;
import com.wu.yuanhao.db.util.MyGetQuery;
import com.wu.yuanhao.db.util.MyQueryResult;
import com.wu.yuanhao.db.util.MyTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DbActivity extends AppCompatActivity {
    private MyTextView mDbNameTv;
    private MyTextView mDbGenderTv;
    private MyEditText mDbNameEt;
    private RadioGroup mDbGenderRg;
    private MyButton mDbQueryBtn;
    private MyTextView mDbResultTv;
    public String mGender; // 获取用户选择性别

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_layout);

        mDbNameTv = findViewById(R.id.tv_name);
        mDbGenderTv = findViewById(R.id.tv_gender);
        mDbNameEt = findViewById(R.id.et_name);
        mDbGenderRg = findViewById(R.id.rg_gender);
        mDbQueryBtn = findViewById(R.id.btn_query);
        mDbResultTv = findViewById(R.id.tv_query_result);

        mDbQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取查询条件
                String name = mDbNameEt.getText().toString().trim();

                String url = DbActivity.this.getString(R.string.dbURL);
                Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

                MyGetQuery request = retrofit.create(MyGetQuery.class);
                Call<MyQueryResult> call = request.getCall();
                call.enqueue(new Callback<MyQueryResult>() {
                    //请求成功时回调
                    @Override
                    public void onResponse(Call<MyQueryResult> call, Response<MyQueryResult> response) {
                        // 步骤7：处理返回的数据结果
                        mDbResultTv.setText(response.body().show());
                    }

                    //请求失败时回调
                    @Override
                    public void onFailure(Call<MyQueryResult> call, Throwable throwable) {
                        Toast.makeText(DbActivity.this,
                                "连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
                /*// 获得用户选择性别
                mDbGenderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // 获取查询条件
                        String name = mDbNameEt.getText().toString().trim();
                        RadioButton radioButton = group.findViewById(checkedId);
                        mGender = radioButton.getText().toString();

                        String url = DbActivity.this.getString(R.string.dbURL);
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

                        MyGetQuery request = retrofit.create(MyGetQuery.class);
                        Call<MyQueryResult> call = request.getCall(name);
                        call.enqueue(new Callback<MyQueryResult>() {
                            //请求成功时回调
                            @Override
                            public void onResponse(Call<MyQueryResult> call, Response<MyQueryResult> response) {
                                // 步骤7：处理返回的数据结果
                                mDbResultTv.setText(response.body().show());
                            }

                            //请求失败时回调
                            @Override
                            public void onFailure(Call<MyQueryResult> call, Throwable throwable) {
                                Toast.makeText(DbActivity.this,
                                        "连接失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });*/
            }
        });
    }
}
