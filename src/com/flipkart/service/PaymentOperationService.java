/**

 * 
 */
package com.flipkart.service;
import java.util.*;


import com.flipkart.bean.Payment;
import com.flipkart.dao.*;
/**
 * @author rahul.kumarsingh
 *
 */
public class PaymentOperationService implements PaymentServiceInterface{
	
	static int paymentId = 0;
	public void processPayment(Payment payment) {

        paymentId = paymentId + 1;
        payment.setReferenceId("R" + Integer.toString(paymentId) );

        PaymentDaoInterface paymentDao = new PaymentDaoOperation();
        paymentDao.storePayment(payment);
       
        
    }

    
	
	
	
}
