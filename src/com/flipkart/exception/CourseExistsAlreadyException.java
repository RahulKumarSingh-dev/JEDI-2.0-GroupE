/**
 * 
 */
package com.flipkart.exception;

/**
 * Group -E
 * rahul.kumar
 * ishika.gupta
 * nishant.singh
 * sri.vyshnavi
 * kartik.garg
 */
 
public class CourseExistsAlreadyException extends Exception{
	
	private String courseCode;
	
	/***
	 * Constructor
	 * @param courseCode
	 */
	public CourseExistsAlreadyException(String courseCode) {
		this.courseCode = courseCode;
	}
	

	/**
	 * Getter method
	 * @return course code
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return "Course: " + courseCode + " already exists in catalog.";
	}
}
