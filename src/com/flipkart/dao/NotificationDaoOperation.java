
package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.flipkart.constant.NotificationTypeConstant;
import com.flipkart.constant.SQLQueriesConstant;
import com.flipkart.utils.DBUtils;

public class NotificationDaoOperation implements NotificationDaoInterface {

	private static volatile NotificationDaoOperation instance = null;

	public static NotificationDaoOperation getInstance() {
		if (instance == null) {
			// This is a synchronized block, when multiple threads will access this instance
			synchronized (NotificationDaoOperation.class) {
				instance = new NotificationDaoOperation();
			}
		}
		return instance;
	}

	@Override
	public boolean sendNotification(NotificationTypeConstant type, String studentId, String referenceId)
			throws SQLException {

		Connection connection = DBUtils.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(SQLQueriesConstant.INSERT_NOTIFICATION);
			ps.setString(1, studentId);
			ps.setString(2, type.toString());
			ps.setString(3, referenceId);

			ps.executeUpdate();

			switch (type) {
			case REGISTRATION:
				System.out.println("Registration successfull. Administration will verify the details and approve it!");
				break;
			case APPROVED:
				System.out.println("Student with id " + studentId + " has been approved!");
				break;
			case PAYED:
				System.out.println("Student with id " + studentId + " fee has been paid");
			}

		} catch (SQLException ex) {
			throw ex;
		}
		return true;
	}

}
