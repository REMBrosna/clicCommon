/**
 * 
 */
package com.guudint.clickargo.task.service;

/**
 * @author billy
 *
 */
public interface ITaskEvent  {

	public enum TaskEvent {
		CREATE("Task Create Event"), GET("Task Get Event"), UPDATE("Task Update Event"), SUBMIT("Task Submit Event"),
		DELETE("Job Delete Event"),	VERIFY("Job Verify Event"), COMPLETE("Job Complete Event");

		// Attributes
		////////////
		private String desc;

		// Constructor
		//////////////
		private TaskEvent(String desc) {
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
