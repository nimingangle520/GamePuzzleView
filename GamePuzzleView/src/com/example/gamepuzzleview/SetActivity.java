package com.example.gamepuzzleview;

import com.example.gamepuzzleview.manager.RankManager;
import com.example.gamepuzzleview.media.Music;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SetActivity extends BaseActivity
{
    //MediaPlayer mp;
    //AudioManager audioMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

         final ImageView audio = (ImageView) findViewById(R.id.imageView1);
         //��ȥ��ʱ��ı�����״̬��ͼƬ
         audio.setImageResource(Music.isMusicOn?R.drawable.audio_on:R.drawable.audio_off);
         
        ImageView on = (ImageView) findViewById(R.id.imageView2);
        ImageView off = (ImageView) findViewById(R.id.imageView3);
        ImageView down = (ImageView) findViewById(R.id.imageView4);
        ImageView up = (ImageView) findViewById(R.id.imageView5);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        //����seekBar �����ֵ
//        int volume =Music.currVolume;
//        seekBar.setMax(Music.maxVolume);
//        Music.currVolume=volume;
//        seekBar.setProgress(Music.isMusicOn?Music.currVolume:0);
//        
        
        
        //audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //mp = MediaPlayer.create(this, R.raw.heros);
        
//        audio.setOnClickListener(new OnClickListener()
//        {
//
//            public void onClick(View v)
//            {
//
//            }
//        });
        //����
        on.setOnClickListener(new OnClickListener()
        {

            public void onClick(View v)
            {
                if(!Music.isMusicOn)
                {
                   Music.isMusicOn=true;
                   audio.setImageResource(R.drawable.audio_on);
                   Music.play(SetActivity.this, R.raw.heros);
                    //����seekBar
                   seekBar.setProgress(Music.currVolume);
                  
                }
                //mp.start();
                //audio.setImageResource(R.drawable.audio_on);
            }
        });
        //�ر�
        off.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(Music.isMusicOn){
                    audio.setImageResource(R.drawable.audio_off);
                    Music.stop();
                    //����seekBar
                    int num=Music.currVolume;
                    seekBar.setProgress(0);
                    Music.currVolume=num;
                    //�ܿ��عر�
                    Music.isMusicOn=false;
                }
                
                //mp.stop();
                //audio.setImageResource(R.drawable.audio_off);
               
            }
        });
        //��С
        down.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(Music.isMusicOn){
                    seekBar.setProgress(Music.currVolume-1);
                }
               //audioMgr.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            
            }
        });
        //����
        up.setOnClickListener(new OnClickListener()
        {

            public void onClick(View v)
            {
                if(Music.isMusicOn){
                    seekBar.setProgress(Music.currVolume+1);
                }
                //audioMgr.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                       // AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            }
        });
     
       //int maxV=audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
       //seekBar.setMax(maxV);
        int volume =Music.currVolume;
        seekBar.setMax(Music.maxVolume);
        Music.currVolume=volume;
        seekBar.setProgress(Music.isMusicOn?Music.currVolume:0);
        
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {

            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }

            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser)
            {
     Music.adjustVolume(progress);
             
            }
        });
        // ����
        btn2.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                //startActivity(SecondActivity.class);
                //finish();
                onBackPressed();
            }
        });
        //����浵
        btn1.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
            RankManager rankManager=new RankManager(SetActivity.this);
            rankManager.deleteAllScore();
           
            }
        });
    }
}
