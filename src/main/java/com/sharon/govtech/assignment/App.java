package com.sharon.govtech.assignment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
* The App program implements an application that retrieves and processes data on finance rates 
* of banks and financial companies from Monetary Authority of Singapore (MAS), 
* and provides the information required on the screen.
*
* @author  Tan Yee Ping Sharon
* @version 1.0
* @since   2018-04-30 
*/

public class App
{

	/** Represents the set of monthly bank rates */
    private BankRate[] bank_rates;

    /** Represents the set of monthly financial company rates */
    private FinancialCompanyRate[] fc_rates;

    /** Represents the set of monthly interest rates */
    private InterestRate[] interest_rates;

    /** Represents the set of dates that correspond to each of sets of finance rates */
    private String[] end_of_months;

    /** Represents the number of records in the retrieved data */
    private int numRecords;

    /** Represents the API Call class object to make calls to the MAS APIs */
	private MASApiCall api;

	/** Constructor creates an API Call class object to connect to the MAS API and retrieve records data for processing
    * @param from_date Starting date that corresponds to the first records row.
    * @param to_date End date that corresponds to the last records row.
    * @throws ParseException on parsing error.
    * @throws Exception on other errors.
    */
	public App(String from_date, String to_date) throws ParseException, Exception {
		numRecords = 0;
    	api = new MASApiCall(from_date,to_date);
    	String response = api.getApiResponse();
    	JSONParser parser = new JSONParser();

    	int i=0;
        try {
        	//parse API response into a JSONObject and retrieves the records data
            Object obj = parser.parse(response);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject result = (JSONObject) jsonObject.get("result");
            JSONArray arr = (JSONArray) result.get("records");

            numRecords = arr.size();

            //initialize arrays
            bank_rates = new BankRate[numRecords];
            fc_rates = new FinancialCompanyRate[numRecords];
            interest_rates = new InterestRate[numRecords];
            end_of_months = new String[numRecords];

            // convert date format to MMM-yyyy for better display purposes
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM");  //given format
			SimpleDateFormat dt1 = new SimpleDateFormat("MMM-yyyy");  //this is the formatted date we want
			Date inputDate = null;

            // loop json records and populate arrays
            for(i=0;i<numRecords;i++) {
                JSONObject jsonobj = (JSONObject)arr.get(i);
                end_of_months[i] = (String)jsonobj.get(BankRate.MONTH);
                inputDate = dt.parse(end_of_months[i]);
				end_of_months[i] = dt1.format(inputDate);

                bank_rates[i] = new BankRate();
                bank_rates[i].setFixedDeposit3M(Float.parseFloat((String)jsonobj.get(BankRate.FIXED_DEPOSIT_3M)));
                bank_rates[i].setFixedDeposit6M(Float.parseFloat((String)jsonobj.get(BankRate.FIXED_DEPOSIT_6M)));
                bank_rates[i].setFixedDeposit12M(Float.parseFloat((String)jsonobj.get(BankRate.FIXED_DEPOSIT_12M)));
                bank_rates[i].setSavingDeposit(Float.parseFloat((String)jsonobj.get(BankRate.SAVINGS_DEPOSIT)));

                fc_rates[i] = new FinancialCompanyRate();
                fc_rates[i].setFixedDeposit3M(Float.parseFloat((String)jsonobj.get(FinancialCompanyRate.FIXED_DEPOSIT_3M)));
                fc_rates[i].setFixedDeposit6M(Float.parseFloat((String)jsonobj.get(FinancialCompanyRate.FIXED_DEPOSIT_6M)));
                fc_rates[i].setFixedDeposit12M(Float.parseFloat((String)jsonobj.get(FinancialCompanyRate.FIXED_DEPOSIT_12M)));
                fc_rates[i].setSavingDeposit(Float.parseFloat((String)jsonobj.get(FinancialCompanyRate.SAVINGS_DEPOSIT)));

                interest_rates[i] = new InterestRate();
                interest_rates[i].setRate(Float.parseFloat((String)jsonobj.get(InterestRate.INTEREST_RATE)));                
            }   

            System.out.println("number of records: " + numRecords);
        } 
        catch (NullPointerException e) {
            System.out.println("Some records are missing " + (end_of_months[i] != null ? ("for " + end_of_months[i]) : ""));
            numRecords = i;
            if (i > 0) {
            	System.out.println("Using only records from " + end_of_months[0] + " to " + end_of_months[i-1]);
            }
            else {
            	throw e;
            }
        } catch (ParseException e) {
            System.out.println("Error retrieving records");
            throw e;
        } catch (Exception e) {
            System.out.println("Error retrieving records");
            throw e;
        } 
	}

	/*
     * compares and prints the monthly rates of the financial companies against banks, 
     * and outputs whether financial companies have a higher rate for that month
     */
	public void printRatesComparison() {
		System.out.println();
		System.out.println("-----------RATES COMPARISON ("+end_of_months[0]+" to "+end_of_months[numRecords-1]+")-----------");
		String rate = "same";
		float bank_rate;
		float fc_rate;
		for(int i=0;i<numRecords;i++) {
			System.out.println(end_of_months[i]);

			System.out.println("Fixed Deposit (3 months):");
			bank_rate = bank_rates[i].getFixedDeposit3M();
			fc_rate = fc_rates[i].getFixedDeposit3M();
			compareAndPrintRate(bank_rate, fc_rate);
			System.out.println();

			System.out.println("Fixed Deposit (6 months):");
			bank_rate = bank_rates[i].getFixedDeposit6M();
			fc_rate = fc_rates[i].getFixedDeposit6M();
			compareAndPrintRate(bank_rate, fc_rate);
			System.out.println();

			System.out.println("Fixed Deposit (12 months):");
			bank_rate = bank_rates[i].getFixedDeposit12M();
			fc_rate = fc_rates[i].getFixedDeposit12M();
			compareAndPrintRate(bank_rate, fc_rate);
			System.out.println();

			System.out.println("Savings Deposit:");
			bank_rate = bank_rates[i].getSavingDeposit();
			fc_rate = fc_rates[i].getSavingDeposit();
			compareAndPrintRate(bank_rate, fc_rate);
			System.out.println();
        }   
		System.out.println();
		System.out.println("----------------------------------------------------------------");
		System.out.println();
	}

	/*
     * computes and prints the overall average of financial companies rates against bank rates.
     */
	public void printOverallAverageRates() {
		System.out.println();
		System.out.println("-----------OVERALL AVERAGE RATES ("+end_of_months[0]+" to "+end_of_months[numRecords-1]+")-----------------");
		double bank_fixed_deposits_3m_sum = 0.0;
		double fc_fixed_deposits_3m_sum = 0.0;
		double bank_fixed_deposits_6m_sum = 0.0;
		double fc_fixed_deposits_6m_sum = 0.0;
		double bank_fixed_deposits_12m_sum = 0.0;
		double fc_fixed_deposits_12m_sum = 0.0;
		double bank_savings_deposits_sum = 0.0;
		double fc_savings_deposits_sum = 0.0;

		double bank_avg = 0.0;
		double fc_avg = 0.0;

		// iterates all the records to compute the total sum for each finance rate
		for(int i=0;i<numRecords;i++) {
			bank_fixed_deposits_3m_sum += bank_rates[i].getFixedDeposit3M();
			fc_fixed_deposits_3m_sum += fc_rates[i].getFixedDeposit3M();
			bank_fixed_deposits_6m_sum += bank_rates[i].getFixedDeposit6M();
			fc_fixed_deposits_6m_sum += fc_rates[i].getFixedDeposit6M();
			bank_fixed_deposits_12m_sum += bank_rates[i].getFixedDeposit12M();
			fc_fixed_deposits_12m_sum += fc_rates[i].getFixedDeposit12M();
			bank_savings_deposits_sum += bank_rates[i].getSavingDeposit();
			fc_savings_deposits_sum += fc_rates[i].getSavingDeposit();
		}
		
		System.out.println("Fixed Deposit (3 months):");
		computeAndPrintAverageRate(bank_fixed_deposits_3m_sum, fc_fixed_deposits_3m_sum);
		System.out.println();	

		System.out.println("Fixed Deposit (6 months):");
		computeAndPrintAverageRate(bank_fixed_deposits_6m_sum, fc_fixed_deposits_6m_sum);
		System.out.println();	

		System.out.println("Fixed Deposit (12 months):");
		computeAndPrintAverageRate(bank_fixed_deposits_12m_sum, fc_fixed_deposits_12m_sum);
		System.out.println();	

		System.out.println("Savings Deposit:");
		computeAndPrintAverageRate(bank_savings_deposits_sum, fc_savings_deposits_sum);
		System.out.println();	

		System.out.println("----------------------------------------------------------------");
		System.out.println();
	}

	/*
	* compares and prints interest rates slope based on an upward/downward/stagnant trend
	*/
	public void printInterestRateSlope() {
		System.out.println();
		System.out.println("-------------INTEREST RATE SLOPE ("+end_of_months[0]+" to "+end_of_months[numRecords-1]+")----------------");
			
		if (numRecords > 1) {	

			//currTrend indicates if current interest rate is lower/same/higher than previous interest rate
			//prevTrend holds the previous currTrend	
			//startIndex points to the beginning end_of_month where we start comparing the current trend
			String currTrend = "";
			String prevTrend = "";
			int startIndex = -1;
			int i=1;
			float currRate, prevRate;

			//iterate through and compare each interest_rate item with the previous interest_rate item
			while (i<numRecords) {

				currRate = interest_rates[i].getRate();
				prevRate = interest_rates[i-1].getRate();
				currTrend = compareTrend(currRate, prevRate);
								
				if (startIndex == -1) { 
					prevTrend = currTrend; 
					startIndex = 0;
				}
				else {
					if (prevTrend != currTrend) {
						System.out.println(end_of_months[startIndex] + " to " + end_of_months[i-1]+": " + prevTrend + " trend");
						startIndex = i-1;
					}
					prevTrend = currTrend;
				}	
				i++;
			}

			System.out.println(end_of_months[startIndex] + " to " + end_of_months[numRecords-1]+": " + currTrend + " trend");
		}
		System.out.println();

		// print overall trend from the beginning to the end of the specified time period
		System.out.println("Overall " +compareTrend(interest_rates[numRecords-1].getRate(), interest_rates[0].getRate())+ " trend from " + end_of_months[0] + " to " + end_of_months[numRecords-1]);
		System.out.println();
		System.out.println("----------------------------------------------------------------");
		System.out.println();
	}


	/*
     * computes and prints the overall average of financial companies finance rates as well as bank finance rates.
     * @param bank_sum A double representing the total sum of bank finance rates. This can be the total sum of fixed deposit rates or savings deposit rates.
     * @param fc_sum A double representing the total sum of financial companies finance rates. This can be the total sum of fixed deposit rates or savings deposit rates.
     */
	public void computeAndPrintAverageRate(double bank_sum, double fc_sum) {
		double bank_avg = bank_sum / numRecords;
		double fc_avg = fc_sum / numRecords;
		System.out.println("Bank:                " + String.format( "%.2f", bank_avg ));
		System.out.println("Financial Companies: " + String.format( "%.2f", fc_avg ));
	}

	/*
     * print and compare 2 different rates, and outputs the compared result on the screen
     * @param bank_rate A float representing the specified bank rate
     * @param fc_rate A float representing the specified financial companies rate
     */
	public void compareAndPrintRate(float bank_rate, float fc_rate) {
		System.out.println("Banks:	                " + bank_rate);
		System.out.println("Financial Companies:	" + fc_rate);
		System.out.println("Financial Companies have a " + compareRate(bank_rate, fc_rate) + " rate");
	}

	/*
     * compare 2 different rates and returns the result
     * @param bank_rate A float representing the specified bank rate
     * @param fc_rate A float representing the specified financial companies rate
     * @return A string representing if the second rate (fc_rate) is higher, lower or the same as the first rate (bank_rate)
     */
	public String compareRate(float bank_rate, float fc_rate){
    	
    	String rate;
    			            
    	if (fc_rate > bank_rate) {
			rate = "higher";
		}
		else if (fc_rate < bank_rate) {
			rate = "lower";
		}
		else {
		    rate = "same";
		}

		return rate;
    }

    /*
     * compare the difference between the current rate and the previous rate and returns the trend
     * @param curr_rate A float representing the specified current rate
     * @param prev_rate A float representing the specified previous rate
     * @return A string representing if the 2 rates are on an upward, downward or stagnant trend
     */
    public String compareTrend(float curr_rate, float prev_rate) {
    	String trend;
    			            
    	if (curr_rate > prev_rate) {
			trend = "upward";
		}
		else if (curr_rate < prev_rate) {
			trend = "downward";
		}
		else {
		    trend = "stagnant";
		}

		return trend;
    }

    /*
     * verify and format date inputs, and return the formatted inputs for processing
     * @param inputs A string array representing the specified date inputs
     * @return A string array representing the formatted date inputs
     */
    public static String[] verifyAndUpdateDatesInput(String[] inputs) {

		boolean has_error = false;
		
    	// check that there are 2 dates (starting date and end date) entered in the user input
    	if (inputs.length != 2) {
    		System.out.println( "Invalid number of inputs" );
    		has_error = true;
    		return null;
    	}

    	// test if input is the correct format and return the formatted dates
		SimpleDateFormat dt = new SimpleDateFormat("MMM-yyyy");  //required format for date input from user
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM");  //this is the formatted date we want
		
		Date[] inputDates = new Date[inputs.length];
		String[] newInputs = new String[inputs.length];
		

		//verify inputs have the correct format
		for (int i=0; i<inputs.length; i++) {
			try {
				inputDates[i] = dt.parse(inputs[i]);
				newInputs[i] = dt1.format(inputDates[i]);
			}
			catch (Exception e) {
				newInputs[i] = null;
			}
			
			if (newInputs[i] == null) {
				has_error = true;
				break;
			}
		}

		if (!has_error) {
			//check that the 2nd date (end date) is greater than the 1st date (starting date) 
			if (inputDates[1].before(inputDates[0])) {
				System.out.println("Start date is greater than end date");
				has_error = true;
			}
			else {

				//check that starting date and end date should not be after the current(NOW) date
				Date currDate = new Date();
				if (inputDates[0].after(currDate)) {
					System.out.println("Starting date is greater than current date");
					System.out.println("Please enter a date before today");
					has_error = true;
				}
				else if (inputDates[1].after(currDate)) {
					System.out.println("End date is greater than current date");
					System.out.println("Please enter a date before today");
					has_error = true;
				}

				if (!has_error) {
					//check that the 2 dates should not be more than 100 months apart
					Calendar cal = Calendar.getInstance();
					cal.setTime(inputDates[0]);
					int startMonth = cal.get(Calendar.MONTH);
					int startYear = cal.get(Calendar.YEAR);
					cal.setTime(inputDates[1]);
					int endMonth = cal.get(Calendar.MONTH);
					int endYear = cal.get(Calendar.YEAR);
					int numMonthsDiff = (endYear - startYear) * 12 + (endMonth - startMonth);

					if (numMonthsDiff > 100) {
						System.out.println("Start date and end date are more than 100 months apart");
						has_error = true;
					}
				}
			}
		}
		
		if (has_error) {
			return null;
		}
		else {
			return newInputs;
		}
    }

    
    /**
   	* This is the main method which waits for user inputs for verification and making API call to 
   	* retrieve the required data for processing.
   	* @param args Unused.
   	* @return Nothing.
   	*/
    public static void main( String[] args )
    {
    	//use Scanner class to wait for user input
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Please enter starting date and end date in format (mmm-yyyy) separated by space (e.g. Jan-2017 Dec-2017)");   
                //exit program if user presses 'e'	
                if (scanner.hasNext("e")) {
	        		System.exit(0);
	        	}

	        	String line = scanner.nextLine();
                String[] inputs = line.trim().split(" ");
                
                //get formatted dates from user input
    			inputs = verifyAndUpdateDatesInput(inputs);

    			if (inputs != null) {
    				try {
    					App app = new App(inputs[0], inputs[1]);
        				while (true) {
        					System.out.println("Please enter one of the following options");
	        				System.out.println("Enter 1 to view and compare the financial companies rates against bank rates");
	        				System.out.println("Enter 2 to compare the overall average of financial companies rates against bank rates.");
	        				System.out.println("Enter 3 to view the trend of interest rates slope");
	        				System.out.println("Enter 0 to start over");
	        				System.out.println("Enter e to exit program");

	        				if(!scanner.hasNextInt()) {
	        					if (scanner.hasNext("e")) {
	        						System.exit(0);
	        					}
	        					else {
	        						System.out.println("invalid input");
	        						scanner.nextLine();
	        					}
	        				}
	        				else {
	        					int option = Integer.parseInt(scanner.nextLine().trim());    			
	        					if (option >=0 && option < 4) {

	        						if (option == 0) {
	        							break;
	        						}
	        						else if (option==1) {
	        							app.printRatesComparison();
	        						}
	        						else if (option == 2) {
	        							app.printOverallAverageRates();
	        						}
	        						else {
	        							app.printInterestRateSlope();
	        						}
	        					}
	        					else {
	        						System.out.println("invalid option");

	        					}
	        				}
        				}
    				}
    				catch (Exception e) {
    					System.out.println( "Error retrieving records" );
    					break;
    				}
    			}
    			else {
    				System.out.println("Input error");
    			}
            }
        }
        catch(IllegalArgumentException e) {
			System.out.println( "Invalid input" );
			//e.printStackTrace();
		}
		catch(NullPointerException e) {
			System.out.println( "Missing Records" );
			//e.printStackTrace();
		}
		catch(Exception e) {
			System.out.println( "Error" );
			//e.printStackTrace();
		}    
    }
}
