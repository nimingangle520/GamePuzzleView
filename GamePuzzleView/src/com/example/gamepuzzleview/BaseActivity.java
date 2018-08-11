package com.example.gamepuzzleview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class BaseActivity extends Activity{
	public static int screenWidth;
	public static int screenHeight;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	DisplayMetrics metrics=new DisplayMetrics();
	getWindowManager().getDefaultDisplay().getMetrics(metrics);
	screenWidth=metrics.widthPixels;
	screenHeight=metrics.heightPixels;
	
}
	protected void startActivity(Class<?> clz){
		//Intent intent=new Intent(this,clz);
		
		startActivity(clz,null);
		
	}

	protected void startActivity(Class<?> clz,Bundle bundle){
		Intent intent=new Intent(this,clz);
        if(bundle!=null){
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}
	
}
