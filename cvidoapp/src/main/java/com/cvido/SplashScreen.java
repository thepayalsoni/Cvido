package com.cvido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
    Intent mainIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainIntent = new Intent(SplashScreen.this, RegisterActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 1500);
    }

}
