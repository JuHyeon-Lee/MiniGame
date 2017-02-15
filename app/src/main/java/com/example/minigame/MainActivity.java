package com.example.minigame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static boolean vibration = true;
    public static boolean sound = true;

    public static Activity MA;

    boolean id_checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        final ImageButton next = (ImageButton) findViewById(R.id.game_start);
        next.setVisibility(View.INVISIBLE);

        SharedPreferences Setting = getSharedPreferences("Setting", MODE_PRIVATE);
        sound = Setting.getBoolean("음악", true);
        vibration = Setting.getBoolean("진동", true);

        if(sound==true){
            Intent intent0 = new Intent(MainActivity.this, BackgroundMusic.class);
            startService(intent0);
        }
        else if(sound==false){
            Intent intent0 = new Intent(MainActivity.this, BackgroundMusic.class);
            startService(intent0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stopService(intent0);
        }

        final SharedPreferences AutoLogin = getSharedPreferences("AutoLogin", MODE_PRIVATE);
        if(AutoLogin.getBoolean("자동로그인", false)==true){
            String user_id = AutoLogin.getString("자동아이디", "ERROR");
            TextView name_main = (TextView) findViewById(R.id.name_main);
            name_main.setText(user_id);

            Toast toast = Toast.makeText(getApplicationContext(),user_id+"님 환영합니다!", Toast.LENGTH_SHORT);
            toast.show();

            LinearLayout undone = (LinearLayout) findViewById(R.id.login_undone);
            LinearLayout done = (LinearLayout) findViewById(R.id.login_done);
            undone.setVisibility(View.INVISIBLE);
            done.setVisibility(View.VISIBLE);

            next.setVisibility(View.VISIBLE);

            CheckBox auto = (CheckBox) findViewById(R.id.autologin);
            auto.setVisibility(View.INVISIBLE);

            Intent intent = new Intent(MainActivity.this, GameSelect.class);

            String name = name_main.getText().toString();

            intent.putExtra("이름", name);

            startActivity(intent);
        }

        MA=MainActivity.this;

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView name_main = (TextView) findViewById(R.id.name_main);
                String name = name_main.getText().toString();

                TableLayout signin = (TableLayout) findViewById(R.id.layout_sign);

                if("ID".equals(name)){
                    Toast toast = Toast.makeText(getApplicationContext(),"로그인 후 눌러주세요!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, GameSelect.class);

                    intent.putExtra("이름", name);

                    startActivity(intent);
                }
            }
        });

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText user_id = (EditText) findViewById(R.id.id_login);
                EditText user_pw = (EditText) findViewById(R.id.pw_login);

                if("".equals(user_id.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(),"아이디를 입력해주세요!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if("".equals(user_pw.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    SharedPreferences UserData = getSharedPreferences("UserData", MODE_PRIVATE);
                    String pw = UserData.getString(user_id.getText().toString(), "none");
                    if("none".equals(pw)){
                        Toast toast = Toast.makeText(getApplicationContext(),"등록되지 않은 아이디입니다!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else if(pw.equals(user_pw.getText().toString())==false){
                        Toast toast = Toast.makeText(getApplicationContext(),"비밀번호가 틀립니다!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        TextView name_main = (TextView) findViewById(R.id.name_main);
                        name_main.setText(user_id.getText().toString());

                        Toast toast = Toast.makeText(getApplicationContext(),user_id.getText().toString()+"님 환영합니다!", Toast.LENGTH_SHORT);
                        toast.show();

                        SharedPreferences.Editor editor = AutoLogin.edit();
                        CheckBox auto = (CheckBox) findViewById(R.id.autologin);
                        if(auto.isChecked()==true){
                            editor.putBoolean("자동로그인", true);
                            editor.putString("자동아이디", user_id.getText().toString());
                        }
                        else
                            editor.putBoolean("자동로그인", false);

                        editor.commit();

                        Intent intent = new Intent(MainActivity.this, GameSelect.class);

                        String name = name_main.getText().toString();

                        intent.putExtra("이름", name);

                        startActivity(intent);
                    }
                }

            }
        });

        Button signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_checked=false;

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.layout_signin);
                dialog.setTitle("프로필 정보");

                TableLayout signin = (TableLayout) dialog.findViewById(R.id.layout_sign);
                final EditText user_id = (EditText) dialog.findViewById(R.id.name_signin);
                final EditText user_pw = (EditText) dialog.findViewById(R.id.pw_signin);

                Button check = (Button) dialog.findViewById(R.id.check);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences UserData = getSharedPreferences("UserData", MODE_PRIVATE);
                        String checkid = UserData.getString(user_id.getText().toString(), "none");
                        if(user_id.getText().toString().equals("")){
                            Toast toast = Toast.makeText(getApplicationContext(),"아이디를 입력해주세요!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else if("none".equals(checkid)){
                            Toast toast = Toast.makeText(getApplicationContext(),"사용가능한 아이디입니다.", Toast.LENGTH_SHORT);
                            toast.show();
                            id_checked = true;
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(),"이미 사용중인 아이디입니다.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

                Button complete = (Button) dialog.findViewById(R.id.complete);
                complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(id_checked==false){
                            Toast toast = Toast.makeText(getApplicationContext(),"아이디 중복체크를 해주세요!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else if("".equals(user_pw.getText().toString())){
                            Toast toast = Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else{
                            SharedPreferences UserData = getSharedPreferences("UserData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = UserData.edit();
                            editor.putString(user_id.getText().toString(), user_pw.getText().toString());
                            editor.commit();
                            Toast toast = Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.", Toast.LENGTH_SHORT);
                            toast.show();
                            dialog.dismiss();
                        }

                    }
                });

                dialog.show();
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
