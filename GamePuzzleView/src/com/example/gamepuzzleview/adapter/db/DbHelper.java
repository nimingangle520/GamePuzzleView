package com.example.gamepuzzleview.adapter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper
{

    public DbHelper(Context context)
    {
        super(context,"game", null, 1);
    }

    public void onCreate(SQLiteDatabase db)
    {
        String sql="create table rank(id integer primary key autoincrement,name varchar,level integer,score integer)";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        
    }

}
