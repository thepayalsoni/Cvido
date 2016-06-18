package com.cvido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cvido.application.CvidoApplication;

public class SplashScreen extends Activity {
    Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CvidoApplication.getAppliation().getRegister() != null) {
                    mainIntent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    mainIntent = new Intent(SplashScreen.this, RegisterActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, 1500);
    }

}
