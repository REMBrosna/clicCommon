package com.guudint.clickargo.admin.event;

import org.springframework.context.ApplicationEvent;

import com.vcc.camelone.ccm.dto.CoreUsr;

public class ClickargoPostUserUpdateEvent extends ApplicationEvent {

	private static final long serialVersionUID = 7788222901510444021L;

	public enum PostUserUpdateAction {
		RESET_PWD, NEW_USER
	}

	private CoreUsr usr;
	private boolean isFromManageUser;
	private String randomGeneratedPwd;
	private PostUserUpdateAction postUsrUpdateAction;

	public ClickargoPostUserUpdateEvent(Object source, CoreUsr usr, boolean isFromManageUser,
			PostUserUpdateAction postUsrUpdateAction, String randomGeneratedPwd) {
		super(source);
		this.usr = usr;
		this.isFromManageUser = isFromManageUser;
		this.postUsrUpdateAction = postUsrUpdateAction;
		this.randomGeneratedPwd = randomGeneratedPwd;
	}

	/**
	 * @return the usr
	 */
	public CoreUsr getUsr() {
		return usr;
	}

	/**
	 * @param usr the usr to set
	 */
	public void setUsr(CoreUsr usr) {
		this.usr = usr;
	}

	/**
	 * @return the isFromManageUser
	 */
	public boolean isFromManageUser() {
		return isFromManageUser;
	}

	/**
	 * @param isFromManageUser the isFromManageUser to set
	 */
	public void setFromManageUser(boolean isFromManageUser) {
		this.isFromManageUser = isFromManageUser;
	}

	/**
	 * @return the randomGeneratedPwd
	 */
	public String getRandomGeneratedPwd() {
		return randomGeneratedPwd;
	}

	/**
	 * @param randomGeneratedPwd the randomGeneratedPwd to set
	 */
	public void setRandomGeneratedPwd(String randomGeneratedPwd) {
		this.randomGeneratedPwd = randomGeneratedPwd;
	}

	/**
	 * @return the postUsrUpdateAction
	 */
	public PostUserUpdateAction getPostUsrUpdateAction() {
		return postUsrUpdateAction;
	}

	/**
	 * @param postUsrUpdateAction the postUsrUpdateAction to set
	 */
	public void setPostUsrUpdateAction(PostUserUpdateAction postUsrUpdateAction) {
		this.postUsrUpdateAction = postUsrUpdateAction;
	}

}
