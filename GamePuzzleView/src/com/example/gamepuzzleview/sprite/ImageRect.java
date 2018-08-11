package com.example.gamepuzzleview.sprite;

import com.example.gamepuzzleview.GameActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ImageRect
{
    private Bitmap bitmap;
    public static int imageW,imageH;//图片的宽度和高度
    
    public ImageRect(Bitmap gameBitmap,int imageIndex)
    {
        if(imageIndex==0)
        {
            imageW = gameBitmap.getWidth()/GameActivity.level;
            imageH = gameBitmap.getHeight()/GameActivity.level;
        }
        
        int xLine = imageIndex/GameActivity.level;
        int yLine = imageIndex%GameActivity.level;
        
        int x = yLine * imageW;
        int y = xLine * imageH;
        
        //切图  对图片进行裁剪
        /*
         * 第一个参数：从哪张图片上面裁剪
         * 二，三：下刀的点
         * 四，五：裁剪的宽度和高度
         */
        this.bitmap = Bitmap.createBitmap(gameBitmap, x, y, imageW, imageH);
        //白色的图片
        if(imageIndex == ((GameActivity.level)*(GameActivity.level))-1)
        {
            //根据Bitmap创建一张画布
        
            Canvas canvas = new Canvas(this.bitmap);
            canvas.drawColor(Color.WHITE);
        }
    }
    
    //将自己绘制到画布上面
    public void paint(float x,float y,Canvas canvas,Paint paint)
    {
        canvas.drawBitmap(bitmap, x, y, paint);
}
}