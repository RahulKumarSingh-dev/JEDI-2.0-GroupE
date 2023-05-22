package com.flipkart.exception;

/**
 * Exception to check if student is not registered 
 * Group -E
 * rahul.kumar
 * ishika.gupta
 * nishant.singh
 * sri.vyshnavi
 * kartik.garg
 */

public class StudentNotRegisteredException extends Exception{
	 private String studentName;
	 
	 public StudentNotRegisteredException(String studentName)
	 {
		 this.studentName=studentName;
	 }
	 
	 /**
	  * getter function for studentName
	  * @return
	  */
	 public String getStudentName()
	 {
		 return studentName;
	 }
	 
}