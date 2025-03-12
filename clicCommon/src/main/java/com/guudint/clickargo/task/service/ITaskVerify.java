package com.guudint.clickargo.task.service;

public class ITaskVerify {

	public enum TaskVerifyType {
		DOCUMENT("Job Verified Document"), PAYMENT("Job Get Event");

		// Attributes
		////////////
		private String desc;

		// Constructor
		//////////////
		private TaskVerifyType(String desc) {
			this.setDesc(desc);
		}

		// Properties
		/////////////
		/**
		 * @return the desc
		 */
		public String getDesc() {
			return desc;
		}

		/**
		 * @param desc the desc  123         to set
		 */
		public void setDesc(String desc) {
			this.desc = desc;
		}

	};	
}
