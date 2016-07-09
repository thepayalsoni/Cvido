package com.cvido.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SendMessage implements Serializable {
    private static final long serialVersionUID = -4682000419910499899L;

    @SerializedName("success")
    public boolean success;

    @SerializedName("message")
    public String message;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
