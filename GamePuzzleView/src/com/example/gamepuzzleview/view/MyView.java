package com.example.gamepuzzleview.view;

import com.example.gamepuzzleview.BaseActivity;
import com.example.gamepuzzleview.MainActivity;
import com.example.gamepuzzleview.R;
import com.example.gamepuzzleview.R.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View implements Runnable
{

    private int[] resId = new int[]
    { R.drawable.mmlogo, R.drawable.and1, R.drawable.logo };
    private Bitmap[] bitmaps;
    private int index;
    Paint paint;

    public MyView(Context context)
    {
        super(context);
        init();

    }

    public MyView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        paint = new Paint();
        bitmaps = new Bitmap[resId.length];
        for (int i = 0; i < resId.length; i++)
        {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    resId[i]);

            bitmaps[i] = bitmap.createScaledBitmap(bitmap,
                    BaseActivity.screenWidth, BaseActivity.screenHeight, true);

        }
        new Thread(this).start();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(bitmaps[index], 0, 0, paint);
        super.onDraw(canvas);
    }

    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        while (true)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            index++;
            if (index == 3)
            {
                break;
            }
            postInvalidate();
        }
        MainActivity.mainActivity.startActivity();
    }
}
