package com.example.gamepuzzleview;

import com.example.gamepuzzleview.adapter.HelpListViewAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class HelpActivity extends Activity {
	private  static final String[] title={"游戏操作","游戏系统"};
	private static final String [] content={"\n一、基本操作\n" + "点击欲移动格子进行移动.\n" + "点击缩略图查看原图。\n"
			+ "点击播放图标重放游戏步骤。\n" + "游戏中按手机返回键:弹出菜单中选择返回选关界面;\n\n"
			+ "二、难度设定\n" + "游戏根据简单，普通，困难三个难度将拼图分割为3*3,4*4,5*5三种样式。\n\n","\n在主菜单点击积分排名按钮可查看每个难度下过关的最短时间。"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		ListView listView=(ListView) findViewById(R.id.listView);
		
		HelpListViewAdapter adapter=new HelpListViewAdapter(title,content,this);
		listView.setAdapter(adapter);
		
		
	}

}
