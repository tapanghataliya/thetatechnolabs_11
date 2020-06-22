package com.apphelp.help.thetatechnolabs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.apphelp.help.thetatechnolabs.R;
import com.apphelp.help.thetatechnolabs.controller.Global;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 3000;
    boolean isLogin;
    SharedPreferences sharedPreferences;
    String S_EMAIL = "test@gmail.com";
    String S_PASSWORD = "test123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        Initilization();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void Initilization() {

        sharedPreferences = getSharedPreferences(Global.THETA_TECHNOLABS, MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean(Global.CURRENT_USER_LOGIN, false);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Global.EMAIL, S_EMAIL);
        editor.putString(Global.PASSWORD, S_PASSWORD);
        editor.apply();

        if (isLogin == false){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MainActivity.this,
                            LoginActivity.class);
                    startActivity(i);
                    finish();

                }
            }, SPLASH_SCREEN_TIME_OUT);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MainActivity.this,
                            HomeActivity.class);
                    startActivity(i);
                    finish();

                }
            }, SPLASH_SCREEN_TIME_OUT);
        }

    }

}
