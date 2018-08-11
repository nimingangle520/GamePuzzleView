package com.example.gamepuzzleview.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public abstract class BaseSurfaceView extends SurfaceView implements Callback,Runnable
{
    SurfaceHolder holder;
    Thread thread;
    boolean isRun = true;
    Paint paint;

    public BaseSurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public BaseSurfaceView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint();
        //thread = new Thread(this);
    }
@Override
public void run()
{
    while (isRun)
    {
        try
        {
            Thread.sleep(1000 / 24);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        update();
        paint();

    }
}
    public abstract void onUpdate();

    public abstract void onPaint(Canvas canvas, Paint paint);

    private void paint()
    {
        Canvas canvas = null;
        try
        {
            canvas = holder.lockCanvas();
            if (null != canvas)
            {
                canvas.drawColor(Color.BLACK);
                onPaint(canvas, paint);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != canvas)
            {

                holder.unlockCanvasAndPost(canvas);
            }

        }
    }

    private void update()
    {
        onUpdate();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        isRun=true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        isRun = false;
    }

}
