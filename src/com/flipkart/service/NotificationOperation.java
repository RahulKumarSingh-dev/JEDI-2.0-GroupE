
package com.flipkart.service;

import java.sql.SQLException;
import com.flipkart.constant.NotificationTypeConstant;
import com.flipkart.dao.NotificationDaoInterface;
import com.flipkart.dao.NotificationDaoOperation;

public class NotificationOperation implements NotificationInterface {

	private static volatile NotificationOperation instance = null;
	NotificationDaoInterface notificationDaoInterface = NotificationDaoOperation.getInstance();

	private NotificationOperation() {
	}

	public static NotificationOperation getInstance() {
		if (instance == null) {
			// This is a synchronized block, when multiple threads will access this instance
			synchronized (NotificationOperation.class) {
				instance = new NotificationOperation();
			}
		}
		return instance;
	}

	@Override
	public boolean sendNotification(NotificationTypeConstant type, String studentId, String referenceId)
			throws SQLException {
		// TODO Auto-generated method stub
//	
		return notificationDaoInterface.sendNotification(type, studentId, referenceId);

	}

}