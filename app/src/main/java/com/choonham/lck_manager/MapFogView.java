package com.choonham.lck_manager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.Log;
import android.view.View;
import com.choonham.lck_manager.map_object.*;

import java.util.Map;

public class MapFogView extends View {

    private int width;
    private int height;
    private int diagonal;

    private Canvas temp;
    private Canvas temp2;

    private Paint paint;
    private Paint p = new Paint();
    private Paint transparentPaint;

    private Bitmap bitmap;

    private Canvas onDrawCanvas;

    private  Resources r;

    public MapFogView(Context context) {
        super(context);
        this.r = context.getResources();
    }

    public void setParam(int width, int height, int diagonal) {
        this.width = width;
        this.height = height;
        this.diagonal = diagonal;

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        temp = new Canvas(bitmap);
        temp2 = new Canvas(bitmap);
    }

    public void drawFog() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.fog_color, null));

        temp.drawRect(0, 0, temp.getWidth(), temp.getHeight(), paint);

        transparentPaint = new Paint();
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void setLoadingImg() {
        Rect src = new Rect(0, 0, this.width, this.height);
        Bitmap loadingImage = BitmapFactory.decodeResource(r, R.drawable.loading_image);

        temp2.drawBitmap(loadingImage, null, src, null);
    }

    public void clearResources() {
        temp.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        temp2.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.onDrawCanvas = canvas;
        this.onDrawCanvas .drawBitmap(bitmap, 0, 0, p);
    }

    public void setVision(MapObject mo) {
        temp.drawCircle((float) ((mo.x * 0.01) * this.width), (float) ((mo.y * 0.01) * this.height), (float) (this.diagonal * (mo.visionDistance * 0.01)), transparentPaint);
    }

    public void drawIcon(MapObject mo) {
        Paint tempPaint = new Paint();

        Bitmap turretImage = BitmapFactory.decodeResource(r, R.drawable.turret);
        Bitmap dragonImage = BitmapFactory.decodeResource(r, R.drawable.dragon);
        Bitmap wardImage = BitmapFactory.decodeResource(r, R.drawable.ward);
        Bitmap nexusImage = BitmapFactory.decodeResource(r, R.drawable.nexus);

        tempPaint.setColor(getResources().getColor(R.color.white, null));

        if(mo.inVisision == 1) {
            Rect src = new Rect((int) (((mo.x * 0.01) * this.width) - 50), (int) (((mo.y * 0.01) * this.height) -50), (int) ((mo.x * 0.01) * this.width) +50, (int) ((mo.y * 0.01) * this.height)+50);
            //temp2.drawRect((mo.x * 0.01) -10, (mo.y * 0.01) -10, (mo.x * 0.01), (mo.y * 0.01), tempPaint);
            if(mo instanceof Turret) {
                temp2.drawBitmap(turretImage, null, src, null);
            } else if(mo instanceof Ward) {
                temp2.drawBitmap(wardImage, null, src, null);
            } else if(mo instanceof Nexus) {
                temp2.drawBitmap(nexusImage, null, src, null);
            } else if(mo instanceof Dragon) {
                temp2.drawBitmap(dragonImage, null, src, null);
            } else {
                temp2.drawRect((float) (((mo.x * 0.01) * this.width) - 10), (float) (((mo.y * 0.01) * this.height) -10), (float) ((mo.x * 0.01) * this.width), (float) ((mo.y * 0.01) * this.height), tempPaint);
            }
        }
    }

}
