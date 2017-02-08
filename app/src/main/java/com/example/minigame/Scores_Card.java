package com.example.minigame;

import java.io.Serializable;
import android.graphics.drawable.Drawable;

public class Scores_Card implements Serializable {

    int rank;
    String name;
    int score;
    int d;

    public Scores_Card(int rank, String name, int score, int d){
        this.rank = rank;
        this.name = name;
        this.score = score;
        this.d = d;
    }

    public void setRank(int rank){
        this.rank = rank;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setD(int d) { this.d = d; }

    public int getRank(){
        return rank;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    public int getD(){
        return d;
    }

}
