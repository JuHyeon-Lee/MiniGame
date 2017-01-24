package com.example.minigame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class Ranking extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ranking);

    }

    @Override
    protected void onStart() {
        super.onStart();

        BackgroundMusic.mp.start();
    }

    @Override
    protected void onPause() {
        BackgroundMusic.mp.pause();
        super.onPause();
    }
}
