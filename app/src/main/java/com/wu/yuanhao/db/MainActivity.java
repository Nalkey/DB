package com.wu.yuanhao.db;
// http://www.cnblogs.com/lonelyxmas/p/7349176.html
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    EditText mNameEt = null; // 用户名输入
    EditText mPasswordEt = null; // 密码输入
    Button mLoginBtn = null; // 登录按钮
    ProgressBar mLoginProgBar = null; // 登录进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // 获取界面组件
        mNameEt = (EditText) findViewById(R.id.name_et);
        mPasswordEt = (EditText) findViewById(R.id.pwd_et);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mLoginProgBar = (ProgressBar) findViewById(R.id.login_progress_bar);

        // 登录按钮绑定点击事件
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取用户名和密码的输入
                String name = mNameEt.getText().toString();
                String password = mPasswordEt.getText().toString();
                String testPwd = "abc123";

                // 消息提示
                if (password.equals(testPwd)){
                    // TODO
                    if (mLoginProgBar.getVisibility() != View.VISIBLE) {
                        mLoginProgBar.setVisibility(View.VISIBLE);
                    }
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            HomeActivity.actionStart(MainActivity.this, "data1", "data2");
                            }
                        };
                    Timer timer = new Timer();
                    timer.schedule(task, 2000);
                }else{
                    Toast.makeText(MainActivity.this,
                            "用户名密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
