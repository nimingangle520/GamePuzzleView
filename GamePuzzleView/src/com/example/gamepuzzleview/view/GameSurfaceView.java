package com.example.gamepuzzleview.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.gamepuzzleview.GameActivity;
import com.example.gamepuzzleview.MainActivity;
import com.example.gamepuzzleview.R;
import com.example.gamepuzzleview.R.drawable;
import com.example.gamepuzzleview.sprite.Block;
import com.example.gamepuzzleview.sprite.BlockGroup;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Callback
{

    // 游戏的状态
    private static final int GAMESTATE_READY = 0;
    private static final int GAMESTATE_RUN = 1;
    private static final int GAMESTATE_WIN = 2;
    private static final int GAMESTATE_SHOW = 3;
    private static final int GAMESTATE_REPLAY = 4;

    int gameState;
    BlockGroup bGroup;
    int sec;
    
    Thread thread;
    Paint paint;
    Canvas canvas;

    boolean isStop = false;
    Bitmap minBitmap, bitmap, timeBitmap, runBitmap;
    SurfaceHolder holder;
    Date date;
    // 准备状态的提示文字
    private String[] tips;
    private String currentTip;// 当前提示的文字
    int flushTotal;// 打乱次数
    int flushCount;// 当前打乱了第几次

  

    public static int baseX, baseY;

    // 游戏界面的位置

    public GameSurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public GameSurfaceView(Context context)
    {
        super(context);
        init();
    }

    public void init()
    {
        baseX = 10;
        baseY = 120;
       paint =new Paint();
       holder = getHolder();
       holder.addCallback(this);

        thread = new Thread(new Runnable()
        {

            public void run()
            {

                while (!isStop)
                {
                    try
                    {
                        update();
                        paint();
                      
                        Thread.sleep(1000);
                        // sec++;

                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        });

        new Thread(new Runnable()
        {

            public void run()
            {
                while (!isStop)
                {

                    try
                    {
                        Thread.sleep(1000);
                        sec++;
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        }).start();

        bitmap = BitmapFactory.decodeResource(getResources(),
                GameActivity.selectedImageId);
        bitmap = Bitmap.createScaledBitmap(bitmap, GameActivity.screenW - baseX
                * 2, GameActivity.screenH - baseY, true);
        minBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        timeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.k);
        runBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.up);
        // 设置游戏的初始状态
        gameState = GameSurfaceView.GAMESTATE_RUN;
        // 格子组
        bGroup = new BlockGroup(bitmap);
        // 打乱提示的数组
        tips = getResources().getStringArray(R.array.tips);
        flushTotal = GameActivity.level * GameActivity.level;
        
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        thread.start();

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height)
    {

    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
        isStop = true;
    }

    
    private void paint()
    {
     
        
        switch (gameState)
        {
        case GameSurfaceView.GAMESTATE_READY:
            // 绘制文字
           paint.setColor(Color.YELLOW);
           paint.setTextSize(40);
           
        
           
           canvas.drawText(currentTip, 60, 50, paint);
           bGroup.paint(canvas, paint);
            break;

        case GameSurfaceView.GAMESTATE_RUN:

         canvas=null;
            try
            {
                canvas = holder.lockCanvas();
                if (canvas != null)
                {
                    canvas.drawColor(Color.BLACK);
                    paint.setColor(Color.YELLOW);
                    paint.setTextSize(40);
                    canvas.drawText(date(sec), 270, 50, paint);
                    canvas.drawBitmap(timeBitmap, 220, 10, paint);
                    canvas.drawBitmap(runBitmap, 220, 60, paint);
                    // canvas.drawBitmap(bitmap, 10, 120, paint);
                    canvas.drawBitmap(minBitmap, 10, 10, paint);

                    bGroup.paint(canvas, paint);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (canvas!= null)
                {

                    holder.unlockCanvasAndPost(canvas);
                }
            }
            break;
        case GameSurfaceView.GAMESTATE_REPLAY:
            break;
        case GameSurfaceView.GAMESTATE_SHOW:
            break;
        case GameSurfaceView.GAMESTATE_WIN:
            break;
        }

    }

    private void update()
    {

        switch (gameState)
        {
        case GameSurfaceView.GAMESTATE_READY:
            if (flushCount < flushTotal)
            {
                currentTip = tips[flushCount % tips.length];
                flushCount++;
                // 图片打乱
                bGroup.flushBlock();
            }
            else
            {
                gameState = GameSurfaceView.GAMESTATE_RUN;
            }

            break;

        case GameSurfaceView.GAMESTATE_RUN:
            break;
        case GameSurfaceView.GAMESTATE_REPLAY:
            break;
        case GameSurfaceView.GAMESTATE_SHOW:
            break;
        case GameSurfaceView.GAMESTATE_WIN:
            break;
        }
    }

    // 触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (gameState)
        {
        case GameSurfaceView.GAMESTATE_READY:
            break;

        case GameSurfaceView.GAMESTATE_RUN:
            break;
        case GameSurfaceView.GAMESTATE_REPLAY:
            break;
        case GameSurfaceView.GAMESTATE_SHOW:
            break;
        case GameSurfaceView.GAMESTATE_WIN:
            break;
        }

        return super.onTouchEvent(event);
    }

    public String date(int k)
    {

        date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(k);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        return formatter.format(date);

    }

}
