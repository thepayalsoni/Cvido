package com.cvido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ForgotPasswordActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
    }


    public void onClickHandler(View view) {
        switch (view.getId()) {

            case R.id.btn_sign_in:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

            case R.id.btnCancel:
                finish();
                break;


            default:
                break;
        }
    }
}
