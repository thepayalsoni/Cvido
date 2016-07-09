package com.cvido.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class MessageData implements Serializable {


    private static final long serialVersionUID = -8224772377404129193L;

    @SerializedName("success")
    public boolean success;


    @SerializedName("message")
    public String message;


    @SerializedName("total_pages")

    public int totalPages;
    @SerializedName("page")

    public int page;
    @SerializedName("sender_avatar")

    public String senderAvatar;
    @SerializedName("sender_name")

    public String senderName;

    @SerializedName("data")
    public Data data;

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

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable {

        @SerializedName("cvs")
        public ArrayList<Cv> cvs = new ArrayList<Cv>();

        public ArrayList<Cv> getCvs() {
            return cvs;
        }

        public void setCvs(ArrayList<Cv> cvs) {
            this.cvs = cvs;
        }

        public class Cv implements Serializable {

            @SerializedName("id")

            public int id;
            @SerializedName("cv_message_id")

            public int cvMessageId;
            @SerializedName("sender_id")

            public int senderId;
            @SerializedName("reciever_id")

            public int recieverId;
            @SerializedName("message")

            public String message;
            @SerializedName("created_at")

            public String createdAt;
            @SerializedName("is_read")

            public int isRead;

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCvMessageId() {
                return cvMessageId;
            }

            public void setCvMessageId(int cvMessageId) {
                this.cvMessageId = cvMessageId;
            }

            public int getSenderId() {
                return senderId;
            }

            public void setSenderId(int senderId) {
                this.senderId = senderId;
            }

            public int getRecieverId() {
                return recieverId;
            }

            public void setRecieverId(int recieverId) {
                this.recieverId = recieverId;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }
        }

    }

}