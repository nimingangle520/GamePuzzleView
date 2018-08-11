package com.example.gamepuzzleview.sprite;

import com.example.gamepuzzleview.GameActivity;

import com.example.gamepuzzleview.view.GameView;

import android.graphics.RectF;

public class Block
{
//格子所对的图片
int imageIndex;
//矩形区域
public RectF rectF;
//左上角的坐标
int x,y;
int xLine,yLine;
public Block(int imageIndex){
    
    this.imageIndex=imageIndex;
    
     xLine=imageIndex/GameActivity.level;
     yLine=imageIndex%GameActivity.level;
    
    x=yLine*ImageRect.imageW+GameView.baseX+yLine;
    y=xLine*ImageRect.imageH+GameView.baseY+xLine;
    //矩形区域
    rectF=new RectF(x,y,x+ImageRect.imageW,y+ImageRect.imageH);
 
}

//判断触摸的是否是此格子
public boolean checkTouched(float x,float y)
{
    return rectF.contains(x, y);
}


}
