package com.example.gamepuzzleview.sprite;

import com.example.gamepuzzleview.GameActivity;

import com.example.gamepuzzleview.view.GameView;

import android.graphics.RectF;

public class Block
{
//�������Ե�ͼƬ
int imageIndex;
//��������
public RectF rectF;
//���Ͻǵ�����
int x,y;
int xLine,yLine;
public Block(int imageIndex){
    
    this.imageIndex=imageIndex;
    
     xLine=imageIndex/GameActivity.level;
     yLine=imageIndex%GameActivity.level;
    
    x=yLine*ImageRect.imageW+GameView.baseX+yLine;
    y=xLine*ImageRect.imageH+GameView.baseY+xLine;
    //��������
    rectF=new RectF(x,y,x+ImageRect.imageW,y+ImageRect.imageH);
 
}

//�жϴ������Ƿ��Ǵ˸���
public boolean checkTouched(float x,float y)
{
    return rectF.contains(x, y);
}


}
