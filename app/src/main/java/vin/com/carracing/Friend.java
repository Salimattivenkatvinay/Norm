package vin.com.carracing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;



public class Friend {

    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    Rect detectCollision;

    public Friend(Context context, int screenX, int screenY) {

        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.friend);
        bitmap=Bitmap.createScaledBitmap(bitmap1,bitmap1.getWidth()/2,screenY/2,false);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        Random generator = new Random(2);
        speed = generator.nextInt(6) + 10;
        x = maxX;
        y = 0;
        detectCollision=new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());
    }

    public void update(int playerSpeed) {
        x -= playerSpeed;
        x -= speed;
        if (x < minX - bitmap.getWidth()) {
            double v=Math.random();
            speed = (int) v*10 + 10;
            x = maxX;
            if(v<0.5)
            y = maxY/2;
            else if(v>.5)
                y=0;
        }
        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    //getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
