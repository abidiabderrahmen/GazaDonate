package com.example.abdou.gazadonate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_DURATION = 2000;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    public static void setLoginState(Context context, boolean isLoggedIn) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public static boolean getLoginState(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(IS_LOGGED_IN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                boolean isLoggedIn = getLoginState(splash.this);

                Intent redirectIntent;
                if (isLoggedIn) {
                    redirectIntent = new Intent(splash.this, news.class);
                } else {
                    redirectIntent = new Intent(splash.this, MainActivity.class);
                }
                startActivity(redirectIntent);
                finish();
            }
        }, SPLASH_DISPLAY_DURATION);
    }
}
