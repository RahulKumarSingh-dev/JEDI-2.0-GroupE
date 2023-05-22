package com.flipkart.exception;

/**
 * Group -E
 * rahul.kumar
 * ishika.gupta
 * nishant.singh
 * sri.vyshnavi
 * kartik.garg
 */

public class ProfessorIdAlreadyInUseException extends Exception{
	private String ProfessorId;
	
	
	/***
	 * Setter function for ProfessorId
	 * @param userId
	 */
	
	public ProfessorIdAlreadyInUseException(String id) {
		ProfessorId = id;
	}
	
	/***
	 * Getter function for ProfessorId
	 * @param userId
	 */
	
	public String getUserId() {
		return ProfessorId;
	}
	
	
	@Override
	public String getMessage() {
		return "userId: " + ProfessorId + " is already in use.";
	}

}