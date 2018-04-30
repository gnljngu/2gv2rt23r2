package com.sharon.govtech.assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

/**
* The MASApiCall class makes calls to APIs provided by Monetary Authority of Singapore (MAS)
*
* @author  Tan Yee Ping Sharon
* @version 1.0
* @since   2018-04-30 
*/

public class MASApiCall {
    
    private String base_url;
    private String url_parameters;
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final int MAX_RECORDS = 100;
    
    /** Constructor creates an api call with the specified range of dates
    * and sets base_url and url_parameters properties
    * @param from Starting date.
    * @param to End date.
    */
    public MASApiCall(String from, String to) {
        
        this.base_url = "https://eservices.mas.gov.sg/api/action/datastore/search.json";
        String resource_id = "5f2b18a8-0883-4769-a635-879c63d3caac";
        this.url_parameters = "resource_id="+resource_id+"&limit="+MAX_RECORDS+"&between[end_of_month]="+from+","+to;
    }

    /*
     * creates a URL with the base_url and url_param, and connects to the API. 
     * The method then returns response from API call that filters the result columns based on the url_parameters.   
     * @return A String containing the API response, or a error message if an exception occurs.
     */
    public String getApiResponse() {

        try {
            URL url = new URL(this.base_url + '?' + this.url_parameters);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();     
                return response.toString();
            }
            else {
                return "GET request not worked";
            }
        }
        catch (MalformedURLException e) {
            return "GET request not worked";
        }
        catch (IOException e) {
            return "GET request not worked";
        }
        catch (Exception e) {
            return "GET request not worked";
        }
    }
    
}


