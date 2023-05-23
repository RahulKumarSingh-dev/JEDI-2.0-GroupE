/**
 *
 */
package com.flipkart.service;

import java.sql.SQLException;
import com.flipkart.constant.NotificationTypeConstant;

/**
 * Group -E rahul.kumar ishika.gupta nishant.singh sri.vyshnavi kartik.garg
 */
public interface NotificationInterface {

	/**
	 * Method to send notification
	 * 
	 * @param type:          type of the notification to be sent
	 * @param studentId:     student to be notified
	 * @param modeOfPayment: payment mode used
	 * @return notification id for the record added in the database
	 * @throws SQLException
	 */

	public boolean sendNotification(NotificationTypeConstant type, String studentId, String referenceId)
			throws SQLException;

}