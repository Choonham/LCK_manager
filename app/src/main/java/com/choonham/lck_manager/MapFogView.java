package com.choonham.lck_manager;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import com.choonham.lck_manager.map_object.MapObject;

public class MapFogView extends View {

    private int width = 0;
    private int height = 0;

    private Canvas temp;
    private Canvas temp2;

    private Paint paint;
    private Paint p = new Paint();
    private Paint transparentPaint;

    private Bitmap bitmap;

    private Canvas onDrawCanvas;

    public MapFogView(Context context) {
        super(context);

    }

    public void drawFog(int width, int height) {
        this.width = width;
        this.height = height;

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        temp = new Canvas(bitmap);
        temp2 = new Canvas(bitmap);

        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.fog_color, null));

        temp.drawRect(0, 0, temp.getWidth(), temp.getHeight(), paint);

        transparentPaint = new Paint();
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.onDrawCanvas = canvas;
        this.onDrawCanvas .drawBitmap(bitmap, 0, 0, p);
    }

    public void setVision(MapObject mo) {
        temp.drawCircle((float) (mo.x), (float) (mo.y), mo.visionDistance, transparentPaint);
    }

    public void drawIcon(MapObject mo) {
        Paint tempPaint = new Paint();
        tempPaint.setColor(getResources().getColor(R.color.white, null));

        temp2.drawRect(mo.x -10, mo.y -10, mo.x, mo.y, tempPaint);
    }

}
