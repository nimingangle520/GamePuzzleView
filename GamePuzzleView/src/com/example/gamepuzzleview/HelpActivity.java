package com.example.gamepuzzleview;

import com.example.gamepuzzleview.adapter.HelpListViewAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class HelpActivity extends Activity {
	private  static final String[] title={"��Ϸ����","��Ϸϵͳ"};
	private static final String [] content={"\nһ����������\n" + "������ƶ����ӽ����ƶ�.\n" + "�������ͼ�鿴ԭͼ��\n"
			+ "�������ͼ���ط���Ϸ���衣\n" + "��Ϸ�а��ֻ����ؼ�:�����˵���ѡ�񷵻�ѡ�ؽ���;\n\n"
			+ "�����Ѷ��趨\n" + "��Ϸ���ݼ򵥣���ͨ�����������ѶȽ�ƴͼ�ָ�Ϊ3*3,4*4,5*5������ʽ��\n\n","\n�����˵��������������ť�ɲ鿴ÿ���Ѷ��¹��ص����ʱ�䡣"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		ListView listView=(ListView) findViewById(R.id.listView);
		
		HelpListViewAdapter adapter=new HelpListViewAdapter(title,content,this);
		listView.setAdapter(adapter);
		
		
	}

}
