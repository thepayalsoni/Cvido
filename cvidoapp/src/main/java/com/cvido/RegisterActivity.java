package com.cvido;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

    // public static final byte[] IV = hexStringToByteArray("00000000000000000000000000000000");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        lblJobSeeker = (TextView) findViewById(R.id.lblJobSeeker);
        lblEmployer = (TextView) findViewById(R.id.lblEmployer);

        //////////////////////////////////////////////////////////////////

       /* try{
            byte[] key = hexStringToByteArray("6900C66B5C13A7579766926ED31B2374BFA80E2FBEEF7860B322159A280378F0");
            System.out.println(key);
            System.out.println(IV);
            String output = decryptStringAES("mo9mbeVkp3HY2o+n/Jt/5MyJcSlQZSryUWVeauNizAimiTzZGbJ/DxtiwqXur5XTMfhNivgtxiXzNpS9dZMUoc6YZbmZuYz5SULuMj7AW2dXwqFLUmBJTRa39f49rjUf",
                    key);
            System.out.println("This is the output");
            System.out.println(key);
            System.out.println(output);
        }
        catch (Exception e){
            System.out.println(e);
        }*/


        //////////////////////////////////////////////////////////////////////

        tbJobSeekerEmployee = (ToggleButton) findViewById(R.id.tbJobSeekerEmployee);

        tbJobSeekerEmployee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lblJobSeeker.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
                    lblEmployer.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.app_bg));
                    userType = "2";
                } else {
                    lblJobSeeker.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.app_bg));
                    lblEmployer.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
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




   /* public static String decryptStringAES (String input, byte[] key) throws Exception { byte[] inputBytes = org.apache.commons.codec.binary.Base64.decodeBase64(input.getBytes());
        Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); decryptCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(IV));
        byte[] decrypt = decryptCipher.doFinal(inputBytes);
        return  new String(decrypt);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }*/

}
