package vin.com.carracing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private ArrayList<Star> stars = new ArrayList<Star>();
    private Boom boom;
    private Friend friend;
    int screenX;
    int countMisses;
    private boolean isGameOver ;
    int x,y;
    Boolean flag;

    public GameView(Context context, int screenX, int screenY,int spd) {
        super(context);
        player = new Player(context, screenX, screenY,spd);
        x=screenX;
        y=screenY;
        surfaceHolder = getHolder();
        paint = new Paint();
        int starNums = 50;
        for (int i = 0; i < starNums; i++) {
            Star s = new Star(screenX, screenY);
            stars.add(s);
        }
        boom = new Boom(context);
        friend = new Friend(context, screenX, screenY);
        this.screenX = screenX;
        countMisses = 0;
        isGameOver = false;
        flag=false;
    }
    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }
    private void update() {
        player.update();
        boom.setX(-250);
        boom.setY(-250);
        for (Star s : stars) {
            s.update(player.getSpeed());
        }
        flag = friend.getX() == screenX;
        friend.update(player.getSpeed());
        if(Rect.intersects(player.getDetectCollision(),friend.getDetectCollision())){
            boom.setX(friend.getX());
            boom.setY(friend.getY());
            playing = false;
            isGameOver = true;
        }else {
            if (flag)countMisses++;
        }
    }
    private void draw() {
        if(surfaceHolder.getSurface().isValid()){
            canvas=surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }
            paint.setTextSize(30);
            canvas.drawText("Score:"+countMisses,x-150,50,paint);
            canvas.drawBitmap(player.getBitmap(),player.getX(),player.getY(),paint);
            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );
            canvas.drawBitmap(
                    friend.getBitmap(),
                    friend.getX(),
                    friend.getY(),
                    paint
            );
            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);
                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                if(motionEvent.getY()<y/2) player.setBoosting();
                else if(motionEvent.getY()>y/2) player.stopBoosting();
                break;
        }
        return true;
    }
}