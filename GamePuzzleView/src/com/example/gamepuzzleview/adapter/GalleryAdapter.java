package com.example.gamepuzzleview.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class GalleryAdapter extends BaseAdapter{
private Context context;
private int[] images;

	public GalleryAdapter(Context context, int[] images) {
	super();
	this.context = context;
	this.images = images;
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return images[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView =new ImageView(context);
		//设置控件的 宽和高
		
		imageView.setScaleType(ScaleType.FIT_XY);
		
		LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		imageView.setLayoutParams(params);
	
		
        imageView.setImageResource(images[position]);
		return imageView;
	}

}
