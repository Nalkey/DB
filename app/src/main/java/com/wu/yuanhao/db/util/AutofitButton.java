package com.wu.yuanhao.db.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.support.v7.widget.AppCompatButton;

/**
 * Created by Yuanhao on 2018/5/16.
 */

public class AutofitButton extends AppCompatButton {
    public AutofitButton(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        autoFitTextSize();
    }

    private void autoFitTextSize() {
        Paint p = getPaint();
        p.setTypeface(getTypeface());
        p.setTextSize(getTextSize());

        float needWidth = getPaddingLeft()+getPaddingRight()+p.measureText(getText().toString());
        if (needWidth > getWidth()) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize()-0.5f);
            autoFitTextSize();
        }
    }
}
