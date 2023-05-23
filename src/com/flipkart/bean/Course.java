
package com.flipkart.bean;

//Group -E

//  rahul.kumar
//  ishika.gupta
//  nishant.singh
//  sri.vyshnavi
//  kartik.garg

public class Course {
	private String crsCode;
	private String crsName;
	private String instructorId;
	private int seats = 10;
	private int fees;

	public Course() {

	}

	public Course(String crsCode, String crsName, String professorId, int seats, int fees) {
		this.crsCode = crsCode;
		this.crsName = crsName;
		this.instructorId = professorId;
		this.seats = seats;
		this.fees = fees;
	}

	/***
	 * Method to get Course Code
	 * 
	 * @return Course Code
	 * 
	 */
	public String getCourseCode() {
		return crsCode;
	}

	/***
	 * @return the fees
	 */
	public int getFees() {
		return fees;
	}

	/***
	 * @param fees the fees to set
	 */
	public void setFees(int fees) {
		this.fees = fees;
	}

	/**
	 * Method to set Course Code
	 * 
	 * @param courseCode
	 */

	public void setCourseCode(String courseCode) {
		this.crsCode = courseCode;
	}

	/***
	 * Method to get Course Name
	 * 
	 * @return Course Name
	 */
	public String getCourseName() {
		return crsName;
	}

	public void setCourseName(String courseName) {
		this.crsName = courseName;
	}

	/***
	 * Method to get available seats
	 * 
	 * @return Seats available
	 */

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getInstructorId() {
		return instructorId;
	}

//	 * Method to set Instructor Id of professor teaching the course
//	 * 
//	 * @param instructorId
//	 */
	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
	}

}
