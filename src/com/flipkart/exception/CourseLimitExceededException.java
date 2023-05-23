/**
 * 
 */
package com.flipkart.exception;

/**
 * Group -E rahul.kumar ishika.gupta nishant.singh sri.vyshnavi kartik.garg
 */

public class CourseLimitExceededException extends Exception {

	private int num;

	/**
	 * Constructor
	 * 
	 * @param num number of courses
	 */
	public CourseLimitExceededException(int num) {
		this.num = num;
	}

	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return "You have already registered for " + num + " courses";
	}
}
