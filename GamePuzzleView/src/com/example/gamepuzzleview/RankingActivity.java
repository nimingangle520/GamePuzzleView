package com.example.gamepuzzleview;

import java.util.ArrayList;
import java.util.List;

import com.example.gamepuzzleview.adapter.ExpandAdapter;
import com.example.gamepuzzleview.adapter.bean.RankBean;
import com.example.gamepuzzleview.manager.RankManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ExpandableListView;

public class RankingActivity extends Activity
{
    
public static String [] groups=new String[]{"¼òµ¥","ÆÕÍ¨","À§ÄÑ"};

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ExpandableListView view= (ExpandableListView) findViewById(R.id.lv);
        
        List<List<RankBean>> list=new ArrayList<List<RankBean>>();
        
        RankManager rankMgr=new  RankManager(this);
        
        for (int i = 0; i < groups.length; i++)
        {
            List<RankBean>ranklist=rankMgr.queryScoreByLevel(i+3);
            list.add(ranklist);
        }
        
      
        ExpandAdapter adapter=new ExpandAdapter(list, groups, this);
        view.setAdapter(adapter);
    }

}
