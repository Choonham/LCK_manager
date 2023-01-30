package com.choonham.lck_manager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import com.choonham.lck_manager.R;

public class MapFogView extends View {

    private int teamSide = 0;
    private int width = 0;
    private int height = 0;

    public MapFogView(Context context, int teamSide, int width, int height) {
        super(context);

        this.teamSide = teamSide;
        this.width = width;
        this.height = height;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint fogPaint = new Paint();
        fogPaint.setAntiAlias(true);
        fogPaint.setColor(getResources().getColor(R.color.fog_color, null));

        fogPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        fogPaint.setStrokeWidth(5.0F);
        
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(width, 0);
        path.lineTo(width, height);
        path.lineTo(0, 0);

        canvas.drawPath(path, fogPaint);
    }


}
