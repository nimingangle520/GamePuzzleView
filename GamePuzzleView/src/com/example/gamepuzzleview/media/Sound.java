package com.example.gamepuzzleview.media;

import android.content.Context;
import android.content.Loader.ForceLoadContentObserver;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound
{
 public static int soundId[];
 public static SoundPool soundPool;
 
public static void init(Context context,int resId[]){
 soundPool=new SoundPool(resId.length, AudioManager.STREAM_MUSIC, 0);
    
    //soundPool.play(soundID, leftVolume, rightVolume, priority, loop, rate);
for (int i = 0; i < resId.length; i++)
{  
    soundId=new int [resId.length];
    soundId[i]=soundPool.load(context, resId[i], 1);
    
}
    
}
public static void play(int index){
    soundPool.play(soundId[index], 1.0f, 1.0f, 0, 0, 1.0f);
}
    
}
