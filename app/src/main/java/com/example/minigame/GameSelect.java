package com.example.minigame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameSelect extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_games);

        Intent intent1 = getIntent();

        String s = intent1.getStringExtra("이름");

        TextView name = (TextView) findViewById(R.id.name_games);

        name.setText(s);

        ///////////////////////////////////////////////////////////////////// 랭킹

        ImageButton ranking = (ImageButton) findViewById(R.id.ranking);

        ranking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(GameSelect.this, Ranking.class);

                TextView name = (TextView) findViewById(R.id.name_games);
                String s = name.getText().toString();
                intent2.putExtra("이름", s);

                TextView score_dudogi = (TextView) findViewById(R.id.highscore_dudogi);
                intent2.putExtra("두더지점수", Integer.valueOf(score_dudogi.getText().toString()));

                TextView score_waffle = (TextView) findViewById(R.id.highscore_waffle);
                intent2.putExtra("와플점수", Integer.valueOf(score_waffle.getText().toString()));

                TextView score_card = (TextView) findViewById(R.id.highscore_card);
                intent2.putExtra("카드점수", Integer.valueOf(score_card.getText().toString()));

                TextView score_muffin = (TextView) findViewById(R.id.highscore_muffin);
                intent2.putExtra("머핀점수", Integer.valueOf(score_muffin.getText().toString()));

                startActivity(intent2);
            }
        });

        ///////////////////////////////////////////////////////////////////// 설정

        ImageButton go_setting = (ImageButton) findViewById(R.id.go_setting);

        go_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(GameSelect.this, Setting.class);

                startActivity(intent3);
            }
        });

        ///////////////////////////////////////////////////////////////////// 두더지

        ImageButton dudogi = (ImageButton) findViewById(R.id.play_dudogi);

        dudogi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(GameSelect.this, Dudogi.class);

                TextView name = (TextView) findViewById(R.id.name_games);
                String s = name.getText().toString();
                intent2.putExtra("이름", s);

                TextView highscore = (TextView) findViewById(R.id.highscore_dudogi);
                intent2.putExtra("최고점수", Integer.valueOf(highscore.getText().toString()));

                startActivity(intent2);

                finish();
            }
        });

        ///////////////////////////////////////////////////////////////////// 머핀

        ImageButton play_muffin = (ImageButton) findViewById(R.id.play_muffin);
        play_muffin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(GameSelect.this, Muffin.class);

                TextView name = (TextView) findViewById(R.id.name_games);
                String s = name.getText().toString();
                intent2.putExtra("이름", s);

                TextView highscore = (TextView) findViewById(R.id.highscore_muffin);
                intent2.putExtra("최고점수", Integer.valueOf(highscore.getText().toString()));

                startActivity(intent2);

                finish();
            }
        });

        ///////////////////////////////////////////////////////////////////// 카드

        ImageButton play_card = (ImageButton) findViewById(R.id.play_card);
        play_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(GameSelect.this, Card.class);

                TextView name = (TextView) findViewById(R.id.name_games);
                String s = name.getText().toString();
                intent2.putExtra("이름", s);

                TextView highscore = (TextView) findViewById(R.id.highscore_card);
                intent2.putExtra("최고점수", Integer.valueOf(highscore.getText().toString()));

                startActivity(intent2);

                finish();
            }
        });

        ///////////////////////////////////////////////////////////////////// 와플

        ImageButton play_waffle = (ImageButton) findViewById(R.id.play_waffle);
        play_waffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(GameSelect.this, Waffle.class);

                TextView name = (TextView) findViewById(R.id.name_games);
                String s = name.getText().toString();
                intent2.putExtra("이름", s);

                TextView highscore = (TextView) findViewById(R.id.highscore_waffle);
                intent2.putExtra("최고점수", Integer.valueOf(highscore.getText().toString()));

                startActivity(intent2);

                finish();
            }
        });

        /////////////////////////////////////////////////////////////////////
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent1 = getIntent();

        String s2 = intent1.getStringExtra("이름");
        TextView name = (TextView) findViewById(R.id.name_games);
        name.setText(s2);

        String s = intent1.getStringExtra("종목");
        int score = intent1.getIntExtra("점수",0);

        if("머핀".equals(s)){
            TextView highscore_muffin = (TextView) findViewById(R.id.highscore_muffin);
            highscore_muffin.setText(Integer.toString(score));
        }
        else if("두더지".equals(s)){
            TextView highscore_dudogi = (TextView) findViewById(R.id.highscore_dudogi);
            highscore_dudogi.setText(Integer.toString(score));
        }
        else if("카드".equals(s)){
            TextView highscore_card = (TextView) findViewById(R.id.highscore_card);
            highscore_card.setText(Integer.toString(score));
        }
        else if("와플".equals(s)){
            TextView highscore_waffle = (TextView) findViewById(R.id.highscore_waffle);
            highscore_waffle.setText(Integer.toString(score));
        }
        else;

        BackgroundMusic.mp.start();
    }

    @Override
    protected void onPause() {
        BackgroundMusic.mp.pause();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DialogView();
    }

    private void DialogView(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(GameSelect.this);
        alert_confirm.setMessage("게임을 종료하시겠습니까?").setCancelable(false).setPositiveButton("네",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.MA.finish();
                        finish();
                    }
                }).setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }
}

