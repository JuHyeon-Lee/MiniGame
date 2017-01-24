package com.example.minigame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static boolean vibration = true;
    public static Activity MA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        MA=MainActivity.this;

        Intent intent0 = new Intent(MainActivity.this, BackgroundMusic.class);
        startService(intent0);

        ImageButton next = (ImageButton) findViewById(R.id.game_start);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText player = (EditText) findViewById(R.id.name_player);
                String name = player.getText().toString();

                if("".equals(name)){
                    Toast toast = Toast.makeText(getApplicationContext(),"닉네임을 입력해주세요!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, GameSelect.class);

                    intent.putExtra("이름", name);

                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        BackgroundMusic.mp.pause();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        BackgroundMusic.mp.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent1 = new Intent(MainActivity.this, BackgroundMusic.class);
        stopService(intent1);
    }
}
