package com.flipkart.constant;

/**
 * 
 * Group -E
 * rahul.kumar
 * ishika.gupta
 * nishant.singh
 * sri.vyshnavi
 * kartik.garg
 */

public enum PaymentModeConstant {
	
	ONLINE,OFFLINE,SCHOLARSHIP;
	
	/**
	 * Method to get Mode of Payment
	 * @param value
	 * @return Mode of Payment
	 */
	public static PaymentModeConstant getPaymentMode(int value)
	{
		switch(value)
		{
			case 1:
				return PaymentModeConstant.ONLINE;
			case 2:
				return PaymentModeConstant.OFFLINE;
			case 3:
				return PaymentModeConstant.SCHOLARSHIP;
			default:
				return null;
				
		}
			
	}
	
}