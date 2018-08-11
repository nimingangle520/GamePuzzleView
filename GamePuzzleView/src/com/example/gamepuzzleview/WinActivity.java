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

        // Toast.makeText(this,"level��"+level+"  score��"+score,
        // Toast.LENGTH_SHORT).show();
        rMgr = new RankManager(this);
        // �ؼ�
        etName = (EditText) findViewById(R.id.editText1);
        String inputName = loadInput();
        etName.setTextKeepState(inputName);
       //����¼�
       findViewById(R.id.button1).setOnClickListener(new OnClickListener()
    {
        
        @Override
        public void onClick(View v)
        {
            String name=etName.getText().toString();
            if(null==name||"".equals(name)){
                Toast.makeText(WinActivity.this, "����������",Toast.LENGTH_SHORT).show();
           return;
            }
            //����name ��sd�����´��û�����Ӯ�˽���ʱ��ֱ��ʹ������������
           saveInput(name);
           //�����������������������
           RankBean bean=new RankBean(name ,score,level);
           rMgr.addRank(bean);
           Toast.makeText(WinActivity.this, "��Ӽ�¼�ɹ�", Toast.LENGTH_SHORT).show();
           startActivity(SecondActivity.class);
           finish();
        }
    });
    }

    // �����û��ı��������
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

    // �����û�������
    private void saveInput(String name)
    {
        FileOutputStream out = null;
        DataOutputStream dos = null;
        try
        { // ���رյ�˳��һ���ǣ��ȿ��ģ���ر�
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