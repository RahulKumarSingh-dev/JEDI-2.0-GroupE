/**
 * 
 */
package com.flipkart.client;

import com.flipkart.bean.*;

import com.flipkart.bean.Grade;
import com.flipkart.constant.NotificationTypeConstant;
import com.flipkart.constant.PaymentModeConstant;
import com.flipkart.dao.StudentDaoInterface;
import com.flipkart.exception.CourseLimitExceededException;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.exception.SeatNotAvailableException;
import com.flipkart.service.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Group -E rahul.kumar ishika.gupta nishant.singh sri.vyshnavi kartik.garg
 */

public class StudentCRSMenu {

	Scanner sc = new Scanner(System.in);
	RegistrationInterface registrationInterface = RegistrationOperation.getInstance();
	StudentInterface studentInterface = StudentOperation.getInstance();
	AdminInterface adminInterface = AdminOperation.getInstance();
	ProfessorInterface professorInterface = ProfessorOperation.getInstance();
//	NotificationInterface notificationInterface = NotificationOperation.getInstance();
	private boolean is_registered;

	/*
	 * Method to Create menu for student
	 * @param: StudentId
	 */
	public void create_menu(String studentId) {

		is_registered = getRegistrationStatus(studentId);

		while (CRSApplication.loggedin) {

			
			String title = "Student Menu";
			String[] menuOptions = {
			        "Register for Course",
			        "Add Course",
			        "Drop Course",
			        "View Course",
			        "View Registered Courses",
			        "View grade card",
			        "Make Payment",
			        "Logout"
			};

			System.out.println("\n------------ " + title + " ------------\n");
			System.out.println("+---------------------------------------+");
			System.out.println("|             Menu Options              |");
			System.out.println("+---------------------------------------+");

			for (int i = 0; i < menuOptions.length; i++) {
			    String option = menuOptions[i];
			    System.out.printf("| %2d. %-34s|\n", i + 1, option);
			}

			System.out.println("+---------------------------------------+");


			int choice = sc.nextInt();

			switch (choice) {

			case 1:
				registerCourses(studentId);
				break;

			case 2:
				addCourse(studentId);
				break;

			case 3:
				dropCourse(studentId);
				break;

			case 4:
				viewCourse(studentId);
				break;

			case 5:
				viewRegisteredCourse(studentId);
				break;

			case 6:
				viewGradeCard(studentId);
				break;

			case 7:
				make_payment(studentId);
				break;

			case 8:
				CRSApplication.loggedin = false;
				break;

			default:
				System.out.println("Incorrect Choice!");

			}

		}

	}
	
	/*
	 * Method to Register Courses
	 * @param: StudentId
	 */
	private void registerCourses(String studentId) {

		if (is_registered) {
			System.out.println(" Registration is already completed");
			return;
		}

		int primary = 0;
		int secondary = 0;
		while ((primary + secondary) < 6) {

			try {
				List<Course> courseList = viewCourse(studentId);

				if (courseList == null)
					return;

				System.out.println("Enter Course Code : " + (primary + secondary + 1));
				String courseCode = sc.next();

				System.out.println("do you want to consider as primary  (1) or secondary (2) ?");
				int courseType = sc.nextInt();
				if (courseType == 1) {
					if (primary < 4) {
						primary += 1;
					} else {
						System.out.println(
								"You exceeded limit on taking primary courses\nPlease select secondary courses only");
						continue;
					}

				}
				if (courseType == 2) {
					if (secondary < 4) {
						secondary += 1;
					} else {
						System.out.println(
								"You exceeded limit on taking secondary courses\nPlease select primary courses only");
						continue;
					}

				}

				if (registrationInterface.addCourse(courseCode, studentId, courseList)) {
					System.out.println("Course " + courseCode + " registered sucessfully.");

				} else {
					System.out.println(" You have already registered for Course : " + courseCode);
				}
			} catch (CourseNotFoundException | CourseLimitExceededException | SQLException e) {
				System.out.println(e.getMessage());
			} catch (SeatNotAvailableException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		System.out.println("\n-----------------------------------------------------");
		System.out.println("Registration Successful");
		System.out.println("-------------------------------------------------------\n");

		try {
			registrationInterface.setRegistrationStatus(studentId);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		is_registered = true;

//    try 
//    {
//		registrationInterface.setRegistrationStatus(studentId);
//	} 
//    catch (SQLException e) 
//    {
//    	System.out.println(e.getMessage());
//	}
	}
	
	/*
	 * Method to add course according StudentId
	 * @param: StudentId
	 */
	private void addCourse(String studentId) {
		if (is_registered) {
			List<Course> availableCourseList = viewCourse(studentId);

			if (availableCourseList == null)
				return;

			try {
				System.out.println("Enter Course Code : ");
				String courseCode = sc.next();
				if (registrationInterface.addCourse(courseCode, studentId, availableCourseList)) {
					System.out.println(" You have successfully registered for Course : " + courseCode);
				} else {
					System.out.println(" You have already registered for Course : " + courseCode);
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} catch (CourseNotFoundException e) {
				System.out.println(e.getMessage());

			} catch (CourseLimitExceededException e) {
				System.out.println(e.getMessage());

			} catch (SeatNotAvailableException e) {
				System.out.println(e.getMessage());

			}
		} else {
			System.out.println("Please complete registration");
		}

	}

	/**
	 * Method to check if student is already registered
	 * 
	 * @param studentId
	 * @return Registration Status
	 */
	private boolean getRegistrationStatus(String studentId) {
		try {
			return registrationInterface.getRegistrationStatus(studentId);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Drop Course
	 * @param studentId
	 */
	private void dropCourse(String studentId) {
		if (is_registered) {
			List<Course> registeredCourseList = viewRegisteredCourse(studentId);

			if (registeredCourseList == null)
				return;

			System.out.println("Enter the Course Code : ");
			String courseCode = sc.next();

			try {
				registrationInterface.dropCourse(courseCode, studentId, registeredCourseList);
				System.out.println("You have successfully dropped Course : " + courseCode);

			} catch (CourseNotFoundException e) {
				System.out.println("You have not registered for course : " + e.getCourseCode());
			} catch (SQLException e) {

				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Please complete registration");
		}
	}

	/**
	 * View all available Courses
	 * 
	 * @param studentId
	 * @return List of available Courses
	 */
	private List<Course> viewCourse(String studentId) {
	    List<Course> course_available = null;

	    try {
	        course_available = registrationInterface.viewCourses(studentId);
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }

	    if (course_available.isEmpty()) {
	        System.out.println("NO COURSE AVAILABLE");
	        return null;
	    }

	    System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+");
	    System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s |\n", "COURSE CODE", "COURSE NAME", "INSTRUCTOR", "SEATS", "FEES");
	    System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+");

	    for (Course obj : course_available) {
	        System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s |\n", obj.getCourseCode(), obj.getCourseName(), obj.getInstructorId(), obj.getSeats(), obj.getFees());
	    }

	    System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+");

	    return course_available;
	}


	/**
	 * View Registered Courses
	 * 
	 * @param studentId
	 * @return List of Registered Courses
	 */
	private List<Course> viewRegisteredCourse(String studentId) {
	    List<Course> course_registered = null;
	    try {
	        course_registered = registrationInterface.viewRegisteredCourses(studentId);
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }

	    if (course_registered.isEmpty()) {
	        System.out.println("You haven't registered for any course");
	        return null;
	    }

	    System.out.println("+----------------------+----------------------+----------------------+");
	    System.out.printf("| %-20s | %-20s | %-20s |\n", "COURSE CODE", "COURSE NAME", "INSTRUCTOR");
	    System.out.println("+----------------------+----------------------+----------------------+");

	    for (Course obj : course_registered) {
	        System.out.printf("| %-20s | %-20s | %-20s |\n", obj.getCourseCode(), obj.getCourseName(), obj.getInstructorId());
	    }

	    System.out.println("+----------------------+----------------------+----------------------+");

	    return course_registered;
	}

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

	/**
	 * View grade card for particular student
	 * @param studentId
	 */
	private void viewGradeCard(String studentId) {
	    List<Grade> grade_card = null;
	    boolean isReportGenerated = false;
	   double sum=0,count=0;

	    try {
	        isReportGenerated = registrationInterface.isReportGenerated(studentId);
	        if (isReportGenerated) {
	            grade_card = registrationInterface.viewGradeCard(studentId);
	            System.out.println("+----------------------+----------------------+----------------------+");
	            System.out.printf("| %-20s | %-20s | %-20s |\n", "COURSE CODE", "COURSE NAME", "GRADE");
	            System.out.println("+----------------------+----------------------+----------------------+");

	            if (grade_card.isEmpty()) {
	                System.out.println("You haven't registered for any course");
	                return;
	            }
	            for (Grade obj : grade_card) {
	            	System.out.printf("| %-20s | %-20s | %-20s |\n", obj.getCrsCode(), obj.getCrsName(), obj.getGrade());
			        sum += getValue(obj.getGrade());
			        count++;
			    }
	            double cpi = sum * 1.0 / count;
	            System.out.println("CPI = "+cpi);

//	            for (Grade obj : grade_card) {
//	                System.out.printf("| %-20s | %-20s | %-20s |\n", obj.getCrsCode(), obj.getCrsName(), obj.getGrade());
//	            }
	        } else {
	            System.out.println("Report card not yet generated");
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    System.out.println("+----------------------+----------------------+----------------------+");
	}
	
	/*
	 * Method to make payment
	 * @param: studentId
	 */
	private void make_payment(String studentId) {

		double fee = studentInterface.calculateFees(studentId);

		boolean isreg = false;
		boolean ispaid = false;
		try {
			isreg = registrationInterface.getRegistrationStatus(studentId);
			ispaid = registrationInterface.getPaymentStatus(studentId);
		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}

		if (!isreg) {
			System.out.println("You have not registered yet");
		} else if (isreg && !ispaid) {

			System.out.println("+----------------------------------------+");
			System.out.println("|        Fee Payment Information         |");
			System.out.println("+----------------------------------------+");
			System.out.println("| Your total fee = " + fee + "           |");
			System.out.println("|                                        |");
			System.out.println("| Want to continue Fee Payment (y/n)?    |");
			System.out.println("+----------------------------------------+");

			String ch = sc.next();
			if (ch.equals("y")) {
				System.out.println("Select Mode of Payment:");

				int index = 1;
				for (PaymentModeConstant mode : PaymentModeConstant.values()) {
					System.out.println(index + " " + mode);
					index = index + 1;
				}

				PaymentModeConstant mode = PaymentModeConstant.getPaymentMode(Integer.parseInt(sc.next()));
				String paymentMode = "";
				if (mode == null)
					System.out.println("Invalid Input");
				else {
					try {
						switch (mode) {
						case ONLINE:
							paymentMode = "ONLINE";
							System.out.println("+----------------------------------------+");
							System.out.println("|         Online Payment Options         |");
							System.out.println("+----------------------------------------+");
							System.out.println("| Press 1 for Card                       |");
							System.out.println("| Press 2 for NetBanking                 |");
							System.out.println("| Press 3 to quit                        |");
							System.out.println("+----------------------------------------+");
							String onlineMode = sc.next();

							switch (onlineMode) {
							case "1":
								System.out.println("+----------------------------------------+");
								System.out.println("| Please Enter Card Number               |");
								sc.next();
								System.out.println("| Please Enter Date of Expiry            |");
								sc.next();
								System.out.println("| Please Enter CVV                       |");
								sc.next();
								System.out.println("|                                        |");
								System.out.println("| Fees Paid.                             |");
								System.out.println("+----------------------------------------+");
								break;
							case "2":
								System.out.println("+----------------------------------------+");
								System.out.println("| Please Enter User Name                 |");
								sc.next();
								System.out.println("| Please Enter Password                  |");
								sc.next();
								System.out.println("|                                        |");
								System.out.println("| Fees Paid.                             |");
								System.out.println("+----------------------------------------+");
								break;
							default:
								System.out.println("Invalid Argument");
								return;
							}

							break;
						case OFFLINE:
							paymentMode = "OFFLINE";
							System.out.println("+----------------------------------------+");
							System.out.println("|         Offline Payment Options        |");
							System.out.println("+----------------------------------------+");
							System.out.println("| Press 1 for Cash                       |");
							System.out.println("| Press 2 for Cheque                     |");
							System.out.println("| Press 3 to quit                        |");
							System.out.println("+----------------------------------------+");
							String offlineMode = sc.next();
							
							switch (offlineMode) {
							case "1":
								System.out.println("Please Pay your fees at Fee Counter");
								break;
							case "2":
								System.out.println("+-----------------------------------------------+");
								System.out.println("|           Cheque Information                  |");
								System.out.println("+-----------------------------------------------+");
								System.out.println("| Please Enter Account Number                   |");
								sc.next();
								System.out.println("| Please Enter IFSC Code                        |");
								sc.next();
								System.out.println("|                                               |");
								System.out.println("| Please Submit your Cheque at the Fee Counter  |");
								System.out.println("+-----------------------------------------------+");
								break;
							default:
								System.out.println("Invalid Argument");
								return;
							}

							break;
						case SCHOLARSHIP:
							paymentMode = "SCHOLARSHIP";
							System.out.println("Fees Paid.");
							break;
						default:
							System.out.println("Invalid Argument");
							return;
						}

						Payment payment = new Payment();

						payment.setAmount(fee);
						payment.setPaymentMode(paymentMode);
						payment.setStudentId(studentId);
						
						PaymentServiceInterface paymentOperation=new PaymentOperationService();
						
						paymentOperation.processPayment(payment);

//						notificationInterface.sendNotification(NotificationTypeConstant.PAYED, studentId, mode, fee);
						System.out.println("Payment Successful by StudentId :" + studentId);
						registrationInterface.setPaymentStatus(studentId);
					} catch (Exception e) {

						System.out.println(e.getMessage());
					}
				}

			}

		}

		else {
			System.out.println("You have already paid the fees");
		}

	}
}