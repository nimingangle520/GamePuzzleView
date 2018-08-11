package com.example.gamepuzzleview.adapter;

import com.example.gamepuzzleview.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HelpListViewAdapter extends BaseAdapter {
private String[] title;
private String[] content;
private Context context;


	public HelpListViewAdapter(String[] title, String[] content, Context context) {
	super();
	this.title = title;
	this.content = content;
	this.context = context;
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return title.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return title[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater=LayoutInflater.from(context);
		final View view=inflater.inflate(R.layout.item_help_list,null);
		
		
		final TextView tvTitle=(TextView) view.findViewById(R.id.tv_help_title);
		final TextView tvContent=(TextView) view.findViewById(R.id.tv_help_content);
		tvTitle.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				tvContent.setVisibility(tvContent.isShown()?view.GONE:view.VISIBLE);
				
			}
		});
		//¶¯Ì¬°ó¶¨
		tvTitle.setText(title[position]);
		tvContent.setText(content[position]);
		return view;
	}

}
