package com.guudint.clickargo.admin.dto;

public class ForgotPassword {

    private String usrId;
	private String email;

    public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
