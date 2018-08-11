package com.example.gamepuzzleview;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.gamepuzzleview.adapter.bean.RankBean;
import com.example.gamepuzzleview.manager.RankManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class WinActivity extends BaseActivity
{
    EditText etName;
    RankManager rMgr;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Bundle bundle = getIntent().getExtras();
      
        final int score = bundle.getInt("score");
        final int level=bundle.getInt("level");

        // Toast.makeText(this,"level："+level+"  score："+score,
        // Toast.LENGTH_SHORT).show();
        rMgr = new RankManager(this);
        // 控件
        etName = (EditText) findViewById(R.id.editText1);
        String inputName = loadInput();
        etName.setTextKeepState(inputName);
       //点击事件
       findViewById(R.id.button1).setOnClickListener(new OnClickListener()
    {
        
        @Override
        public void onClick(View v)
        {
            String name=etName.getText().toString();
            if(null==name||"".equals(name)){
                Toast.makeText(WinActivity.this, "请输入名字",Toast.LENGTH_SHORT).show();
           return;
            }
            //保存name 到sd卡，下次用户进入赢了界面时，直接使用这个名字填充
           saveInput(name);
           //如果输入正常，插入数据中
           RankBean bean=new RankBean(name ,score,level);
           rMgr.addRank(bean);
           Toast.makeText(WinActivity.this, "添加记录成功", Toast.LENGTH_SHORT).show();
           startActivity(SecondActivity.class);
           finish();
        }
    });
    }

    // 加载用户的保存的数据
    private String loadInput()
    {
        DataInputStream dis = null;
        try
        {
            dis = new DataInputStream(openFileInput("game_name.txt"));
            String name = dis.readUTF();
            return name;
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            if(dis!=null){
                try
                {
                    dis.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    // 保存用户的输入
    private void saveInput(String name)
    {
        FileOutputStream out = null;
        DataOutputStream dos = null;
        try
        { // 流关闭的顺序一般是：先开的，后关闭
            out=openFileOutput("game_name.txt", Context.MODE_PRIVATE);
            dos = new DataOutputStream(out);
            dos.writeUTF(name);
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {

            try
            {
                if (dos != null)
                {
                    dos.close();
                }
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}