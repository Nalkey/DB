package com.wu.yuanhao.db;
// http://www.cnblogs.com/lonelyxmas/p/7349176.html
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wu.yuanhao.db.util.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends BaseActivity {

    public static final int STD_SCRN_WIDTH = 768;
    ImageView mLoginImage = null; // 登录界面图片
    ProgressBar mLoginProgBar = null; // 登录进度条
    TextView mNameTv = null;
    TextView mPasswordTv = null;
    EditText mNameEt = null; // 用户名输入
    EditText mPasswordEt = null; // 密码输入
    Button mLoginBtn = null; // 登录按钮
    WindowManager mWindMng;
    DisplayMetrics outMetrics = new DisplayMetrics();
//    public static final int STD_SCRN_HEIGHT = 1280;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // 获取界面组件
        mLoginImage = (ImageView) findViewById(R.id.iv_login_pic);
        mNameTv = (TextView) findViewById(R.id.tv_username);
        mPasswordTv = (TextView) findViewById(R.id.tv_password);
        mNameEt = (EditText) findViewById(R.id.et_name);
        mPasswordEt = (EditText) findViewById(R.id.et_pwd);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mLoginProgBar = (ProgressBar) findViewById(R.id.progbar_login);

        // 获取屏幕宽度以定义字体大小
        mWindMng = this.getWindowManager();
        mWindMng.getDefaultDisplay().getMetrics(outMetrics);
        int mScrnWidth = outMetrics.widthPixels;
//        int mScrnHeight = outMetrics.heightPixels;
        float mFontSize = mScrnWidth/STD_SCRN_WIDTH *18;
        mNameTv.setTextSize(mFontSize);
        mPasswordTv.setTextSize(mFontSize);
        mNameEt.setTextSize(mFontSize);
        mPasswordEt.setTextSize(mFontSize);
        mLoginBtn.setTextSize(mFontSize);

        // TODO: 登录界面图片每日一换
        // 登录按钮绑定点击事件
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取用户名和密码的输入
                String name = mNameEt.getText().toString();
                String password = mPasswordEt.getText().toString();
                String testPwd = "123";

                // 消息提示
                if (password.equals(testPwd)){
                    // TODO
                    if (mLoginProgBar.getVisibility() != View.VISIBLE) {
                        mLoginProgBar.setVisibility(View.VISIBLE);
                    } else {
                        mLoginProgBar.setVisibility(View.GONE);
                    }
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            HomeActivity.actionStart(LoginActivity.this, "data1", "data2");
                            }
                        };
                    Timer timer = new Timer();
                    timer.schedule(task, 2000);
                }else{
                    Toast.makeText(LoginActivity.this,
                            "用户名密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
