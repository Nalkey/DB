package com.wu.yuanhao.db;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.bumptech.glide.Glide;
import com.wu.yuanhao.db.util.HttpUtil;
import com.wu.yuanhao.db.util.MyLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    ImageView mLoginImage; // 登录界面图片
    ProgressBar mLoginProgBar; // 登录进度条
    TextView mUsernameTv;
    TextView mPasswordTv;
    EditText mUsernameEt; // 用户名输入
    EditText mPasswordEt; // 密码输入
    Button mLoginBtn; // 登录按钮
    WindowManager mWindMng;
    public static final int STD_SCRN_WIDTH = 768;
    //    public static final int STD_SCRN_HEIGHT = 1280;
    public float mFontSize = 18;
    DisplayMetrics outMetrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 把屏幕最上面系统的状态栏和图片融合到一起，通过setSystemUiVisibility()改变系统UI的显示
        // Android 5.0以上支持
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.login_layout);

        // 获取界面组件
        mLoginImage = findViewById(R.id.iv_login_pic);
        mUsernameTv = findViewById(R.id.tv_username);
        mPasswordTv = findViewById(R.id.tv_password);
        mUsernameEt = findViewById(R.id.et_username);
        mPasswordEt = findViewById(R.id.et_pwd);
        mLoginBtn = findViewById(R.id.btn_login);
        mLoginProgBar = findViewById(R.id.progbar_login);

        // 获取SharedPreferences中的缓存背景图片，如果有直接调用glide加载，如果没有就调用loadBingPic()
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String mBingPic = mPrefs.getString("bing_pic", null);
        if(mBingPic != null) {
            Glide.with(this).load(mBingPic).into(mLoginImage);
        } else {
            loadBingPic();
        }

        // 获取屏幕宽度以定义字体大小
        mWindMng = this.getWindowManager();
        mWindMng.getDefaultDisplay().getMetrics(outMetrics);
        int mScrnWidth = outMetrics.widthPixels;
//        int mScrnHeight = outMetrics.heightPixels;
        mFontSize = mScrnWidth/STD_SCRN_WIDTH *18;
        mUsernameTv.setTextSize(mFontSize);
        mPasswordTv.setTextSize(mFontSize);
        mUsernameEt.setTextSize(mFontSize);
        mPasswordEt.setTextSize(mFontSize);
        mLoginBtn.setTextSize(mFontSize);

        // 登录按钮绑定点击事件
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取用户名和密码的输入
                String name = mUsernameEt.getText().toString();
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
                            MyLog.d("Permission", Integer.toString(result));
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

    // 从网络加载Bing每日一图：从guolin/api获得图片真实地址，将地址缓存到SharedPreferences中，使用glide加载
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpReq(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.
                        getDefaultSharedPreferences(LoginActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(LoginActivity.this).load(bingPic).into(mLoginImage);
                    }
                });
            }
        });
    }
}
