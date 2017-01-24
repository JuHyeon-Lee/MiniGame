package com.example.minigame;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);

        Button vibrate_on = (Button) findViewById(R.id.vibrate_on);
        Button vibrate_off = (Button) findViewById(R.id.vibrate_off);

        if(MainActivity.vibration==true)
            vibrate_off.setVisibility(View.INVISIBLE);
        else if(MainActivity.vibration==false)
            vibrate_on.setVisibility(View.INVISIBLE);

        vibrate_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button vibrate_on = (Button) findViewById(R.id.vibrate_on);
                Button vibrate_off = (Button) findViewById(R.id.vibrate_off);

                MainActivity.vibration=false;

                vibrate_off.setVisibility(View.VISIBLE);
                vibrate_on.setVisibility(View.INVISIBLE);
            }
        });

        vibrate_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button vibrate_on = (Button) findViewById(R.id.vibrate_on);
                Button vibrate_off = (Button) findViewById(R.id.vibrate_off);

                MainActivity.vibration=true;

                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);

                vibrate_on.setVisibility(View.VISIBLE);
                vibrate_off.setVisibility(View.INVISIBLE);
            }
        });

        Button clear_cache = (Button) findViewById(R.id.clear_cache);

        clear_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearApplicationCache(null);
            }
        });

        Button sound_on = (Button) findViewById(R.id.sound_on);
        Button sound_off = (Button) findViewById(R.id.sound_off);

        if(BackgroundMusic.working==true){
            sound_on.setVisibility(View.VISIBLE);
            sound_off.setVisibility(View.INVISIBLE);
        }
        else if(BackgroundMusic.working==false){
            sound_on.setVisibility(View.INVISIBLE);
            sound_off.setVisibility(View.VISIBLE);
        }


        sound_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Setting.this, BackgroundMusic.class);
                stopService(intent1);

                Button sound_on = (Button) findViewById(R.id.sound_on);
                Button sound_off = (Button) findViewById(R.id.sound_off);

                sound_on.setVisibility(View.INVISIBLE);
                sound_off.setVisibility(View.VISIBLE);
            }
        });

        sound_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Setting.this, BackgroundMusic.class);
                startService(intent1);

                Button sound_on = (Button) findViewById(R.id.sound_on);
                Button sound_off = (Button) findViewById(R.id.sound_off);

                sound_on.setVisibility(View.VISIBLE);
                sound_off.setVisibility(View.INVISIBLE);
            }
        });

        Button sendmail = (Button) findViewById(R.id.sendmail);
        sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("mailto:leon6095@gmail.com");

                Intent it = new Intent(Intent.ACTION_SENDTO, uri);

                startActivity(it);

            }
        });

        Button goinfo = (Button) findViewById(R.id.information);
        goinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://blog.naver.com/leon5995/220914672632");

                Intent it  = new Intent(Intent.ACTION_VIEW,uri);

                startActivity(it);
            }
        });
    }

    private void clearApplicationCache(java.io.File dir){
        if(dir==null)
            dir = getCacheDir();
        else;
        if(dir==null)
            return;
        else;
        java.io.File[] children = dir.listFiles();
        try{
            for(int i=0;i<children.length;i++)
                if(children[i].isDirectory())
                    clearApplicationCache(children[i]);
                else children[i].delete();
            Toast toast = Toast.makeText(this, "캐시가 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
        catch(Exception e){}
    }

    @Override
    protected void onPause() {
        BackgroundMusic.mp.pause();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        BackgroundMusic.mp.start();
    }

}
