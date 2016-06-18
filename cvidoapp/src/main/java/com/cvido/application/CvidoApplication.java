package com.cvido.application;

import android.app.Activity;
import android.app.Application;

import com.akslibrary.utility.ShareData;
import com.cvido.model.Register;

public class CvidoApplication extends Application {
    public Register loginData;
    ShareData mShareData;

    public Register getRegister() {
        try {
            String userData = null;
            if (loginData == null
                    && (userData = getShavedData().getDataFromSharedPref(AppUtill.KEY_LOGIN_USER)) != null) {
                loginData = (Register) Serializer.deserialize(userData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginData;
    }

    public void setRegister(Register loginData) {
        this.loginData = loginData;
        initRegister(loginData, false);
    }


    private static CvidoApplication opxApplication = null;

    public CvidoApplication() {
    }

    public void onCreate() {
        super.onCreate();
        setAppliation(this);
        MyImageLoader.init(this);
        MyVolley.init(this);
    }

    public static CvidoApplication getAppliation() {
        return opxApplication;
    }

    private static void setAppliation(CvidoApplication context) {
        opxApplication = context;
    }


    public ShareData getShavedData() {
        if (this.mShareData == null) {
            this.mShareData = new ShareData(this);
        }

        return this.mShareData;
    }

    public void initRegister(Register currentUser, boolean forceWrite) {

        if (getShavedData().getDataFromSharedPref(AppUtill.KEY_LOGIN_USER) == null || getShavedData().getDataFromSharedPref(AppUtill.KEY_LOGIN_USER).equals("") || forceWrite) {
            getShavedData().clearSharedPref(AppUtill.KEY_LOGIN_USER);
            getShavedData().addToSharedPref(AppUtill.KEY_LOGIN_USER, Serializer.serialize(currentUser));
        }
        this.loginData = currentUser;
    }

    public void logout(Activity act) {
        initRegister(null, true);
    }

}
