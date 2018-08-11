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
  
    int level2;//表示格子的数量
    Bitmap gameBitmap;
    int whiteB;
    
    private static final Random rand = new Random();
    //初始状态
    int startState[];
    public List<int[]>stepList=new ArrayList<int[]>();
    
    
    public BlockGroup(Bitmap gameBitmap)
    {
        this.gameBitmap = gameBitmap;
     
        this.level2 = GameActivity.level * GameActivity.level;
        this.whiteB=GameActivity.level*GameActivity.level-1;
        
        //初始化
        initImageRects();
        initBlocks();
    }
    
    //初始化图片数组
    private void initImageRects()
    {
        imageRects = new ImageRect[this.level2];
        for (int i = 0; i < this.level2; i++)
        {
            ImageRect imageRect = new ImageRect(gameBitmap, i);
            imageRects[i] = imageRect;
        }
    }
    
    //初始化格子数组
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

   //图片打乱
   public void flushBlock()
   {
       //无序打乱
       //flush();
       flush2();
   }
   
   //无序打乱     可能会造成拼图游戏永远不能成功 
   /*
    * 随机产生0至level2-1不重复的随机数，然后分别赋给blocks数组中的每个block的imageIndex
    */
   private void flush()
   {
       //随机产生0至level2-1不重复随机数
       List<Integer> list = new ArrayList<Integer>();
       for (int i = 0; i < level2; i++)
       {
           list.add(i);
       }
       Collections.shuffle(list);
       
       //将这些随机数替换掉block的imageIndex
       for (int i = 0; i < blocks.length; i++)
       {
           Block block = blocks[i];
           block.imageIndex = list.get(i);
       }
   }
   
   //有序打乱
   /*
    * 1.找到白色图片在哪个格子上面
    * 2.随机产生一个方向
    * 3.判断此方向能否移动，若可以，则跟旁边的格子交换imageIndex,否则放弃
    * 
    */
   private void flush2()
   {
       // 1.找到白色图片在哪个格子上面
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
       
       //白色格子所在的行和列
       int xLine = whiteBlock.xLine;
       int yLine = whiteBlock.yLine;
      
       
       //白色格子索引
       int whileBlockIndex = xLine * GameActivity.level +yLine;
       
       
       //产生随机方向 0 向左  1 向右  2 向上   3向下
       int direct = rand.nextInt(4);
       
       //判断能否移动，如果能移动就移动
       //向左
       if(direct==0)
       {
           //如果不是最左边这一列
           if(yLine>0)
           {
               //交换图片
               whiteBlock.imageIndex = blocks[whileBlockIndex-1].imageIndex;
               blocks[whileBlockIndex-1].imageIndex = whiteB;
           }
       }
       else if(direct==1)
       {
           if(yLine<GameActivity.level-1)
           {
               //交换图片
               whiteBlock.imageIndex = blocks[whileBlockIndex+1].imageIndex;
               blocks[whileBlockIndex+1].imageIndex = whiteB;
           }
       }
       //向上
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
   
   //触摸游戏界面
   /*
    * 1.判断是否触摸的是格子
    * 2.判断能否移动，如果能移动就移动
    * 3.判断游戏是否赢了
    */
   //如果游戏赢了，返回true，否则返回false
   public boolean touchResponse(float x,float y)
   {
       for (int i = 0; i < blocks.length; i++)
       {
           Block block = blocks[i];
           //如果返回true，表示触摸的就是这个格子
           if(block.checkTouched(x, y))
           {
               //检查能否移动
               if(checkAndMove(block))
               {
                   //如果移动了，接下来判断游戏是否赢了
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
   
   //检查能否移动，如果能够移动就移动(跟白色格子交换位置)，
   //如果返回true,表示移动了，返回false，不能移动
   private boolean checkAndMove(Block block)
   {
       /*
        * 1.先找到白色格子的位置
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
        * 2.如果在同一行或者同一列并且相邻，就可以交换位置
        */
       if(block.xLine==whiteBlock.xLine&&Math.abs(block.yLine-whiteBlock.yLine)==1)
       {
           whiteBlock.imageIndex = block.imageIndex;
           block.imageIndex = whiteB;
           //记录走的步骤
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
   
   //游戏是否胜利
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
   //标记初始状态
   public void markState(){
  startState= new int [level2];
  for (int i = 0; i < level2; i++){
   startState[i]=blocks[i].imageIndex;
}
   }
   //恢复成初始的状态
   public void rebackState(){
       for (int i = 0; i < blocks.length; i++)
    {
      blocks[i].imageIndex=startState[i];  
    }
   }
   //记录走的步骤
   public void addStep(Block block1,Block block2){
       int index1=block1.xLine*GameActivity.level+block1.yLine;
       int index2=block2.xLine*GameActivity.level+block2.yLine;
       int step[]=new int[2];
       step[0]=index1;
       step[1]=index2;
       stepList.add(step);
   }
   //回放一步
   public void replayStep(int replayCount){
   
       int steps[]=stepList.get(replayCount);
       
       Block block1=blocks[steps[0]];
       Block block2=blocks[steps[1]];
      int temp=block1.imageIndex;
      block1.imageIndex=block2.imageIndex;
      block2.imageIndex=temp;
   }
   
}
