package com.cvido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void onClickHandler(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;

            case R.id.btnLoginUser:
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                break;

            case R.id.lblForgotPassword:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                //finish();
                break;


            default:
                break;
        }
    }
}
