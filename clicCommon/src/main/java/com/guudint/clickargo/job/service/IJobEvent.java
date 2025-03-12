/**
 * 
 */
package com.guudint.clickargo.job.service;

/**
 * @author billy
 *
 */
public interface IJobEvent  {

	public enum JobEvent {
		CREATE("Job Create Event"), GET("Job Get Event"), UPDATE("Job Update Event"), SUBMIT("Job Submit Event"),
		REJECT("Job Reject Event"), CANCEL("Job Cancel Event"), DELETE("Job Delete Event"),
		CONFIRM("Job Confirm Event"), PAY("Job Pay Event"), PAID("Job Paid Event"), COMPLETE("Job Complete Event");

		// Attributes
		////////////
		private String desc;

		// Constructor
		//////////////
		private JobEvent(String desc) {
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
		 * @param desc the desc to set
		 */
		public void setDesc(String desc) {
			this.desc = desc;
		}

	};
}
