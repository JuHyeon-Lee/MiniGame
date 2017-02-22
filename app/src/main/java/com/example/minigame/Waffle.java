package com.example.minigame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class Waffle extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_waffle);

        final Random rnd = new Random();

        final ImageView stone1 = (ImageView) findViewById(R.id.stone1);
        final ImageView stone2 = (ImageView) findViewById(R.id.stone2);
        final ImageView stone3 = (ImageView) findViewById(R.id.stone3);
        final ImageView stone4 = (ImageView) findViewById(R.id.stone4);

        final ImageView pickaxe1 = (ImageView) findViewById(R.id.pickaxe1);
        final ImageView pickaxe2 = (ImageView) findViewById(R.id.pickaxe2);
        final ImageView pickaxe3 = (ImageView) findViewById(R.id.pickaxe3);
        final ImageView pickaxe4 = (ImageView) findViewById(R.id.pickaxe4);

        final TextView score_waffle = (TextView) findViewById(R.id.score_waffle);

        stone1.setVisibility(View.VISIBLE);

        RelativeLayout layout_pickaxe = (RelativeLayout) findViewById(R.id.layout_pickaxe);
        layout_pickaxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score1 = Integer.valueOf(score_waffle.getText().toString());
                score1+=1;
                score_waffle.setText(Integer.toString(score1));

                Animation size_down = AnimationUtils.loadAnimation(Waffle.this, R.anim.size_down);
                score_waffle.startAnimation(size_down);

                if(Integer.valueOf(score_waffle.getText().toString())>99){
                    stone1.setVisibility(View.INVISIBLE);
                    stone2.setVisibility(View.VISIBLE);
                    stone3.setVisibility(View.INVISIBLE);
                    stone4.setVisibility(View.INVISIBLE);
                }
                if(Integer.valueOf(score_waffle.getText().toString())>199){
                    stone1.setVisibility(View.INVISIBLE);
                    stone2.setVisibility(View.INVISIBLE);
                    stone3.setVisibility(View.VISIBLE);
                    stone4.setVisibility(View.INVISIBLE);
                }
                if(Integer.valueOf(score_waffle.getText().toString())>299){
                    stone1.setVisibility(View.INVISIBLE);
                    stone2.setVisibility(View.INVISIBLE);
                    stone3.setVisibility(View.INVISIBLE);
                    stone4.setVisibility(View.VISIBLE);
                }

                int num = rnd.nextInt(4);

                if(num==0){
                    pickaxe1.setVisibility(View.VISIBLE);
                    Animation pickaxe_right = AnimationUtils.loadAnimation(Waffle.this, R.anim.pickaxe_right);
                    pickaxe1.startAnimation(pickaxe_right);
                }
                else if(num==1){
                    pickaxe2.setVisibility(View.VISIBLE);
                    Animation pickaxe_left = AnimationUtils.loadAnimation(Waffle.this, R.anim.pickaxe_left);
                    pickaxe2.startAnimation(pickaxe_left);
                }
                else if(num==2){
                    pickaxe3.setVisibility(View.VISIBLE);
                    Animation pickaxe_right = AnimationUtils.loadAnimation(Waffle.this, R.anim.pickaxe_right);
                    pickaxe3.startAnimation(pickaxe_right);
                }
                else if(num==3){
                    pickaxe4.setVisibility(View.VISIBLE);
                    Animation pickaxe_left = AnimationUtils.loadAnimation(Waffle.this, R.anim.pickaxe_left);
                    pickaxe4.startAnimation(pickaxe_left);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent1 = getIntent();
        String s = intent1.getStringExtra("이름");
        TextView name = (TextView) findViewById(R.id.name_waffle);
        name.setText(s);

        TextView highscore = (TextView) findViewById(R.id.highscore_in_waffle);
        highscore.setText(Integer.toString(intent1.getIntExtra("최고점수",0)));

        RelativeLayout layout_pickaxe = (RelativeLayout) findViewById(R.id.layout_pickaxe);
        layout_pickaxe.bringToFront();

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
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Waffle.this);
        alert_confirm.setMessage("일시정지").setCancelable(false).setPositiveButton("게임선택으로",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Waffle.this, GameSelect.class);

                        String name = "와플";

                        TextView score = (TextView) findViewById(R.id.score_waffle);
                        int scr = Integer.valueOf(score.getText().toString());

                        TextView highscore = (TextView) findViewById(R.id.highscore_in_waffle);
                        if( Integer.valueOf(highscore.getText().toString()) > scr){
                            scr = Integer.valueOf(highscore.getText().toString());
                        }

                        TextView s2 = (TextView) findViewById(R.id.name_waffle);
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
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }
}
