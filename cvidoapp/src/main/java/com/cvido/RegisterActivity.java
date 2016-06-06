package com.cvido;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

public class RegisterActivity extends Activity {
    ToggleButton tbJobSeekerEmployee;
    EditText txt_signup_username, txt_signup_password, txt_signup_repassword;
    ProgressDialog mProgressDialog;
    String userType = "2";
    TextView lblEmployer, lblJobSeeker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        lblJobSeeker = (TextView) findViewById(R.id.lblJobSeeker);
        lblEmployer = (TextView) findViewById(R.id.lblEmployer);

        tbJobSeekerEmployee = (ToggleButton) findViewById(R.id.tbJobSeekerEmployee);

        tbJobSeekerEmployee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lblJobSeeker.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    lblEmployer.setTextColor(getResources().getColor(R.color.app_bg));
                    userType = "2";
                } else {
                    lblJobSeeker.setTextColor(getResources().getColor(R.color.app_bg));
                    lblEmployer.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    userType = "3";
                }
            }
        });
        txt_signup_username = (EditText) findViewById(R.id.txt_signup_username);
        txt_signup_password = (EditText) findViewById(R.id.txt_signup_password);
        txt_signup_repassword = (EditText) findViewById(R.id.txt_signup_repassword);

    }


    public void onClickHandler(View view) {
        switch (view.getId()) {

            case R.id.btn_sign_in:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

            case R.id.btnRegisterUser:

                if (Util.isEditTextEmpty(txt_signup_username, txt_signup_password, txt_signup_repassword)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_empty_filelds), Toast.LENGTH_LONG).show();
                } else if (!txt_signup_password.getText().toString().equals(txt_signup_repassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_re_password), Toast.LENGTH_LONG).show();
                } else {
                    RegisterUser(txt_signup_username.getText().toString(), txt_signup_password.getText().toString());
                }
                break;

            case R.id.lblForgotPassword:
                startActivity(new Intent(this, TutorialActivity.class));
                finish();
                break;


            default:

                break;
        }
    }

    private void RegisterUser(String userName, String password) {

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Registration[email]", userName);
        hashMap.put("Registration[password_hash]", password);
        hashMap.put("Registration[confirmPassword]", password);
        hashMap.put("Registration[role_id]", userType);
        hashMap.put("Registration[check]", "1");

        Response.Listener<Register> onSuccess = new Response.Listener<Register>() {
            @Override
            public void onResponse(final Register response) {
                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                if (response.getSuccess()) {
                    CvidoApplication.getAppliation().setRegister(response);
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Util.showErrorBox(RegisterActivity.this, "Error", response.getMessage());
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
            PostRequest lRequest = new PostRequest(RegisterActivity.this, type, getString(R.string.URL_REGISTER), hashMap, onSuccess, onError, Request.Priority.HIGH);
            queue.add(lRequest);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.error_no_internet), Toast.LENGTH_LONG).show();
        }
    }


}
