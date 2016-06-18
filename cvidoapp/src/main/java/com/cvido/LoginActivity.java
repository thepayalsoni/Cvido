package com.cvido;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.akslibrary.request.PostRequest;
import com.akslibrary.utility.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvido.application.CvidoApplication;
import com.cvido.application.MyVolley;
import com.cvido.model.Register;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LoginActivity extends Activity {
    EditText txt_signup_username, txt_signup_password;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_signup_username = (EditText) findViewById(R.id.txt_signup_username);
        txt_signup_password = (EditText) findViewById(R.id.txt_signup_password);
    }


    public void onClickHandler(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;

            case R.id.btnLoginUser:

                if (Util.isEditTextEmpty(txt_signup_username, txt_signup_password)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_empty_filelds), Toast.LENGTH_LONG).show();
                } else {
                    RegisterUser(txt_signup_username.getText().toString(), txt_signup_password.getText().toString());
                }
               /* startActivity(new Intent(this, HomeActivity.class));
                finish();*/
                break;

            case R.id.lblForgotPassword:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                //finish();
                break;


            default:
                break;
        }
    }

    private void RegisterUser(String userName, String password) {

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("User[email]", userName);
        hashMap.put("User[password]", password);

        Response.Listener<Register> onSuccess = new Response.Listener<Register>() {
            @Override
            public void onResponse(final Register response) {
                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                if (response.getSuccess()) {
                    CvidoApplication.getAppliation().setRegister(response);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Util.showErrorBox(LoginActivity.this, "Error", response.getMessage());
                }
            }
        };

        Response.ErrorListener onError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();


            }
        };

        if (Util.hasInternetAccess(this)) {

            //  showProgressDialog(null, getString(R.string.str_FetchingData), false);
            try {
                this.mProgressDialog = ProgressDialog.show(this, null, "Loading...", true, false);
            } catch (Exception var5) {
                var5.printStackTrace();
            }

            RequestQueue queue = MyVolley.getRequestQueue();
            Type type = new TypeToken<Register>() {
            }.getType();
            PostRequest lRequest = new PostRequest(LoginActivity.this, type, getString(R.string.URL_LOGIN), hashMap, onSuccess, onError, Request.Priority.HIGH);
            queue.add(lRequest);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.error_no_internet), Toast.LENGTH_LONG).show();
        }
    }


}
