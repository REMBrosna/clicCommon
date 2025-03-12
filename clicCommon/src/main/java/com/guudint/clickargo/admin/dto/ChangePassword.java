package com.guudint.clickargo.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePassword {

    @JsonProperty
    private boolean isFromManageUser = false;
    private boolean forceChangePwd = false;
    private String userId;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private String token;

    public boolean isFromManageUser() {
        return isFromManageUser;
    }

    public void setFromManageUser(boolean fromManageUser) {
        isFromManageUser = fromManageUser;
    }

    public boolean isForceChangePwd() {
		return forceChangePwd;
	}

	public void setForceChangePwd(boolean forceChangePwd) {
		this.forceChangePwd = forceChangePwd;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
