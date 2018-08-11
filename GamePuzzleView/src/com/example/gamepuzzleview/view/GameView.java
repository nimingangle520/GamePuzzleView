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
    // ��Ϸ��״̬
    private static final int GAMESTATE_READY = 0;// ׼��
    private static final int GAMESTATE_RUN = 1;// ������Ϸ��
    private static final int GAMESTATE_WIN = 2;// ʤ��
    private static final int GAMESTATE_SHOW = 3;// �鿴����ͼ
    private static final int GAMESTATE_REPLAY = 4;// �ط�
    int gameState;
    Bitmap gameBitmap, smallBitmap, timeBitmap, replayBitmap;
    RectF smallRectF, replayRectF;
    BlockGroup bGroup;

    Date date;

    // ׼��״̬����ʾ����
    private String[] tips;
    private String currTip;// ��ǰ����ʾ����
    int flushTotal;// ͼƬ���ҵĴ���
    int flushCount;// ��ǰ���ҵ��˵ڼ���

    // ��Ϸ��ʱ
    // long startTime = 0;

    // �����¼�
    float touchX, touchY;
    boolean isTouched = false;

    // �ط�
    int replayCount = 0;
    int currReplayCount = 0;
    // ʤ��
    int winText;
    // ����
    long score;

    // ��Ϸ����λ��
    public static int baseX, baseY;

    // ��ʼ��
    GameActivity gameActivity;

    public GameView(Context context)
    {
        super(context);
        this.gameActivity = (GameActivity) context;
        init();
    }

    // ��ʼ��
    private void init()
    {

        baseX = 10;
        baseY = 120;

        gameBitmap = BitmapFactory.decodeResource(getResources(),
                GameActivity.selectedImageId);
        // ѹ��
        gameBitmap = Bitmap.createScaledBitmap(gameBitmap,
                GameActivity.screenWidth - baseX * 2, GameActivity.screenHeight
                        - baseY, true);
        // ����ͼ
        smallBitmap = Bitmap.createScaledBitmap(gameBitmap, 100, 100, true);
        smallRectF = new RectF(10, 10, 110, 110);
        // ʱ��ͼ
        timeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.k);
        // �طŰ�ť
        replayBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.up);
        replayRectF = new RectF(220, 60, 220 + replayBitmap.getWidth(),
                60 + replayBitmap.getHeight());

        // ������Ϸ�ĳ�ʼ״̬
        gameState = GameView.GAMESTATE_READY;

        // ������
        bGroup = new BlockGroup(gameBitmap);

        // ��ʾ������
        tips = getResources().getStringArray(R.array.tips);
        flushTotal = GameActivity.level * GameActivity.level * 6;
        // ʤ�������ֹ���
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
                // ͼƬ����
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
                // ��ǳ�ʼ��״̬
                bGroup.markState();
                gameState = GameView.GAMESTATE_RUN;
            }

            break;
        case GameView.GAMESTATE_RUN:
            // ���������
            if (isTouched)
            {
                // ������x��y�ǲ���������ľ���������
                if (smallRectF.contains(touchX, touchY))
                {
                    gameState = GameView.GAMESTATE_SHOW;
                }
                // ����������ǻط�
                else if (replayRectF.contains(touchX, touchY))
                {
                    // ֻ���߶��˲Ž���ط�״̬
                    if (bGroup.stepList.size() > 0)
                    {   //����ط�״̬
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
            // ÿ10֡�ط�һ��
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
                // ��ת

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
            // ��������
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
            canvas.drawText("��ϲ��Ӯ�ˣ�������", winText, GameActivity.screenH - 50,
                    paint);
            break;
        }
    }

    // �����¼�
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