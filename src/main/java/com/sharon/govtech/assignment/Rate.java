package com.sharon.govtech.assignment;

/** Represents a numerical rate
*
* @author  Tan Yee Ping Sharon
* @version 1.0
* @since   2018-04-30 
*/

public class Rate {

	/** Represents the numerical value */
	private float rate;
	
	/** Class constructor */
	public Rate(){}

	/** Creates a rate with the specified value.
 	* @param rate A float representing the rate value.
	*/
	public Rate(float rate){
		this.rate=rate;
	}

	/** Gets rate value.
	 * @return A float representing the rate value.
	*/
	public float getRate() {
		return this.rate;
	}

	/** Sets the rate value.
	 * @param rate A float representing the rate value.
	*/
	public void setRate(float rate) {
		this.rate = rate;
	}
	
}