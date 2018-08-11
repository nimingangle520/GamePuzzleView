package com.example.gamepuzzleview.adapter;

import java.util.List;

import com.example.gamepuzzleview.R;
import com.example.gamepuzzleview.adapter.bean.RankBean;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandAdapter extends BaseExpandableListAdapter
{
List<List<RankBean>> list;
String [] groups;
Context context;

    public ExpandAdapter(List<List<RankBean>> list, String[] groups, Context context)
{
    super();
    this.list = list;
    this.groups = groups;
    this.context = context;
}

    @Override
    public int getGroupCount()
    {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return list.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return list.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent)
    {
        TextView tv=new TextView(context);
        tv.setText(groups[groupPosition]);
        tv.setPadding(50, 20, 0, 20);
        
        return tv;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent)
    {
     LayoutInflater inflater= LayoutInflater.from(context);
     View view=inflater.inflate(R.layout.item_expandable, null);
     TextView tv1=(TextView) view.findViewById(R.id.textView1);
     TextView tv2=(TextView) view.findViewById(R.id.textView2);
     TextView tv3=(TextView) view.findViewById(R.id.textView3);
     
     RankBean bean=list.get(groupPosition).get(childPosition);
     tv1.setText(bean.getId()+"");
     tv2.setText(bean.getName());
     tv3.setText(bean.getScore()+"");
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

}
