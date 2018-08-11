package com.example.gamepuzzleview.sprite;

import com.example.gamepuzzleview.GameActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ImageRect
{
    private Bitmap bitmap;
    public static int imageW,imageH;//ͼƬ�Ŀ�Ⱥ͸߶�
    
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
        
        //��ͼ  ��ͼƬ���вü�
        /*
         * ��һ��������������ͼƬ����ü�
         * ���������µ��ĵ�
         * �ģ��壺�ü��Ŀ�Ⱥ͸߶�
         */
        this.bitmap = Bitmap.createBitmap(gameBitmap, x, y, imageW, imageH);
        //��ɫ��ͼƬ
        if(imageIndex == ((GameActivity.level)*(GameActivity.level))-1)
        {
            //����Bitmap����һ�Ż���
        
            Canvas canvas = new Canvas(this.bitmap);
            canvas.drawColor(Color.WHITE);
        }
    }
    
    //���Լ����Ƶ���������
    public void paint(float x,float y,Canvas canvas,Paint paint)
    {
        canvas.drawBitmap(bitmap, x, y, paint);
}
}