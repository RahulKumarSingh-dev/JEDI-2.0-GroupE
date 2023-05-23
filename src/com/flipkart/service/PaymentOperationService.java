/**

 * 
 */
package com.flipkart.service;

import java.sql.SQLException;
import java.util.*;

import com.flipkart.bean.Payment;
import com.flipkart.dao.*;
import com.flipkart.constant.NotificationTypeConstant;

/**
 * @author rahul.kumarsingh
 *
 */
public class PaymentOperationService implements PaymentServiceInterface {

	static int paymentId = 0;

	NotificationInterface notificationDaoInterface = NotificationOperation.getInstance();

	public void processPayment(Payment payment) {

		paymentId = paymentId + 1;
		payment.setReferenceId("R" + Integer.toString(paymentId));

		PaymentDaoInterface paymentDao = new PaymentDaoOperation();
		paymentDao.storePayment(payment);

		try {
			notificationDaoInterface.sendNotification(NotificationTypeConstant.PAYED, payment.getStudentId(),
					payment.getReferenceId());
		} catch (SQLException e) {
			System.out.println("Can not store Notification");
//			e.printStackTrace();
		}

	}

}
