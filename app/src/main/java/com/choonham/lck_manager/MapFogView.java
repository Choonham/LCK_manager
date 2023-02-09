package com.choonham.lck_manager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.view.View;
import com.choonham.lck_manager.map_object.*;

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

    private  Resources r;

    public MapFogView(Context context) {
        super(context);
       this.r = context.getResources();
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

    public void drawIcon(MapObject mo) {
        Paint tempPaint = new Paint();

        temp.drawCircle((float) (mo.x), (float) (mo.y), mo.visionDistance, transparentPaint);

        Bitmap turretImage = BitmapFactory.decodeResource(r, R.drawable.turret);
        Bitmap dragonImage = BitmapFactory.decodeResource(r, R.drawable.dragon);
        Bitmap wardImage = BitmapFactory.decodeResource(r, R.drawable.ward);
        Bitmap nexusImage = BitmapFactory.decodeResource(r, R.drawable.nexus);

        tempPaint.setColor(getResources().getColor(R.color.white, null));

        Rect src = new Rect((int) (mo.x - 50), (int) (mo.y -50), (int) mo.x+50, (int) mo.y+50);
        //temp2.drawRect(mo.x -10, mo.y -10, mo.x, mo.y, tempPaint);
        if(mo instanceof Turret) {
            temp2.drawBitmap(turretImage, null, src, null);
        } else if(mo instanceof Ward) {
            temp2.drawBitmap(wardImage, null, src, null);
        } else if(mo instanceof Nexus) {
            temp2.drawBitmap(nexusImage, null, src, null);
        } else if(mo instanceof Dragon) {
            temp2.drawBitmap(dragonImage, null, src, null);
        } else {
            temp2.drawRect(mo.x -10, mo.y -10, mo.x, mo.y, tempPaint);
        }
    }

}
