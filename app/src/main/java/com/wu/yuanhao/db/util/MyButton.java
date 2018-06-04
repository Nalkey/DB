package com.wu.yuanhao.db.util;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by Yuanhao on 2018/6/3.
 */

public class MyButton extends AppCompatButton {
    WindowManager mWindMng;
    public static final int STD_SCRN_WIDTH = 768;
    //public static final int STD_SCRN_HEIGHT = 1280;
    public float mFontSize = 18;
    DisplayMetrics outMetrics = new DisplayMetrics();

    public MyButton(Context context) {
        super(context);
        mWindMng = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, refitText());
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWindMng = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, refitText());
    }

    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mWindMng = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, refitText());
    }

    // 获取屏幕宽度以定义字体大小
    private float refitText() {
        mWindMng.getDefaultDisplay().getMetrics(outMetrics);
        int mScrnWidth = outMetrics.widthPixels;
        //int mScrnHeight = outMetrics.heightPixels;
        mFontSize = mScrnWidth/STD_SCRN_WIDTH *18;
        return (getTextSize() * (float) 1.3);
    }
}
