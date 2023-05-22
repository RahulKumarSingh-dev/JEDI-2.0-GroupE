
package com.flipkart.service;

import java.util.UUID;

import com.flipkart.constant.NotificationTypeConstant;
import com.flipkart.constant.PaymentModeConstant;
import com.flipkart.dao.NotificationDaoInterface;
import com.flipkart.dao.NotificationDaoOperation;


public class NotificationOperation implements NotificationInterface{
	
	private static volatile NotificationOperation instance=null;
	NotificationDaoInterface notificationDaoInterface=NotificationDaoOperation.getInstance();
	private NotificationOperation() {}

	public static NotificationOperation getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(NotificationOperation.class){
				instance=new NotificationOperation();
			}
		}
		return instance;
	}
	

	
	@Override
	public int sendNotification(NotificationTypeConstant type, int studentId,PaymentModeConstant modeOfPayment,double amount) {
		return 0;
		
	}

	@Override
	public boolean sendNotification(NotificationTypeConstant type, String studentId, PaymentModeConstant modeOfPayment, double amount) {
		// TODO Auto-generated method stub
//		˙
		return true;
	}

	@Override
	public UUID getReferenceId(int notificationId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}