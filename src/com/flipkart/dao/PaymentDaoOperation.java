package com.flipkart.dao;

import java.sql.*;

import com.flipkart.bean.*;
import com.flipkart.constant.*;
import com.flipkart.utils.DBUtils;

public class PaymentDaoOperation implements PaymentDaoInterface {

	// Step 1
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/CRSDatabase";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "rahulkumar";

	public boolean storePayment(Payment payment) {

		try {
			
			Connection con = DBUtils.getConnection();

			PreparedStatement stmt = con.prepareStatement(SQLQueriesConstant.ADD_PAYMENT);

			stmt.setString(1, payment.getStudentId());
			stmt.setString(2, payment.getPaymentMode());
			stmt.setString(3, payment.getReferenceId());
			stmt.setDouble(4, payment.getAmount());

			stmt.executeUpdate();
			stmt.close();
			con.close();

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			return false;
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
