package com.example.gamepuzzleview.adapter.bean;

import com.example.gamepuzzleview.GameActivity;

public class RankBean
{
    
int id;
String name;
int score;
int level;

public RankBean(int id, String name, int score, int level)
{
    super();
    this.id = id;
    this.name = name;
    this.score = score;
    this.level = level;
}

public RankBean( String name, int score,int level)
{
    
    this.name = name;
    this.score = score;
    this.level=level;
}

public int getLevel()
{
    return level;
}

public void setLevel(int level)
{
    this.level = level;
}

public int getId()
{
    return id;
}

public void setId(int id)
{
    this.id = id;
}

public String getName()
{
    return name;
}

public void setName(String name)
{
    this.name = name;
}

public int getScore()
{
    return score;
}

public void setScore(int score)
{
    this.score = score;
}

}
