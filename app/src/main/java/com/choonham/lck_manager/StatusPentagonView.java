package com.choonham.lck_manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import com.choonham.lck_manager.R;
import com.choonham.lck_manager.enums.ActivityTagEnum;

public class StatusPentagonView extends View {
    private final ActivityTagEnum TAG = ActivityTagEnum.STATUS_PENTAGON_VIEW;

    private Paint paint;
    private float width = 0f;
    private float height = 0f;

    float stability;
    float physical;
    float outSmart;
    float laneStrength;
    float teamFight;


public StatusPentagonView(Context context,
                          float width,
                          float height,
                          float stability,
                          float physical,
                          float outSmart,
                          float laneStrength,
                          float teamFight) {
        super(context);

        this.width = width;
        this.height = height;

        this.stability = stability;
        this.physical = physical;
        this.outSmart = outSmart;
        this.laneStrength = laneStrength;
        this.teamFight = teamFight;

    }

    @Override
    // View 가 화면에 그려질 때 호출되는 콜백
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Cap.BUTT와 Join.MITER를 페인트 객체에 적용
        Paint pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setColor(getResources().getColor(R.color.icon_color, null));
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(5.0F);
        pathPaint.setStrokeCap(Paint.Cap.BUTT);
        pathPaint.setStrokeJoin(Paint.Join.MITER);

        float startPointX = width/2 - (width/20);
        float startPointY = 0;

        float h = height/2;

        float centerX = startPointX;
        float centerY = h;

        // Path 객체 생성
        Path path = new Path();
        path.moveTo(startPointX, startPointY);

        float multiX1 = (float) Math.cos(Math.toRadians(162)); // -
        float multiX2 = (float) Math.cos(Math.toRadians(234)); // -

        float multiY1 = (float) Math.sin(Math.toRadians(162)); // +
        float multiY2 = (float) Math.sin(Math.toRadians(234)); // -

        float point1X = centerX - h*multiX1; float point1Y = centerY - h*multiY1;
        float point2X = centerX - h*multiX2; float point2Y = centerY - h*multiY2;
        float point3X = centerX + h*multiX2; float point3Y = centerY - h*multiY2;
        float point4X = centerX + h*multiX1; float point4Y = centerY - h*multiY1;

        path.lineTo(point1X, point1Y);
        path.lineTo(point2X, point2Y);
        path.lineTo(point3X, point3Y);
        path.lineTo(point4X, point4Y);
        path.lineTo(startPointX, startPointY);

        // Path 객체 그리기
        canvas.drawPath(path, pathPaint);

        pathPaint.setColor(Color.WHITE);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setPathEffect(new DashPathEffect(new float[]{5, 10, 15, 20}, 0));
        pathPaint.setStrokeWidth(2.5F);

        Path pathFromCenter1 = new Path();
        pathFromCenter1.moveTo(centerX, centerY);
        pathFromCenter1.lineTo(startPointX, startPointY);
        canvas.drawPath(pathFromCenter1, pathPaint);

        pathFromCenter1.moveTo(centerX, centerY);
        pathFromCenter1.lineTo(point1X, point1Y);
        canvas.drawPath(pathFromCenter1, pathPaint);

        pathFromCenter1.moveTo(centerX, centerY);
        pathFromCenter1.lineTo(point2X, point2Y);
        canvas.drawPath(pathFromCenter1, pathPaint);

        pathFromCenter1.moveTo(centerX, centerY);
        pathFromCenter1.lineTo(point3X, point3Y);
        canvas.drawPath(pathFromCenter1, pathPaint);

        pathFromCenter1.moveTo(centerX, centerY);
        pathFromCenter1.lineTo(point4X, point4Y);
        canvas.drawPath(pathFromCenter1, pathPaint);

        Paint statPaint = new Paint();
        statPaint.setAntiAlias(true);
        statPaint.setColor(getResources().getColor(R.color.list_view_select_color, null));
        statPaint.setStyle(Paint.Style.STROKE);
        statPaint.setStrokeWidth(3.0F);

        statPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        float physicalIndex = (float) (physical + (Math.random() * 5)) * 10;
        float outSmartIndex = (float) (outSmart + (Math.random() * 5)) * 10;
        float laneStrengthIndex = (float) (laneStrength + (Math.random() * 5)) * 10;
        float teamFightIndex = (float) (teamFight + (Math.random() * 5)) * 10;
        float stabilityIndex = (float) (stability + (Math.random() * 5)) * 10;


        float statPoint0X = centerX;
        float statPoint0Y = centerY - physicalIndex* (float) Math.sin(Math.toRadians(90));

        float statPoint1X = centerX - outSmartIndex*(float) Math.cos(Math.toRadians(162));
        float statPoint1Y = centerY - outSmartIndex* (float) Math.sin(Math.toRadians(162));

        float statPoint2X = centerX - laneStrengthIndex*(float) Math.cos(Math.toRadians(234));
        float statPoint2Y = centerY - laneStrengthIndex*(float) Math.sin(Math.toRadians(234));

        float statPoint3X = centerX + teamFightIndex*(float) Math.cos(Math.toRadians(234));
        float statPoint3Y = centerY - teamFightIndex*(float) Math.sin(Math.toRadians(234));

        float statPoint4X = centerX + stabilityIndex*(float) Math.cos(Math.toRadians(162));
        float statPoint4Y = centerY - stabilityIndex* (float) Math.sin(Math.toRadians(162));

        Path statPath = new Path();
        statPath.moveTo(statPoint0X, statPoint0Y);

        statPath.lineTo(statPoint1X, statPoint1Y);
        statPath.lineTo(statPoint2X, statPoint2Y);
        statPath.lineTo(statPoint3X, statPoint3Y);
        statPath.lineTo(statPoint4X, statPoint4Y);
        statPath.lineTo(statPoint0X, statPoint0Y);

        canvas.drawPath(statPath, statPaint);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getAction() == MotionEvent.ACTION_DOWN) {
        }
        return super.onTouchEvent(e);
    }
}
