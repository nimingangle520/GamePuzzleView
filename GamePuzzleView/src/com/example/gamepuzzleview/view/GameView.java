package com.example.gamepuzzleview.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.gamepuzzleview.GameActivity;
import com.example.gamepuzzleview.R;
import com.example.gamepuzzleview.sprite.BlockGroup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class GameView extends BaseSurfaceView
{
    public static boolean isDebug = true;
    // 游戏的状态
    private static final int GAMESTATE_READY = 0;// 准备
    private static final int GAMESTATE_RUN = 1;// 正在游戏中
    private static final int GAMESTATE_WIN = 2;// 胜利
    private static final int GAMESTATE_SHOW = 3;// 查看缩略图
    private static final int GAMESTATE_REPLAY = 4;// 回放
    int gameState;
    Bitmap gameBitmap, smallBitmap, timeBitmap, replayBitmap;
    RectF smallRectF, replayRectF;
    BlockGroup bGroup;

    Date date;

    // 准备状态的提示文字
    private String[] tips;
    private String currTip;// 当前的提示文字
    int flushTotal;// 图片打乱的次数
    int flushCount;// 当前打乱到了第几次

    // 游戏计时
    // long startTime = 0;

    // 触摸事件
    float touchX, touchY;
    boolean isTouched = false;

    // 回放
    int replayCount = 0;
    int currReplayCount = 0;
    // 胜利
    int winText;
    // 分数
    long score;

    // 游戏界面位置
    public static int baseX, baseY;

    // 初始化
    GameActivity gameActivity;

    public GameView(Context context)
    {
        super(context);
        this.gameActivity = (GameActivity) context;
        init();
    }

    // 初始化
    private void init()
    {

        baseX = 10;
        baseY = 120;

        gameBitmap = BitmapFactory.decodeResource(getResources(),
                GameActivity.selectedImageId);
        // 压缩
        gameBitmap = Bitmap.createScaledBitmap(gameBitmap,
                GameActivity.screenWidth - baseX * 2, GameActivity.screenHeight
                        - baseY, true);
        // 缩略图
        smallBitmap = Bitmap.createScaledBitmap(gameBitmap, 100, 100, true);
        smallRectF = new RectF(10, 10, 110, 110);
        // 时间图
        timeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.k);
        // 回放按钮
        replayBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.up);
        replayRectF = new RectF(220, 60, 220 + replayBitmap.getWidth(),
                60 + replayBitmap.getHeight());

        // 设置游戏的初始状态
        gameState = GameView.GAMESTATE_READY;

        // 格子组
        bGroup = new BlockGroup(gameBitmap);

        // 提示的数组
        tips = getResources().getStringArray(R.array.tips);
        flushTotal = GameActivity.level * GameActivity.level * 6;
        // 胜利的文字滚动
        winText = GameActivity.screenW;

        if (isDebug)
        {
            flushTotal = 3;
        }

    }

    public void onUpdate()
    {
        switch (gameState)
        {
        case GameView.GAMESTATE_READY:
            if (flushCount < flushTotal)
            {
                currTip = tips[flushCount % tips.length];
                flushCount++;
                // 图片打乱
                bGroup.flushBlock();
            }
            else
            {
                // startTime = System.currentTimeMillis();
                new Thread(new Runnable()
                {

                    public void run()
                    {
                        while (true)
                        {
                            try
                            {
                                Thread.sleep(1000);
                                score++;
                            }
                            catch (InterruptedException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }

                    }
                }).start();
                // 标记初始的状态
                bGroup.markState();
                gameState = GameView.GAMESTATE_RUN;
            }

            break;
        case GameView.GAMESTATE_RUN:
            // 如果触摸了
            if (isTouched)
            {
                // 触摸的x和y是不是在我这的矩形区域内
                if (smallRectF.contains(touchX, touchY))
                {
                    gameState = GameView.GAMESTATE_SHOW;
                }
                // 如果触摸的是回放
                else if (replayRectF.contains(touchX, touchY))
                {
                    // 只有走动了才进入回放状态
                    if (bGroup.stepList.size() > 0)
                    {   //进入回放状态
                        gameState=GameView.GAMESTATE_REPLAY;
                        bGroup.rebackState();
                    }

                }
                else if (bGroup.touchResponse(touchX, touchY))
                {
                    gameState = GAMESTATE_WIN;

                }
                isTouched = false;
            }
            break;
        case GameView.GAMESTATE_REPLAY:
            // 每10帧回放一步
            if (currReplayCount<10)
            {
                currReplayCount++;
                return;
            }
                currReplayCount=0;

            if (replayCount < bGroup.stepList.size())
            {
                bGroup.replayStep(replayCount);
                replayCount++;
            }
            else
            {
                replayCount=0;
                gameState = GameView.GAMESTATE_RUN;
            }
            break;
        case GameView.GAMESTATE_SHOW:
            if (isTouched)
            {
                gameState = GameView.GAMESTATE_RUN;
                isTouched = false;
            }
            break;
        case GameView.GAMESTATE_WIN:
            if (winText > 0)
            {
                winText -= 10;
            }
            else
            {
                // 跳转

                GameActivity.gameActivity.startActivity(score);
                this.isRun = false;
            }
            break;
        }
    }

    @Override
    public void onPaint(Canvas canvas, Paint paint)
    {
        switch (gameState)
        {
        case GameView.GAMESTATE_READY:
            // 绘制文字
            paint.setColor(Color.YELLOW);
            paint.setTextSize(40);
            canvas.drawText(currTip, 60, 50, paint);
            bGroup.paint(canvas, paint);
            break;
        case GameView.GAMESTATE_RUN:

            paint.setColor(Color.YELLOW);
            paint.setTextSize(40);

            // long currTime = System.currentTimeMillis();

            canvas.drawText(date((int) score), 270, 50, paint);
            canvas.drawBitmap(smallBitmap, 10, 10, paint);
            canvas.drawBitmap(timeBitmap, 220, 10, paint);
            canvas.drawBitmap(replayBitmap, 220, 60, paint);
            // canvas.drawBitmap(gameBitmap, 10, 120, paint);
            bGroup.paint(canvas, paint);

            break;
        case GameView.GAMESTATE_REPLAY:
            bGroup.paint(canvas, paint);

            break;
        case GameView.GAMESTATE_SHOW:
            canvas.drawBitmap(gameBitmap, 0, 0, paint);
            break;
        case GameView.GAMESTATE_WIN:
            // canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(gameBitmap, 10, 10, paint);
            paint.setColor(Color.YELLOW);
            paint.setTextSize(40);
            canvas.drawText("恭喜您赢了！！！！", winText, GameActivity.screenH - 50,
                    paint);
            break;
        }
    }

    // 触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (gameState)
        {
        case GameView.GAMESTATE_READY:

            break;
        case GameView.GAMESTATE_RUN:
            touchX = event.getX();
            touchY = event.getY();
            isTouched = true;
            break;
        case GameView.GAMESTATE_REPLAY:

            break;
        case GameView.GAMESTATE_SHOW:
            isTouched = true;
            break;
        case GameView.GAMESTATE_WIN:
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