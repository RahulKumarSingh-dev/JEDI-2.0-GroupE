/**
 * 
 */
package com.flipkart.dao;

import com.flipkart.bean.Payment;

/**
 * @author rahul.kumar
 *
 */
public interface PaymentDaoInterface {

	public boolean storePayment(Payment payment);

}
