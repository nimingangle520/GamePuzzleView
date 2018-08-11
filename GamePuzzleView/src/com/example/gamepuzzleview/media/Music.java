package com.example.gamepuzzleview.media;

import com.example.gamepuzzleview.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.MediaPlayer;


public class Music{
    
public static AudioManager audioMgr;
public static MediaPlayer mPlayer;
public static int maxVolume;
public static int currVolume=2;
public static boolean isMusicOn=true;

//��ʼ��
public  static void init(Context context)
{

   audioMgr= (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
   maxVolume=audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
   
}
   
//��������
public static void adjustVolume(int volume){
    audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
    Music.currVolume=volume;
}
//����
public static void play(Context context,int resId){
    if(!Music.isMusicOn){
        return ;
    }
   mPlayer= MediaPlayer.create(context, resId);
   //ѭ������
   mPlayer.setLooping(true);
   mPlayer.start();
}
//ֹͣ
public static void stop(){
    if(mPlayer==null||!Music.isMusicOn){
        return;
    }
    
    if(mPlayer.isPlaying()){
        mPlayer.stop();
        mPlayer.release();
        mPlayer=null;
    }
}
//����ƫ������
public static void save(Context context){
    SharedPreferences spf=context.getSharedPreferences("music",context.MODE_PRIVATE );
    Editor editor=spf.edit();
    editor.putBoolean("isMusicOn", isMusicOn);
    editor.putInt("currVolume", currVolume);
    editor.commit();
}
//����
public static void load(Context context){
    SharedPreferences spf=context.getSharedPreferences("music", context.MODE_PRIVATE);
    Music.isMusicOn=spf.getBoolean("isMusicOn", false);
    Music.currVolume=spf.getInt("currVolume", 0);
    
}
}
