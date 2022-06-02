package com.choonham.lck_manager.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

public class StatusPentagonView extends View {

    private Paint paint;
    int width = 0;
    int height = 0;

    public StatusPentagonView(Context context) {

        super(context);
    }

    @Override
    // View 가 화면에 그려질 때 호출되는 콜백
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Cap.BUTT와 Join.MITER를 페인트 객체에 적용
        Paint pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setColor(Color.YELLOW);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(5.0F);
        pathPaint.setStrokeCap(Paint.Cap.BUTT);
        pathPaint.setStrokeJoin(Paint.Join.MITER);

        // Path 객체 생성
        Path path = new Path();
        path.moveTo(233.5f, 2);
        float temp = (float) Math.cos(Math.toRadians(162));
        Log.d("ㅎㅎㅎㅎ", temp+"aa");
        path.lineTo(395.2f, 119.5f);
        path.lineTo(333.4f, 309.5f);
        path.lineTo(133.6f, 309.5f);
        path.lineTo(71.8f, 119.5f);
        path.lineTo(233.5f, 2);

        // Path 객체 그리기
        canvas.drawPath(path, pathPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getAction() == MotionEvent.ACTION_DOWN) {
        }
        return super.onTouchEvent(e);
    }
}
