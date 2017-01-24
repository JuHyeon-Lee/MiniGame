package com.example.minigame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Waffle extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_waffle);
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
