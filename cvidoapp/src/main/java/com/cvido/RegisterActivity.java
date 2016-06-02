package com.cvido;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }


    public void onClickHandler(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }
}
