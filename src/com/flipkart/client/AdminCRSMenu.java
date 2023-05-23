/**
 * 
 */
package com.flipkart.client;

import java.sql.SQLException;


import java.util.List;
import java.util.Scanner;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Professor;
import com.flipkart.bean.RegisteredCourse;
import com.flipkart.bean.Student;
import com.flipkart.constant.GenderConstant;
import com.flipkart.constant.GradeConstant;
import com.flipkart.constant.NotificationTypeConstant;
import com.flipkart.constant.RoleConstant;
import com.flipkart.exception.CourseExistsAlreadyException;
import com.flipkart.exception.CourseNotDeletedException;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.exception.ProfessorNotAddedException;
import com.flipkart.exception.StudentNotFoundForApprovalException;
import com.flipkart.exception.UserIdAlreadyInUseException;
import com.flipkart.exception.UserNotFoundException;
import com.flipkart.service.AdminInterface;
import com.flipkart.service.AdminOperation;
import com.flipkart.service.NotificationInterface;
//import com.flipkart.service.NotificationOperation;
import com.flipkart.service.RegistrationInterface;
import com.flipkart.service.RegistrationOperation;

/**
 * Group -E
 * rahul.kumar
 * ishika.gupta
 * nishant.singh
 * sri.vyshnavi
 * kartik.garg
 */
public class AdminCRSMenu {

	AdminInterface adminOperation = AdminOperation.getInstance();
	Scanner in = new Scanner(System.in);
//	NotificationInterface notificationInterface=NotificationOperation.getInstance();
	RegistrationInterface registrationInterface = RegistrationOperation.getInstance();
	
	/**
	 * Method to Create Admin Menu
	 */
	public void createMenu(){
		
		while(CRSApplication.loggedin) {
			System.out.println("+-------------- Admin Menu -----------------+\n");
			System.out.println("+-------------------------------------------+");
			System.out.println("|              Menu Options                 |");
			System.out.println("+-------------------------------------------+");
			System.out.println("|  1. View Courses in catalog               |");
			System.out.println("|  2. Add Course to catalog                 |");
			System.out.println("|  3. Delete Course from catalog            |");
			System.out.println("|  4. Approve Student Registration          |");
			System.out.println("|  5. Approve All Student Registration      |");
			System.out.println("|  6. View Pending Approvals                |");
			System.out.println("|  7. Add Professor                         |");
			System.out.println("|  8. Assign Professor To Courses           |");
			System.out.println("|  9. Generate Report Card                  |");
			System.out.println("| 10. Logout                                |");
			System.out.println("+-------------------------------------------+");


			int choice = in.nextInt();
			
			switch(choice) {
			case 1:
				viewCoursesInCatalogue();
				break;
				
			case 2:
				addCourseToCatalogue();
				break;
				
			case 3:
				removeCourse();
				break;
				
			case 4:
			
				approveStudent();
				break;
			case 5:
				approveAllStudent();
				break;
			
			case 6:
				viewPendingAdmissions();
				break;
			
			case 7:
				addProfessor();
				break;
			
			case 8:
				assignCourseToProfessor();
				break;
				
			case 9:
				generateReportCard();
				break;
			
			case 10:
				CRSApplication.loggedin = false;
				return;

			default:
				System.out.println("------ Wrong Choice ------");
			}
		}
	}
	
	
	
	/**
	 * Method to display courses in catalogue
	 * @return List of courses in catalogue
	 */
	private List<Course> viewCoursesInCatalogue() {
	    List<Course> courseList = adminOperation.viewCourses();
	    if (courseList.size() == 0) {
	        System.out.println("Nothing present in the catalogue!");
	        return courseList;
	    }
	    System.out.println("+----------------------+----------------------+----------------------+----------------------+");
	    System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n", "COURSE CODE", "COURSE NAME", "INSTRUCTOR", "COURSE FEES");
	    System.out.println("+----------------------+----------------------+----------------------+----------------------+");
	    courseList.forEach(course -> {
    System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n", course.getCourseCode(), course.getCourseName(), course.getInstructorId(), course.getFees());
});
	    System.out.println("+----------------------+----------------------+----------------------+----------------------+");
	    return courseList;
	}

	
	/**
	 * Method to add Course to catalogue
	 * @throws CourseExistsAlreadyException 
	 */
	
	
	private void addCourseToCatalogue() {
		List<Course> courseList = viewCoursesInCatalogue();

		in.nextLine();
		System.out.println("+----------------------------------------+");
		System.out.println("|          Add Course Information        |");
		System.out.println("+----------------------------------------+");
		System.out.println("| Enter Course Code:                     |");
		String courseCode = in.nextLine();
		System.out.println("| Enter Course Name:                     |");
		String courseName = in.next();
		System.out.println("| Enter Course Fees:                     |");
		int courseFees = in.nextInt();
		System.out.println("+----------------------------------------+");

		
		Course course = new Course(courseCode, courseName,"", 10,courseFees);
		course.setCourseCode(courseCode);
		course.setCourseName(courseName);
		course.setSeats(10);
		course.setFees(courseFees);
		
		try {
		adminOperation.addCourse(course, courseList);		
		}
		catch (CourseExistsAlreadyException e) {
			System.out.println("Course already existed "+e.getMessage());
		}

	}
	
	
	/**
	 * Method to delete Course from catalogue
	 * @throws CourseNotFoundException 
	 */
	private void removeCourse() {
		List<Course> courseList = viewCoursesInCatalogue();
		System.out.println("Enter Course Code:");
		String courseCode = in.next();
		
		try {
			adminOperation.removeCourse(courseCode, courseList);
			System.out.println("\nCourse Deleted : "+courseCode+"\n");
		} catch (CourseNotFoundException e) {
			
			System.out.println(e.getMessage());
		}
		catch (CourseNotDeletedException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * Method to approve a Student using Student's ID
	 */
	private void approveStudent() {
		
		List<Student> studentList = viewPendingAdmissions();
		if(studentList.size() == 0) {
			
			
			return;
		}
		
		System.out.println("Enter Student's ID:");
		String studentUserIdApproval = in.next();
		
		
		try {
			adminOperation.approveStudent(studentUserIdApproval, studentList);
			System.out.println("\nStudent with Id : " +studentUserIdApproval+ " has been approved\n");
			//send notification from system
//			notificationInterface.sendNotification(NotificationTypeConstant.REGISTRATION, studentUserIdApproval, null,0);
	
		} catch (StudentNotFoundForApprovalException e) {
			System.out.println(e.getMessage());
		}
	
		
	}
	
	
	/*
	 * Method to approve all pending students at once 
	 */
	private void approveAllStudent() {
	    List<Student> studentList = viewPendingAdmissions();
	    if (studentList.size() == 0) {
	        return;
	    }

	    try {
	        adminOperation.approveAllStudent(studentList);
	        System.out.println("\nApproved Students:\n");
	        System.out.println("+----------------------+");
	        System.out.println("|     Student ID       |");
	        System.out.println("+----------------------+");
	        studentList.forEach(student -> {
    String studentUserIdApproval = student.getUserId();
    System.out.printf("| %-20s |\n", studentUserIdApproval);
});
	        System.out.println("+----------------------+");

	    } catch (StudentNotFoundForApprovalException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	
	/**
	 * Method to view Students who are yet to be approved
	 * @return List of Students whose admissions are pending
	 */
	private List<Student> viewPendingAdmissions() {
	    List<Student> pendingStudentsList = adminOperation.viewPendingAdmissions();
	    if (pendingStudentsList.size() == 0) {
	        System.out.println("No students pending approvals");
	        return pendingStudentsList;
	    }
	    System.out.println("+----------------------+----------------------+----------------------+");
	    System.out.printf("| %-20s | %-20s | %-20s |\n", "Student ID", "Name", "Gender");
	    System.out.println("+----------------------+----------------------+----------------------+");
	    pendingStudentsList.forEach(student -> {
    System.out.printf("| %-20s | %-20s | %-20s |\n", student.getStudentId(), student.getName(), student.getGender().toString());
});

	    System.out.println("+----------------------+----------------------+----------------------+");
	    return pendingStudentsList;
	}


	
	
	/**
	 * Method to add Professor to Database
	 */
	private void addProfessor() {
		
		System.out.println("+----------------------------------------+");
		System.out.println("| Enter Professor Id:                    |");
		String userId = in.next();
		Professor professor = new Professor(userId);
		System.out.println("| Enter Professor Name:                  |");
		String professorName = in.next();
		professor.setName(professorName);
		System.out.println("| Enter Department:                      |");
		String department = in.next();
		professor.setDepartment(department);
		System.out.println("| Enter Designation:                     |");
		String designation = in.next();
		professor.setDesignation(designation);
		System.out.println("| Enter Password:                        |");
		String password = in.next();
		professor.setPassword(password);
		System.out.println("| Enter GenderConstant:                  |");
		System.out.println("| 1: Male                                |");
		System.out.println("| 2: Female                              |");
		System.out.println("| 3: Other                               |");
		int gender = in.nextInt();
		System.out.println("| Enter Address:                         |");
		String address = in.next();
		professor.setAddress(address);
		System.out.println("+----------------------------------------+");

		
		
		if(gender==1)
			professor.setGender(GenderConstant.MALE);
		else if(gender==2)
			professor.setGender(GenderConstant.FEMALE);
		else if(gender==3)
			professor.setGender(GenderConstant.OTHER);
		
		
		professor.setRole(RoleConstant.PROFESSOR);
		
		try {
			adminOperation.addProfessor(professor);
		} catch (ProfessorNotAddedException | UserIdAlreadyInUseException e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Method to assign Course to a Professor
	 */
	
	private void assignCourseToProfessor() {
		List<Professor> professorList= adminOperation.viewProfessors();
		System.out.println("---------------------- Professor ---------------------- ");
		System.out.println(String.format("%-20s | %-20s | %-20s ", "ProfessorId", "Name", "Designation"));
		professorList.forEach(professor -> {
    System.out.println(String.format("%-20s | %-20s | %-20s ", professor.getUserId(), professor.getName(), professor.getDesignation()));
});

		
		
		System.out.println("\n\n");
		List<Course> courseList= adminOperation.viewCourses();
		System.out.println("---------------------- Course ----------------------");
		System.out.println(String.format("%-20s | %-20s | %-20s", "CourseCode", "CourseName", "ProfessorId"));
		courseList.forEach(course -> {
    System.out.println(String.format("%-20s | %-20s | %-20s", course.getCourseCode(), course.getCourseName(), course.getInstructorId()));
});

		System.out.println("+----------------------------------------+");
		System.out.println("| Enter Course Code:                     |");
		String courseCode = in.next();
		System.out.println("| Enter Professor's User Id:             |");
		String userId = in.next();
		System.out.println("+----------------------------------------+");
		
		try {
			
			adminOperation.assignCourse(courseCode, userId);
		
		}
		catch(CourseNotFoundException | UserNotFoundException  e) {
			
			System.out.println(e.getMessage());
		}
		
	}
	
	/*
	 * Method to get corresponding value of Grade
	 * return value of Grade
	 */
	private int getValue(String grade) {
		switch (grade) {	
		case "A":
			return 10;
		case "B":
			return 8;
		case "C":
			return 6;
		case "D":
			return 4;
		case "E":
			return 2;
		case "F":
			return 0;
		default:
			return 0;
		}
	}
	
	/*
	 * Method to Generate Report Card
	 */
		
	private void generateReportCard() 
	{
		
		List<Grade> grade_card=null;
		boolean isReportGenerated = true;
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("\nEnter the StudentId for report card generation : ");
		String studentId = in.next();
		
		try 
		{
			adminOperation.setGeneratedReportCardTrue(studentId);
			if(isReportGenerated) {
				grade_card = registrationInterface.viewGradeCard(studentId);
				
				boolean ok=false;

				if (grade_card.isEmpty()) {
				    System.out.println("You have not registered for any courses.");
				} else {
				    int sum = 0;
				    int count = 0;
//				    if(ok==false)
//				    {
//				    	System.out.println("+----------------------+----------------------+----------------------+");
//						System.out.println("|   COURSE CODE        |   COURSE NAME        |       GRADE          |");
//						System.out.println("+----------------------+----------------------+----------------------+");
//				    	ok=true;
//				    }
//				    else
//				    {
//					    for (Grade obj : grade_card) {
//					        System.out.printf("|   %-20s |   %-20s |   %-20s |\n", obj.getCrsCode(), obj.getCrsName(), obj.getGrade());
//					        sum += getValue(obj.getGrade());
//					        count++;
//					    }
//				    }
				    double cpi = sum * 1.0 / count;
				  System.out.println("Report Card Generated!");
				}

				System.out.println("+----------------------+----------------------+----------------------+");

			}
			else
				System.out.println("Report card has'nt been generated yet");
		} 
		catch (SQLException e) 
		{

			System.out.println(e.getMessage());
		}		
		
	}
	
}
