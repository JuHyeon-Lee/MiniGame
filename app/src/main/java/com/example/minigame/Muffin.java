package com.example.minigame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class Muffin extends AppCompatActivity{

    final startGame thread_startGame = new startGame();
    final endGame thread_endGame = new endGame();
    int cnt = 30;
    int done = 0;
    boolean finish_thread = false;
    boolean wait_thread = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_muffin);

        finish_thread = false;
        wait_thread = false;

        ImageView muffin = (ImageView) findViewById(R.id.muffin);
        ImageView shit = (ImageView) findViewById(R.id.shit);

        muffin.setVisibility(View.VISIBLE);
        shit.setVisibility(View.INVISIBLE);

        ImageView correct = (ImageView) findViewById(R.id.correct);
        ImageView wrong = (ImageView) findViewById(R.id.wrong);

        correct.setVisibility(View.INVISIBLE);
        wrong.setVisibility(View.INVISIBLE);

        DialogView_Start();

        Button eat_muffin = (Button) findViewById(R.id.eat_muffin);
        eat_muffin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rnd = new Random();

                int score1;
                int rndnum;

                ImageView muffin = (ImageView) findViewById(R.id.muffin);
                ImageView shit = (ImageView) findViewById(R.id.shit);

                ImageView correct = (ImageView) findViewById(R.id.correct);
                ImageView wrong = (ImageView) findViewById(R.id.wrong);

                TextView score = (TextView) findViewById(R.id.score_muffin);
                score1=Integer.valueOf(score.getText().toString());

                if(muffin.getVisibility()==View.VISIBLE){
                    wrong.setVisibility(View.INVISIBLE);
                    correct.setVisibility(View.VISIBLE);
                    score1+=10;
                }
                else if(shit.getVisibility()==View.VISIBLE){
                    correct.setVisibility(View.INVISIBLE);
                    wrong.setVisibility(View.VISIBLE);
                    score1-=10;
                }

                score.setText(String.valueOf(score1));

                rndnum = rnd.nextInt(2);

                muffin.setVisibility(View.INVISIBLE);
                shit.setVisibility(View.INVISIBLE);

                if(rndnum==1){
                    muffin.setVisibility(View.INVISIBLE);
                    shit.setVisibility(View.VISIBLE);
                }
                else if(rndnum==0){
                    muffin.setVisibility(View.VISIBLE);
                    shit.setVisibility(View.INVISIBLE);
                }
            }
        });

        Button throw_shit = (Button) findViewById(R.id.throw_shit);
        throw_shit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rnd = new Random();

                int score1;
                int rndnum;

                ImageView muffin = (ImageView) findViewById(R.id.muffin);
                ImageView shit = (ImageView) findViewById(R.id.shit);

                ImageView correct = (ImageView) findViewById(R.id.correct);
                ImageView wrong = (ImageView) findViewById(R.id.wrong);

                TextView score = (TextView) findViewById(R.id.score_muffin);
                score1=Integer.valueOf(score.getText().toString());

                if(muffin.getVisibility()==View.VISIBLE){
                    wrong.setVisibility(View.VISIBLE);
                    correct.setVisibility(View.INVISIBLE);
                    score1-=10;
                }
                else if(shit.getVisibility()==View.VISIBLE){
                    correct.setVisibility(View.VISIBLE);
                    wrong.setVisibility(View.INVISIBLE);
                    score1+=10;
                }

                score.setText(String.valueOf(score1));

                rndnum = rnd.nextInt(2);

                muffin.setVisibility(View.INVISIBLE);
                shit.setVisibility(View.INVISIBLE);

                if(rndnum==1){
                    muffin.setVisibility(View.INVISIBLE);
                    shit.setVisibility(View.VISIBLE);
                }
                else if(rndnum==0){
                    muffin.setVisibility(View.VISIBLE);
                    shit.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        Intent intent1 = getIntent();

        String s = intent1.getStringExtra("이름");
        TextView name = (TextView) findViewById(R.id.name_muffin);
        name.setText(s);

        TextView highscore = (TextView) findViewById(R.id.highscore_in_muffin);
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
    public void onBackPressed() {
        DialogView();
    }

    private void DialogView(){

        wait_thread=true;

        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Muffin.this);
        alert_confirm.setMessage("일시정지").setCancelable(false).setPositiveButton("게임선택",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Muffin.this, GameSelect.class);

                        String name = "머핀";

                        TextView score = (TextView) findViewById(R.id.score_muffin);
                        int scr = Integer.valueOf(score.getText().toString());

                        TextView highscore = (TextView) findViewById(R.id.highscore_in_muffin);
                        if( Integer.valueOf(highscore.getText().toString()) > scr){
                            scr = Integer.valueOf(highscore.getText().toString());
                        }

                        TextView s2 = (TextView) findViewById(R.id.name_muffin);
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
                        dialog.cancel();
                        wait_thread=false;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    private void DialogView_Start(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Muffin.this);
        alert_confirm.setMessage("\n머핀먹기 : 게임설명\n\n" +
                "화면에 보이는 그림이 머핀일 경우\n" +
                "먹기를 클릭하면 10점 획득\n" +
                "화면에 보이는 그림이 똥일 경우\n" +
                "버리기를 클릭하면 10점 획득\n" +
                "반대로 클릭하면 10점 감점\n" +
                "제한시간 30초\n")
                .setCancelable(false).setPositiveButton("게임선택",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Muffin.this, GameSelect.class);

                        String name = "머핀";

                        TextView score = (TextView) findViewById(R.id.score_muffin);
                        int scr = Integer.valueOf(score.getText().toString());

                        TextView highscore = (TextView) findViewById(R.id.highscore_in_muffin);
                        if( Integer.valueOf(highscore.getText().toString()) > scr){
                            scr = Integer.valueOf(highscore.getText().toString());
                        }

                        TextView s2 = (TextView) findViewById(R.id.name_muffin);
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
                        dialog.cancel();
                        thread_startGame.start();
                        thread_endGame.start();
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    private void DialogView_End(){
        TextView score = (TextView) findViewById(R.id.score_muffin);
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Muffin.this);
        alert_confirm.setMessage("게임 종료\n\n" +
                "최종 점수 : "+score.getText().toString()+"점\n")
                .setCancelable(false).setPositiveButton("게임선택",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Muffin.this, GameSelect.class);

                        String name = "머핀";

                        TextView score = (TextView) findViewById(R.id.score_muffin);
                        int scr = Integer.valueOf(score.getText().toString());

                        TextView highscore = (TextView) findViewById(R.id.highscore_in_muffin);
                        if( Integer.valueOf(highscore.getText().toString()) > scr){
                            scr = Integer.valueOf(highscore.getText().toString());
                        }

                        TextView s2 = (TextView) findViewById(R.id.name_muffin);
                        String s = s2.getText().toString();
                        intent.putExtra("이름", s);

                        intent.putExtra("종목", name);
                        intent.putExtra("점수", scr);

                        startActivity(intent);

                        done=0;

                        finish();
                    }
                }).setNegativeButton("재시작",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cnt=30;
                        TextView time = (TextView) findViewById(R.id.time_muffin);
                        time.setText("30");
                        done=0;
                        TextView score = (TextView) findViewById(R.id.score_muffin);
                        score.setText("0");
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wait_thread=true;
        finish_thread=true;
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
            TextView time = (TextView) findViewById(R.id.time_muffin);
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
}
