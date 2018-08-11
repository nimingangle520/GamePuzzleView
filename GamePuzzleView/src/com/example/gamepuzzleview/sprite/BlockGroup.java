package com.example.gamepuzzleview.sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.example.gamepuzzleview.GameActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BlockGroup
{
    public Block blocks[];
    public ImageRect imageRects[];
  
    int level2;//��ʾ���ӵ�����
    Bitmap gameBitmap;
    int whiteB;
    
    private static final Random rand = new Random();
    //��ʼ״̬
    int startState[];
    public List<int[]>stepList=new ArrayList<int[]>();
    
    
    public BlockGroup(Bitmap gameBitmap)
    {
        this.gameBitmap = gameBitmap;
     
        this.level2 = GameActivity.level * GameActivity.level;
        this.whiteB=GameActivity.level*GameActivity.level-1;
        
        //��ʼ��
        initImageRects();
        initBlocks();
    }
    
    //��ʼ��ͼƬ����
    private void initImageRects()
    {
        imageRects = new ImageRect[this.level2];
        for (int i = 0; i < this.level2; i++)
        {
            ImageRect imageRect = new ImageRect(gameBitmap, i);
            imageRects[i] = imageRect;
        }
    }
    
    //��ʼ����������
    private void initBlocks()
    {
        blocks = new Block[this.level2];
        for (int i = 0; i < this.level2; i++)
        {
            Block block = new Block(i);
            blocks[i] = block;
        }
    }
    
    
    public void paint(Canvas canvas,Paint paint)
    {
        for (int i = 0; i < blocks.length; i++)
        {
            Block block = blocks[i];
            //ImageRect imageRect = imageRects[i];
            ImageRect imageRect=imageRects[block.imageIndex];
            imageRect.paint(block.x, block.y, canvas, paint);
            
        }
    }

   //ͼƬ����
   public void flushBlock()
   {
       //�������
       //flush();
       flush2();
   }
   
   //�������     ���ܻ����ƴͼ��Ϸ��Զ���ܳɹ� 
   /*
    * �������0��level2-1���ظ����������Ȼ��ֱ𸳸�blocks�����е�ÿ��block��imageIndex
    */
   private void flush()
   {
       //�������0��level2-1���ظ������
       List<Integer> list = new ArrayList<Integer>();
       for (int i = 0; i < level2; i++)
       {
           list.add(i);
       }
       Collections.shuffle(list);
       
       //����Щ������滻��block��imageIndex
       for (int i = 0; i < blocks.length; i++)
       {
           Block block = blocks[i];
           block.imageIndex = list.get(i);
       }
   }
   
   //�������
   /*
    * 1.�ҵ���ɫͼƬ���ĸ���������
    * 2.�������һ������
    * 3.�жϴ˷����ܷ��ƶ��������ԣ�����Աߵĸ��ӽ���imageIndex,�������
    * 
    */
   private void flush2()
   {
       // 1.�ҵ���ɫͼƬ���ĸ���������
       Block whiteBlock=null;
       for (int i = 0; i < blocks.length; i++)
       {
           Block block = blocks[i];
           if(block.imageIndex == whiteB)
           {
               whiteBlock = block;
               break;
           }
       }
       
       //��ɫ�������ڵ��к���
       int xLine = whiteBlock.xLine;
       int yLine = whiteBlock.yLine;
      
       
       //��ɫ��������
       int whileBlockIndex = xLine * GameActivity.level +yLine;
       
       
       //����������� 0 ����  1 ����  2 ����   3����
       int direct = rand.nextInt(4);
       
       //�ж��ܷ��ƶ���������ƶ����ƶ�
       //����
       if(direct==0)
       {
           //��������������һ��
           if(yLine>0)
           {
               //����ͼƬ
               whiteBlock.imageIndex = blocks[whileBlockIndex-1].imageIndex;
               blocks[whileBlockIndex-1].imageIndex = whiteB;
           }
       }
       else if(direct==1)
       {
           if(yLine<GameActivity.level-1)
           {
               //����ͼƬ
               whiteBlock.imageIndex = blocks[whileBlockIndex+1].imageIndex;
               blocks[whileBlockIndex+1].imageIndex = whiteB;
           }
       }
       //����
       else if(direct==2)
       {
           if(xLine>0)
           {
               whiteBlock.imageIndex = blocks[whileBlockIndex-GameActivity.level].imageIndex;
               blocks[whileBlockIndex-GameActivity.level].imageIndex = whiteB;
           }
       }
       else
       {
           if(xLine<GameActivity.level-1)
           {
               whiteBlock.imageIndex = blocks[whileBlockIndex+GameActivity.level].imageIndex;
               blocks[whileBlockIndex+GameActivity.level].imageIndex = whiteB;
           }
       }
   } 
   
   //������Ϸ����
   /*
    * 1.�ж��Ƿ������Ǹ���
    * 2.�ж��ܷ��ƶ���������ƶ����ƶ�
    * 3.�ж���Ϸ�Ƿ�Ӯ��
    */
   //�����ϷӮ�ˣ�����true�����򷵻�false
   public boolean touchResponse(float x,float y)
   {
       for (int i = 0; i < blocks.length; i++)
       {
           Block block = blocks[i];
           //�������true����ʾ�����ľ����������
           if(block.checkTouched(x, y))
           {
               //����ܷ��ƶ�
               if(checkAndMove(block))
               {
                   //����ƶ��ˣ��������ж���Ϸ�Ƿ�Ӯ��
                   if(checkWin())
                   {
                       return true;
                   }
               }
               break;
           }
       }
       return false;
   }
   
   //����ܷ��ƶ�������ܹ��ƶ����ƶ�(����ɫ���ӽ���λ��)��
   //�������true,��ʾ�ƶ��ˣ�����false�������ƶ�
   private boolean checkAndMove(Block block)
   {
       /*
        * 1.���ҵ���ɫ���ӵ�λ��
        * 
        */
       Block whiteBlock=null;
       for (int i = 0; i < blocks.length; i++)
       {
           Block b = blocks[i];
           if(b.imageIndex == whiteB)
           {
               whiteBlock = b;
               break;
           }
       }
       
       /*
        * 2.�����ͬһ�л���ͬһ�в������ڣ��Ϳ��Խ���λ��
        */
       if(block.xLine==whiteBlock.xLine&&Math.abs(block.yLine-whiteBlock.yLine)==1)
       {
           whiteBlock.imageIndex = block.imageIndex;
           block.imageIndex = whiteB;
           //��¼�ߵĲ���
           addStep(whiteBlock, block);
           return true;
       }
       
      if(block.yLine==whiteBlock.yLine && Math.abs(block.xLine-whiteBlock.xLine)==1)
       {
           whiteBlock.imageIndex = block.imageIndex;
           block.imageIndex = whiteB;
           addStep(whiteBlock, block);
           return true;
       }
       
       return false;
   }
   
   //��Ϸ�Ƿ�ʤ��
   private boolean checkWin()
   {
       for (int i = 0; i < blocks.length; i++)
       {
           if(blocks[i].imageIndex != i)
           {
               return false;
           }
       }
       return true;
   } 
   //��ǳ�ʼ״̬
   public void markState(){
  startState= new int [level2];
  for (int i = 0; i < level2; i++){
   startState[i]=blocks[i].imageIndex;
}
   }
   //�ָ��ɳ�ʼ��״̬
   public void rebackState(){
       for (int i = 0; i < blocks.length; i++)
    {
      blocks[i].imageIndex=startState[i];  
    }
   }
   //��¼�ߵĲ���
   public void addStep(Block block1,Block block2){
       int index1=block1.xLine*GameActivity.level+block1.yLine;
       int index2=block2.xLine*GameActivity.level+block2.yLine;
       int step[]=new int[2];
       step[0]=index1;
       step[1]=index2;
       stepList.add(step);
   }
   //�ط�һ��
   public void replayStep(int replayCount){
   
       int steps[]=stepList.get(replayCount);
       
       Block block1=blocks[steps[0]];
       Block block2=blocks[steps[1]];
      int temp=block1.imageIndex;
      block1.imageIndex=block2.imageIndex;
      block2.imageIndex=temp;
   }
   
}
