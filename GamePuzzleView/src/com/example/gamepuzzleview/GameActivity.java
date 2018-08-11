package com.example.gamepuzzleview;

import com.example.gamepuzzleview.adapter.bean.RankBean;
import com.example.gamepuzzleview.manager.RankManager;
import com.example.gamepuzzleview.view.GameSurfaceView;
import com.example.gamepuzzleview.view.GameView;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.AlteredCharSequence;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class GameActivity extends BaseActivity
{
    RankManager rMgr;
    public static GameActivity gameActivity;
    public static int selectedImageId;
    public static int level;
    public static int screenW;
    public static int screenH;

    protected void onCreate(Bundle savedInstanceState)
    {   //属性赋值
        gameActivity = this;
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle bundle = getIntent().getExtras();

        selectedImageId = bundle.getInt("selectedImageId");
        level = bundle.getInt("level");

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenW = outMetrics.widthPixels;
        screenH = outMetrics.heightPixels;

        GameView view = new GameView(this);
        setContentView(view);
        
        rMgr=new RankManager(this);
        //Toast.makeText(this, selectedImageId + " " + level, Toast.LENGTH_SHORT).show();
    }
    
public boolean onCreateOptionsMenu(Menu menu)
{
      menu.add(Menu.NONE, Menu.FIRST, 0, "返回游戏");
      menu.add(Menu.NONE, Menu.FIRST+1, 0, "游戏帮助");
      menu.add(Menu.NONE, Menu.FIRST+2, 0, "游戏设置");
      menu.add(Menu.NONE, Menu.FIRST+3, 0, "返回选关");
   
    return true;
}

public boolean onOptionsItemSelected(MenuItem item)
{
    switch (item.getItemId())
    {
    case Menu.FIRST:
       
        break;

    case Menu.FIRST+1:
        startActivity(HelpActivity.class);
        break;
    case Menu.FIRST+2:
        startActivity(SetActivity.class);
        break;
    case Menu.FIRST+3:
       AlertDialog.Builder builder=new AlertDialog.Builder(this);
       builder.setTitle("退出游戏").setMessage("是否返回选关").setIcon(R.drawable.about);
       builder.setNegativeButton("退出",new OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            startActivity(StartActivity.class);
            
        }
    } );
       builder.setPositiveButton("返回", new OnClickListener()
    {
        
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            startActivity(StartActivity.class);
            
        }
    });
       builder.show();
        break;
    } 
     
    return super.onOptionsItemSelected(item);
}
    //成员方法
    public void startActivity(long score){
        
        RankBean bean=rMgr.query10Score(this.level);
        if(bean==null||bean.getScore()>score){
            
            Bundle bundle =new Bundle();
            bundle.putInt("level",this.level); 
            bundle.putInt("score",(int)score );
            startActivity(WinActivity.class, bundle);
          
        }
        else{
            startActivity(SecondActivity.class);
        }
        finish();
    }

}
