package com.wu.yuanhao.db;
// http://www.cnblogs.com/lonelyxmas/p/7349176.html
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.wu.yuanhao.db.util.BaseActivity.actionStart;

public class LoginActivity extends AppCompatActivity {

    public static final int STD_SCRN_WIDTH = 768;
    ImageView mLoginImage; // 登录界面图片
    ProgressBar mLoginProgBar; // 登录进度条
    TextView mNameTv;
    TextView mPasswordTv;
    EditText mNameEt; // 用户名输入
    EditText mPasswordEt; // 密码输入
    Button mLoginBtn; // 登录按钮
    WindowManager mWindMng;
    DisplayMetrics outMetrics = new DisplayMetrics();
//    public static final int STD_SCRN_HEIGHT = 1280;
    public float mFontSize = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // 获取界面组件
        mLoginImage = findViewById(R.id.iv_login_pic);
        mNameTv = findViewById(R.id.tv_username);
        mPasswordTv = findViewById(R.id.tv_password);
        mNameEt = findViewById(R.id.et_name);
        mPasswordEt = findViewById(R.id.et_pwd);
        mLoginBtn = findViewById(R.id.btn_login);
        mLoginProgBar = findViewById(R.id.progbar_login);

        // 获取屏幕宽度以定义字体大小
        mWindMng = this.getWindowManager();
        mWindMng.getDefaultDisplay().getMetrics(outMetrics);
        int mScrnWidth = outMetrics.widthPixels;
//        int mScrnHeight = outMetrics.heightPixels;
        mFontSize = mScrnWidth/STD_SCRN_WIDTH *18;
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

                    Intent mIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    mIntent.putExtra("FontSize", mFontSize);
                    startActivity(mIntent);
                }else{
                    Toast.makeText(LoginActivity.this,
                            "用户名密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // APP请求权限，如果权限在APP运行时还未授权，就加入mPermissionList
        List<String> mPermissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            mPermissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            mPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            mPermissionList.add(Manifest.permission.CAMERA);
        }
        if(ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            mPermissionList.add(Manifest.permission.INTERNET);
        }
        if(ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mPermissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        // 统一申请所有未授权权限
        if(!mPermissionList.isEmpty()) {
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);
            ActivityCompat.requestPermissions(LoginActivity.this, permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0) {
                    for(int result : grantResults) {
                        if(result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限，程序才能正常工作！",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

}
