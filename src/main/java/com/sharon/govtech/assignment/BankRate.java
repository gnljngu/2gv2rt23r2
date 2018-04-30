package com.sharon.govtech.assignment;

/** Represents a set of Bank rates and is a subclass of FinanceRate
*
* @author  Tan Yee Ping Sharon
* @version 1.0
* @since   2018-04-30 
*/

public class BankRate extends FinanceRate {

	/** Represents the json keyword for retrieving the fixed deposit rate (3 months) */
	public static final String FIXED_DEPOSIT_3M = "banks_fixed_deposits_3m";

	/** Represents the json keyword for retrieving the fixed deposit rate (6 months) */
    public static final String FIXED_DEPOSIT_6M = "banks_fixed_deposits_6m";

    /** Represents the json keyword for retrieving the fixed deposit rate (12 months) */
    public static final String FIXED_DEPOSIT_12M = "banks_fixed_deposits_12m";

    /** Represents the json keyword for retrieving the savings deposit rate */
    public static final String SAVINGS_DEPOSIT = "banks_savings_deposits";

    /** Class constructor */
	public void BankRate() {}
}