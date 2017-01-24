package com.example.minigame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BackgroundMusic extends Service {

    public static MediaPlayer mp;
    public static boolean working;

    @Override
    public void onCreate() {
        super.onCreate();
        working=true;
    }

    @Override
    public void onDestroy() {
        mp.stop();
        working=false;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp = MediaPlayer.create(this, R.raw.background);
        mp.setLooping(true);
        mp.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
