package com.cvido.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Register implements Serializable {

    private static final long serialVersionUID = 6860437319408392664L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    public boolean getSuccess() {
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

    public class Data implements Serializable {

        @SerializedName("id")
        private int id;

        @SerializedName("role_id")
        private int roleId;

        @SerializedName("username")
        private String username;

        @SerializedName("email")
        private String email;

        @SerializedName("avatar")
        private String avatar;

        @SerializedName("auth_key")
        private String authKey;

        @SerializedName("password_hash")
        private String passwordHash;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("user_status_id")
        private int userStatusId;

        @SerializedName("role_name")
        private String roleName;

        @SerializedName("avatar_thumb")
        private String avatarThumb;

        @SerializedName("status")
        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAuthKey() {
            return authKey;
        }

        public void setAuthKey(String authKey) {
            this.authKey = authKey;
        }

        public String getPasswordHash() {
            return passwordHash;
        }

        public void setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getUserStatusId() {
            return userStatusId;
        }

        public void setUserStatusId(int userStatusId) {
            this.userStatusId = userStatusId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getAvatarThumb() {
            return avatarThumb;
        }

        public void setAvatarThumb(String avatarThumb) {
            this.avatarThumb = avatarThumb;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
