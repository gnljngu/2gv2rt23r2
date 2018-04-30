package com.sharon.govtech.assignment;

/** Represents a set of finance rates 
*
* @author  Tan Yee Ping Sharon
* @version 1.0
* @since   2018-04-30 
*/

public class FinanceRate
{
	/** Represents the json keyword for retrieving the end_of_month date value */
	public static final String MONTH = "end_of_month";

	/** Represents the fixed deposit rate (3 months) */
	protected Rate fixed_deposit_3m;

	/** Represents the fixed deposit rate (6 months) */
	protected Rate fixed_deposit_6m;

	/** Represents the fixed deposit rate (12 months) */
	protected Rate fixed_deposit_12m;

	/** Represents the savings deposit rate*/
	protected Rate saving_deposit;

	/** Class constructor */
	public FinanceRate(){}
	
	/** Sets the fixed deposit rate (3 months).
	 * @param rate A float representing the value of the fixed deposit rate (3 months).
	*/
	public void setFixedDeposit3M(float rate) {
		fixed_deposit_3m = new Rate(rate);
	}

	/** Sets the fixed deposit rate (6 months).
	 * @param rate A float representing the value of the fixed deposit rate (6 months).
	*/
	public void setFixedDeposit6M(float rate) {
		fixed_deposit_6m = new Rate(rate);
	}

	/** Sets the fixed deposit rate (12 months).
	 * @param rate A float representing the value of the fixed deposit rate (12 months).
	*/
	public void setFixedDeposit12M(float rate) {
		fixed_deposit_12m = new Rate(rate);
	}

	/** Sets the savings deposit rate.
	 * @param rate A float representing the value of the savings deposit rate.
	*/
	public void setSavingDeposit(float rate) {
		saving_deposit = new Rate(rate);
	}

	/** Gets fixed deposit rate (3 months).
	 * @return A float representing the value of the fixed deposit rate (3 months).
	*/
	public float getFixedDeposit3M() {
		return fixed_deposit_3m.getRate();
	}
		
	/** Gets fixed deposit rate (6 months).
	 * @return A float representing the value of the fixed deposit rate (6 months).
	*/	
	public float getFixedDeposit6M() {
		return fixed_deposit_6m.getRate();
	}
	
	/** Gets fixed deposit rate (12 months).
	 * @return A float representing the value of the fixed deposit rate (12 months).
	*/
	public float getFixedDeposit12M() {
		return fixed_deposit_12m.getRate();
	}

	/** Gets savings deposit rate.
	 * @return A float representing the value of the savings deposit rate.
	*/
	public float getSavingDeposit() {
		return saving_deposit.getRate();
	}
}