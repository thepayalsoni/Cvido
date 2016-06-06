package com.cvido;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class TutorialActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        ImageView img = (ImageView) findViewById(R.id.imgTutorial);
        ((AnimationDrawable) img.getBackground()).start();
    }


}
