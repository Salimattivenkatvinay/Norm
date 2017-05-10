package vin.com.carracing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Player {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 50;
    private boolean boosting;
    private final int GRAVITY = -10;
    private int maxY;
    private int minY;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    private Rect detectCollision;

    public Player(Context context, int screenX, int screenY,int spd) {
        x = 75;
        y = screenY/2;
        speed = spd;
         Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        bitmap=Bitmap.createScaledBitmap(bitmap1,bitmap1.getWidth()/2,screenY/2,false);
        maxY = screenY;
        minY = 0;
        boosting = false;
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }
    public void setBoosting() {
        boosting = true;
    }
    public void stopBoosting() {
        boosting = false;
    }

    public void update(){
        if (boosting) {
            y = 0;

        } else {
            y = maxY/2;

        }
          if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }
    public Rect getDetectCollision() {
        return detectCollision;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getSpeed() {
        return speed;
    }
}