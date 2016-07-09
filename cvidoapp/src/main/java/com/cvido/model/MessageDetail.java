package com.cvido.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageDetail implements Serializable {

    private static final long serialVersionUID = 1617508324687840047L;

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

    public ArrayList<Datum> data = new ArrayList<Datum>();

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

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public class Datum implements Serializable {

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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }
    }

}
