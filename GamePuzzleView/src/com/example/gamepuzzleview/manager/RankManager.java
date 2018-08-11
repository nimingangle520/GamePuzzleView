package com.example.gamepuzzleview.manager;

import java.util.ArrayList;
import java.util.List;

import com.example.gamepuzzleview.GameActivity;
import com.example.gamepuzzleview.adapter.bean.RankBean;
import com.example.gamepuzzleview.adapter.db.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RankManager 
{
DbHelper dbHelper=null;

public RankManager(Context context){
    
    dbHelper=new DbHelper(context);
}

public RankBean query10Score(int level){
   //得到数据库对对象
   SQLiteDatabase db= dbHelper.getWritableDatabase();
   //查询
   Cursor cursor=db.rawQuery("select * from rank where level=? order by score asc limit 9,1", new String[]{level+""});
    //取得记录
    RankBean bean=null;//变量多了就用对象装，对象多了就用容器装（数组，集合）
    
    if(cursor.moveToNext()){
    String name= cursor.getString(cursor.getColumnIndex("name"));
    int score =cursor.getInt(cursor.getColumnIndex("score"));
    
    
     bean=new RankBean(name,score,level);
    }
    cursor.close();
    db.close();
    return bean;
}

//查询每个等级的前10名
public List<RankBean> queryScoreByLevel(int level){
    SQLiteDatabase db= dbHelper.getWritableDatabase();
    Cursor cursor=db.rawQuery("select * from rank where level=? order by score asc limit 10", new String []{level+""});
    List<RankBean> list=new ArrayList<RankBean>();
    int index=0;
    while(cursor.moveToNext()){
        index++;
        String name =cursor.getString(cursor.getColumnIndex("name"));
        int score=cursor.getInt(cursor.getColumnIndex("score"));
        RankBean bean=new RankBean(index,name,score,level);
        list.add(bean);
        
    }
    cursor.close();
    db.close();
    
    return list;
}



//添加一条记录
public void addRank(RankBean bean){
  SQLiteDatabase db=  dbHelper.getWritableDatabase();
  
  ContentValues values=new ContentValues();
  values.put("name", bean.getName());
  values.put("score", bean.getScore());
  values.put("level", bean.getLevel());
  long id=db.insert("rank", "name", values);
  db.close();
}
//清空存档
public void deleteAllScore(){
   SQLiteDatabase db= dbHelper.getWritableDatabase();
   db.delete("rank", null, null);
   db.close();
}

}
