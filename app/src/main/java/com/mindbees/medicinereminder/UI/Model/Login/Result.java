package com.mindbees.medicinereminder.UI.Model.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 10-02-2017.
 */

public class Result implements Serializable {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("user_password")
    @Expose
    private String userPassword;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("registered_date")
    @Expose
    private String registeredDate;
    @SerializedName("user_status")
    @Expose
    private String userStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
