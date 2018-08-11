package com.example.gamepuzzleview;

import com.example.gamepuzzleview.adapter.GalleryAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;

public class StartActivity extends BaseActivity implements OnClickListener {
	
	
	
private  int level;	
private int []images={R.drawable.b1,R.drawable.b2,R.drawable.b3};
private int selectedImageId;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		Gallery gallery=(Gallery) findViewById(R.id.gallery);
		GalleryAdapter adapter=new GalleryAdapter(this,images);
		
		gallery.setAdapter(adapter);
		
gallery.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		selectedImageId=images[position];
		
		AlertDialog.Builder builder=new AlertDialog.Builder(StartActivity.this);
		builder.setTitle("请选择难度").setIcon(R.drawable.about);
		builder.setPositiveButton("简单",StartActivity.this);
		builder.setNegativeButton("困难", StartActivity.this);
		builder.setNeutralButton("普通", StartActivity.this);
		
		builder.show();
	}
});
		
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			level=3;
			break;

		case DialogInterface.BUTTON_NEUTRAL:
			level=4;
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			level=5;
			break;
		}
		
		
		Bundle bundle=new  Bundle();
        bundle.putInt("selectedImageId", selectedImageId);
        bundle.putInt("level",level);
		startActivity(GameActivity.class, bundle);
		
	

	}
	}
