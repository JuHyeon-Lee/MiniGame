package com.example.minigame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Card extends AppCompatActivity {

    int cnt1;
    int[] click = new int[1000];
    int[] card;

    int cnt = 30;
    int done = 0;

    boolean finish_thread = false;
    boolean wait_thread = false;

    final startGame thread_startGame = new startGame();
    final endGame thread_endGame = new endGame();

    playGame thread_playGame = new playGame();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_card);

        finish_thread = false;
        wait_thread = false;

        card = rndcard();
        setcard(card);

        DialogView_Start();

        ImageView card1 = (ImageView) findViewById(R.id.card1);
        ImageView card2 = (ImageView) findViewById(R.id.card2);
        ImageView card3 = (ImageView) findViewById(R.id.card3);
        ImageView card4 = (ImageView) findViewById(R.id.card4);
        ImageView card5 = (ImageView) findViewById(R.id.card5);
        ImageView card6 = (ImageView) findViewById(R.id.card6);
        ImageView card7 = (ImageView) findViewById(R.id.card7);
        ImageView card8 = (ImageView) findViewById(R.id.card8);
        ImageView card9 = (ImageView) findViewById(R.id.card9);
        ImageView card10 = (ImageView) findViewById(R.id.card10);
        ImageView card11 = (ImageView) findViewById(R.id.card11);
        ImageView card12 = (ImageView) findViewById(R.id.card12);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[0];
                cnt1++;
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[1];
                cnt1++;
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[2];
                cnt1++;
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[3];
                cnt1++;
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[4];
                cnt1++;
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[5];
                cnt1++;
            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[6];
                cnt1++;
            }
        });
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[7];
                cnt1++;
            }
        });
        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[8];
                cnt1++;
            }
        });
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[9];
                cnt1++;
            }
        });
        card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[10];
                cnt1++;
            }
        });
        card12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click[cnt1]=card[11];
                cnt1++;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent1 = getIntent();
        String s = intent1.getStringExtra("이름");
        TextView name = (TextView) findViewById(R.id.name_card);
        name.setText(s);

        TextView highscore = (TextView) findViewById(R.id.highscore_in_card);
        highscore.setText(Integer.toString(intent1.getIntExtra("최고점수",0)));

        BackgroundMusic.mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BackgroundMusic.mp.pause();
        DialogView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wait_thread=true;
        finish_thread=true;
    }

    @Override
    public void onBackPressed() {
        DialogView();
    }

    private void DialogView(){
        wait_thread=true;
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Card.this);
        alert_confirm.setMessage("일시정지").setCancelable(false).setPositiveButton("게임선택",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Card.this, GameSelect.class);

                        String name = "카드";

                        TextView score = (TextView) findViewById(R.id.score_card);
                        int scr = Integer.valueOf(score.getText().toString());

                        TextView highscore = (TextView) findViewById(R.id.highscore_in_card);
                        if( Integer.valueOf(highscore.getText().toString()) > scr){
                            scr = Integer.valueOf(highscore.getText().toString());
                        }

                        TextView s2 = (TextView) findViewById(R.id.name_card);
                        String s = s2.getText().toString();
                        intent.putExtra("이름", s);

                        intent.putExtra("종목", name);
                        intent.putExtra("점수", scr);

                        startActivity(intent);

                        finish();
                    }
                }).setNegativeButton("계속하기",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wait_thread=false;
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    private void DialogView_Start(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Card.this);
        alert_confirm.setMessage("\n짝맞추기 : 게임설명\n\n" +
                "같은 그림인 카드끼리 짝을 맞추면 없어집니다. " +
                "제한시간 내에 최대한 많이 맞춰보세요! " +
                "\n\n제한시간 30초\n").setCancelable(false).setPositiveButton("게임선택",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Card.this, GameSelect.class);

                        String name = "카드";

                        TextView score = (TextView) findViewById(R.id.score_card);
                        int scr = Integer.valueOf(score.getText().toString());

                        TextView highscore = (TextView) findViewById(R.id.highscore_in_card);
                        if( Integer.valueOf(highscore.getText().toString()) > scr){
                            scr = Integer.valueOf(highscore.getText().toString());
                        }

                        TextView s2 = (TextView) findViewById(R.id.name_card);
                        String s = s2.getText().toString();
                        intent.putExtra("이름", s);

                        intent.putExtra("종목", name);
                        intent.putExtra("점수", scr);

                        startActivity(intent);

                        finish();
                    }
                }).setNegativeButton("게임시작",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        thread_playGame.start();
                        thread_startGame.start();
                        thread_endGame.start();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    private void DialogView_End() {
        wait_thread=true;
        TextView score = (TextView) findViewById(R.id.score_card);
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Card.this);
        alert_confirm.setMessage("게임 종료\n\n" +
                "최종 점수 : "+score.getText().toString()+"점\n").setCancelable(false).setPositiveButton("게임선택",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Card.this, GameSelect.class);

                        String name = "카드";

                        TextView score = (TextView) findViewById(R.id.score_card);
                        int scr = Integer.valueOf(score.getText().toString());

                        TextView highscore = (TextView) findViewById(R.id.highscore_in_card);
                        if( Integer.valueOf(highscore.getText().toString()) > scr){
                            scr = Integer.valueOf(highscore.getText().toString());
                        }

                        TextView s2 = (TextView) findViewById(R.id.name_card);
                        String s = s2.getText().toString();
                        intent.putExtra("이름", s);

                        intent.putExtra("종목", name);
                        intent.putExtra("점수", scr);

                        startActivity(intent);

                        finish();
                    }
                }).setNegativeButton("다시하기",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wait_thread=false;
                        cnt=30;
                        TextView time = (TextView) findViewById(R.id.time_card);
                        time.setText("30");
                        done=0;
                        TextView score = (TextView) findViewById(R.id.score_card);
                        score.setText("0");
                        dialog.cancel();

                        ImageView card1 = (ImageView) findViewById(R.id.card1);
                        ImageView card2 = (ImageView) findViewById(R.id.card2);
                        ImageView card3 = (ImageView) findViewById(R.id.card3);
                        ImageView card4 = (ImageView) findViewById(R.id.card4);
                        ImageView card5 = (ImageView) findViewById(R.id.card5);
                        ImageView card6 = (ImageView) findViewById(R.id.card6);
                        ImageView card7 = (ImageView) findViewById(R.id.card7);
                        ImageView card8 = (ImageView) findViewById(R.id.card8);
                        ImageView card9 = (ImageView) findViewById(R.id.card9);
                        ImageView card10 = (ImageView) findViewById(R.id.card10);
                        ImageView card11 = (ImageView) findViewById(R.id.card11);
                        ImageView card12 = (ImageView) findViewById(R.id.card12);

                        card = rndcard();
                        setcard(card);

                        card1.setVisibility(View.VISIBLE);
                        card2.setVisibility(View.VISIBLE);
                        card3.setVisibility(View.VISIBLE);
                        card4.setVisibility(View.VISIBLE);
                        card5.setVisibility(View.VISIBLE);
                        card6.setVisibility(View.VISIBLE);
                        card7.setVisibility(View.VISIBLE);
                        card8.setVisibility(View.VISIBLE);
                        card9.setVisibility(View.VISIBLE);
                        card10.setVisibility(View.VISIBLE);
                        card11.setVisibility(View.VISIBLE);
                        card12.setVisibility(View.VISIBLE);
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    private class startGame extends Thread {
        public void run(){
            while(finish_thread==false){
                while(wait_thread==false){

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {e.printStackTrace();}

                    if(cnt>0){
                        cnt--;
                        startHandler.sendEmptyMessage(0);
                    }

                }
            }
        }
    }

    private Handler startHandler = new Handler (){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TextView time = (TextView) findViewById(R.id.time_card);
            time.setText(Integer.toString(cnt));
        }
    };

    private class endGame extends Thread {
        public void run(){
            while(finish_thread==false){
                while(wait_thread==false){

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {e.printStackTrace();}

                    if(done==0){
                        if(cnt==0){
                            endHandler.sendEmptyMessage(0);
                            done=1;
                        }
                    }

                }
            }
        }
    }

    private Handler endHandler = new Handler (){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DialogView_End();
        }
    };

    static int[] rndcard(){

        Random rnd = new Random();

        int[] num = new int[12];
        int[] card = new int[12];
        for(int i=0 ; i<12 ; i++)
            num[i]=i+2;
        for(int i=0 ; i<12 ; i++)
            card[i]=100;

        while(true){

            int rndnum = rnd.nextInt(12);
            for(int i=0 ; i<12 ; i++){
                if(card[rndnum]==100) {
                    if (num[i] != 0) {
                        card[rndnum] = num[i]/2;
                        num[i]=0;
                        break;
                    }
                }
                else
                    break;
            }

            int break1 = 0;
            for(int i=0 ; i<12 ; i++){
                if(card[i]==100)
                    break;
                else if(i==11)
                    break1=1;
            }
            if(break1==1)
                break;

        }

        System.out.print("@@@@@@@@@@@@@@@@@@@@@@@@@");
        for(int i=0 ; i<12 ; i++)
            System.out.print(" "+card[i]+" ");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");

        return card;
    }

    public void setcard(int[] card){

        ImageView card1 = (ImageView) findViewById(R.id.card1);
        ImageView card2 = (ImageView) findViewById(R.id.card2);
        ImageView card3 = (ImageView) findViewById(R.id.card3);
        ImageView card4 = (ImageView) findViewById(R.id.card4);
        ImageView card5 = (ImageView) findViewById(R.id.card5);
        ImageView card6 = (ImageView) findViewById(R.id.card6);
        ImageView card7 = (ImageView) findViewById(R.id.card7);
        ImageView card8 = (ImageView) findViewById(R.id.card8);
        ImageView card9 = (ImageView) findViewById(R.id.card9);
        ImageView card10 = (ImageView) findViewById(R.id.card10);
        ImageView card11 = (ImageView) findViewById(R.id.card11);
        ImageView card12 = (ImageView) findViewById(R.id.card12);

        for(int i=0; i<12 ; i++){
            if(i==0){
                if(card[i]==1)
                    card1.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card1.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card1.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card1.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card1.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card1.setBackgroundResource(R.drawable.card6star);
            }
            if(i==1){
                if(card[i]==1)
                    card2.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card2.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card2.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card2.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card2.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card2.setBackgroundResource(R.drawable.card6star);
            }
            if(i==2){
                if(card[i]==1)
                    card3.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card3.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card3.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card3.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card3.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card3.setBackgroundResource(R.drawable.card6star);
            }
            if(i==3){
                if(card[i]==1)
                    card4.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card4.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card4.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card4.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card4.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card4.setBackgroundResource(R.drawable.card6star);
            }
            if(i==4){
                if(card[i]==1)
                    card5.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card5.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card5.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card5.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card5.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card5.setBackgroundResource(R.drawable.card6star);
            }
            if(i==5){
                if(card[i]==1)
                    card6.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card6.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card6.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card6.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card6.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card6.setBackgroundResource(R.drawable.card6star);
            }
            if(i==6){
                if(card[i]==1)
                    card7.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card7.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card7.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card7.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card7.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card7.setBackgroundResource(R.drawable.card6star);
            }
            if(i==7){
                if(card[i]==1)
                    card8.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card8.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card8.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card8.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card8.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card8.setBackgroundResource(R.drawable.card6star);
            }
            if(i==8){
                if(card[i]==1)
                    card9.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card9.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card9.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card9.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card9.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card9.setBackgroundResource(R.drawable.card6star);
            }
            if(i==9){
                if(card[i]==1)
                    card10.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card10.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card10.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card10.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card10.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card10.setBackgroundResource(R.drawable.card6star);
            }
            if(i==10){
                if(card[i]==1)
                    card11.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card11.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card11.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card11.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card11.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card11.setBackgroundResource(R.drawable.card6star);
            }
            if(i==11){
                if(card[i]==1)
                    card12.setBackgroundResource(R.drawable.card1circle);
                if(card[i]==2)
                    card12.setBackgroundResource(R.drawable.card2clover);
                if(card[i]==3)
                    card12.setBackgroundResource(R.drawable.card3diamond);
                if(card[i]==4)
                    card12.setBackgroundResource(R.drawable.card4heart);
                if(card[i]==5)
                    card12.setBackgroundResource(R.drawable.card5spade);
                if(card[i]==6)
                    card12.setBackgroundResource(R.drawable.card6star);
            }
        }
    }

    private class playGame extends Thread{
        public void run() {

            while (finish_thread == false) {
                while (wait_thread == false) {

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(cnt1>1){
                        if(cnt1%2==0){
                            if(click[cnt1-1]==click[cnt1-2]){
                                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                if(MainActivity.vibration==true)
                                    vibe.vibrate(100);
                                handler_card.sendEmptyMessage(0);
                                handler_score.sendEmptyMessage(0);
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                cnt1++;
                                click[cnt1]=100;
                                cnt1++;
                            }
                        }
                    }

                }
            }
        }
    }

    private Handler handler_score = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int score1;
            TextView score = (TextView) findViewById(R.id.score_card);
            score1 = Integer.valueOf(score.getText().toString());
            score1+=10;
            score.setText(Integer.toString(score1));

            if(score1%60==0){
                ImageView card1 = (ImageView) findViewById(R.id.card1);
                ImageView card2 = (ImageView) findViewById(R.id.card2);
                ImageView card3 = (ImageView) findViewById(R.id.card3);
                ImageView card4 = (ImageView) findViewById(R.id.card4);
                ImageView card5 = (ImageView) findViewById(R.id.card5);
                ImageView card6 = (ImageView) findViewById(R.id.card6);
                ImageView card7 = (ImageView) findViewById(R.id.card7);
                ImageView card8 = (ImageView) findViewById(R.id.card8);
                ImageView card9 = (ImageView) findViewById(R.id.card9);
                ImageView card10 = (ImageView) findViewById(R.id.card10);
                ImageView card11 = (ImageView) findViewById(R.id.card11);
                ImageView card12 = (ImageView) findViewById(R.id.card12);

                card = rndcard();
                setcard(card);

                card1.setVisibility(View.VISIBLE);
                card2.setVisibility(View.VISIBLE);
                card3.setVisibility(View.VISIBLE);
                card4.setVisibility(View.VISIBLE);
                card5.setVisibility(View.VISIBLE);
                card6.setVisibility(View.VISIBLE);
                card7.setVisibility(View.VISIBLE);
                card8.setVisibility(View.VISIBLE);
                card9.setVisibility(View.VISIBLE);
                card10.setVisibility(View.VISIBLE);
                card11.setVisibility(View.VISIBLE);
                card12.setVisibility(View.VISIBLE);
            }
        }
    };

    private Handler handler_card = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ImageView card1 = (ImageView) findViewById(R.id.card1);
            ImageView card2 = (ImageView) findViewById(R.id.card2);
            ImageView card3 = (ImageView) findViewById(R.id.card3);
            ImageView card4 = (ImageView) findViewById(R.id.card4);
            ImageView card5 = (ImageView) findViewById(R.id.card5);
            ImageView card6 = (ImageView) findViewById(R.id.card6);
            ImageView card7 = (ImageView) findViewById(R.id.card7);
            ImageView card8 = (ImageView) findViewById(R.id.card8);
            ImageView card9 = (ImageView) findViewById(R.id.card9);
            ImageView card10 = (ImageView) findViewById(R.id.card10);
            ImageView card11 = (ImageView) findViewById(R.id.card11);
            ImageView card12 = (ImageView) findViewById(R.id.card12);

            Animation smaller = AnimationUtils.loadAnimation(Card.this, R.anim.smaller);

            for(int i=0 ; i<12 ; i++){

                if(card[i]==click[cnt1-2]){
                    if(i==0){
                        card1.startAnimation(smaller);
                        card1.setVisibility(View.INVISIBLE);
                    }
                    if(i==1){
                        card2.startAnimation(smaller);
                        card2.setVisibility(View.INVISIBLE);
                    }
                    if(i==2){
                        card3.startAnimation(smaller);
                        card3.setVisibility(View.INVISIBLE);
                    }
                    if(i==3){
                        card4.startAnimation(smaller);
                        card4.setVisibility(View.INVISIBLE);
                    }
                    if(i==4){
                        card5.startAnimation(smaller);
                        card5.setVisibility(View.INVISIBLE);
                    }
                    if(i==5){
                        card6.startAnimation(smaller);
                        card6.setVisibility(View.INVISIBLE);
                    }
                    if(i==6){
                        card7.startAnimation(smaller);
                        card7.setVisibility(View.INVISIBLE);
                    }
                    if(i==7){
                        card8.startAnimation(smaller);
                        card8.setVisibility(View.INVISIBLE);
                    }
                    if(i==8){
                        card9.startAnimation(smaller);
                        card9.setVisibility(View.INVISIBLE);
                    }
                    if(i==9){
                        card10.startAnimation(smaller);
                        card10.setVisibility(View.INVISIBLE);
                    }
                    if(i==10){
                        card11.startAnimation(smaller);
                        card11.setVisibility(View.INVISIBLE);
                    }
                    if(i==11){
                        card12.startAnimation(smaller);
                        card12.setVisibility(View.INVISIBLE);
                    }
                }
            }

        }
    };
}
