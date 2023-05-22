
package com.flipkart.bean;


public class Grade 
{
	private String crsCode;
	private String crsName;
	private String grade;
	
	public Grade(String courseCode, String courseName, String grade) {
		this.crsCode = courseCode;
		this.crsName = courseName;
		this.grade = grade;
	}


 
	public String getCrsCode() {
		return crsCode;
	}
	
	

	
	public void setCrsCode(String crsCode) {
		this.crsCode = crsCode;
	}
	
	

	
	public String getCrsName() {
		return crsName;
	}
	
	

	
	public void setCrsName(String crsName) {
		this.crsName = crsName;
	}
	
	
	
	public String getGrade() {
		return grade;
	}
	
	
	
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
	
}
