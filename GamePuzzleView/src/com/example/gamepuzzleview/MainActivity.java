package com.example.gamepuzzleview;

import com.example.gamepuzzleview.view.MyView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends BaseActivity {
public static MainActivity mainActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		MyView view =new MyView(this);
		
		mainActivity=this;
		
		setContentView(view);
		
		
	}

	public void startActivity(){
		startActivity(SecondActivity.class);
		finish();
	}
}
