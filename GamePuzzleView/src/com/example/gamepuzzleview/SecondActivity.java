package com.example.gamepuzzleview;

import com.example.gamepuzzleview.media.Music;
import com.example.gamepuzzleview.media.Sound;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class SecondActivity extends BaseActivity implements View.OnClickListener {
	 AlertDialog.Builder builder;
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		findViewById(R.id.imageView5).setOnClickListener(this);
		findViewById(R.id.imageView6).setOnClickListener(this);
		findViewById(R.id.imageView4).setOnClickListener(this);
		findViewById(R.id.imageView1).setOnClickListener(this);
		findViewById(R.id.imageView2).setOnClickListener(this);
		findViewById(R.id.imageView3).setOnClickListener(this);
      //��������
		Music.init(this);
		Music.load(this);
		Music.play(this, R.raw.heros);
		Sound.init(this,new int []{R.raw.sound1,R.raw.sound2,R.raw.sound3,R.raw.sound4,R.raw.sound5});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageView5:
		    
			 builder =new AlertDialog.Builder(this); 
			 builder.setTitle("����").setMessage("��Ȩ���У���л���棡").setIcon(R.drawable.about);
			 builder.setPositiveButton("����",null);
			 builder.show();
			break;

		case R.id.imageView6:
		    builder =new AlertDialog.Builder(this);
			builder.setTitle("�˳���Ϸ").setMessage("�Ƿ��˳���Ϸ��").setIcon(R.drawable.about);
			builder.setPositiveButton("����",null);
			builder.setNegativeButton("�˳�",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				    Music.save(SecondActivity.this);
	                Music.stop();
	                finish();
				}
			});
			builder.show();
			break;
		
		case R.id.imageView4:
			startActivity(HelpActivity.class);
			break;
			
		case  R.id.imageView1:
		    //��Ч 
		    Sound.play(4);
			startActivity(StartActivity.class);
			break;
		case R.id.imageView2:
		    startActivity(RankingActivity.class);
		    break;
		case R.id.imageView3:
		    startActivity(SetActivity.class);
		break;
		}

	}
	
public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	if(keyCode==KeyEvent.KEYCODE_BACK){
		builder =new AlertDialog.Builder(this);
		builder.setTitle("�˳���Ϸ").setMessage("�Ƿ��˳���Ϸ��").setIcon(R.drawable.about);
		builder.setPositiveButton("����",null);
		builder.setNegativeButton("�˳�",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			    Music.save(SecondActivity.this);
   		        Music.stop();
				finish();
			}
		});
		builder.show();
	}
	return super.onKeyDown(keyCode, event);
	
}

//protected void onDestroy()
//{
//    Music.save(SecondActivity.this);
//    Music.stop();
//    super.onDestroy();
//}

}
