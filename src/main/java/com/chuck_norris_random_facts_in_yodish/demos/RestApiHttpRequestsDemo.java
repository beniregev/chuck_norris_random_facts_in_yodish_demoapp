package com.chuck_norris_random_facts_in_yodish.demos;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class RestApiHttpRequestsDemo {
    public static final String USER_AGENT = "Mozilla/5.0";

    private BufferedReader in;
    private HttpURLConnection conn = null;
    private StringBuffer result;
    private StringBuffer response;

    public String sendGetRequest(final String stringUrl) {
        result = new StringBuffer();

        try {
            URL url = new URL(stringUrl);
            conn = (HttpURLConnection) url.openConnection();

            // optional default is GET
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", RestApiHttpRequestsDemo.USER_AGENT);
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

//        //add request header
//        conn.setRequestProperty("User-Agent", USER_AGENT);

            int httpStatusCode = conn.getResponseCode();

            Reader streamReader;
            if (httpStatusCode > 299) {
                streamReader = new InputStreamReader(conn.getErrorStream());
            } else {
                streamReader = new InputStreamReader(conn.getInputStream());
            }

            in = new BufferedReader( streamReader );
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            conn.disconnect();

            //print result
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("HTTP Status Code: " + httpStatusCode);
            System.out.println("response = { " + response.toString() + " }");

            result.append(response.toString()).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
            result.append(e.getMessage()).append("\n");
        } finally {
            try {
                conn.disconnect();
            } catch(NullPointerException npe) {
                npe.printStackTrace();
                result.append(npe.getMessage());
            }
        }
        return result.toString();
    }

    public String sendPostRequest() {
        String urlString = "https://selfsolve.apple.com/wcResults.do";
        result = new StringBuffer();

        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();

            //add reuqest header
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", RestApiHttpRequestsDemo.USER_AGENT);
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());

            result.append(response.toString()).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
            result.append(e.getMessage()).append("\n");
        } finally {
            try {
                conn.disconnect();
            } catch(NullPointerException npe) {
                npe.printStackTrace();
                result.append(npe.getMessage());
            }
        }
        return result.toString();
    }

    public String sendPostRequest(String stringUrl) {
        result = new StringBuffer();
        try {
            URL url = new URL(stringUrl);
            conn = (HttpURLConnection) url.openConnection();

            //  add request header
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", RestApiHttpRequestsDemo.USER_AGENT);
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "text=Confused by looking at the infinity sign, chuck norris once got,It back into a clock, so he roundhouse kicked,It straped onto his wrist and calls it his wristwatch, he now has.";

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());

            result.append(response.toString()).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
            result.append(e.getMessage()).append("\n");
        } finally {
            try {
                conn.disconnect();
            } catch(NullPointerException npe) {
                npe.printStackTrace();
                result.append(npe.getMessage());
            }
        }
        return result.toString();
    }

    public String sendPostRequest(String stringUrl, Map<String, String> pathParams, Map<String, String> requestParams) {
        result = new StringBuffer();
        response = new StringBuffer();

        try {
            URL url = new URL(stringUrl);
            conn = (HttpURLConnection) url.openConnection();

            //add reuqest header
            conn.setRequestMethod("POST");

            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String stringUrl1 = "https://api.nasa.gov/planetary/apod?api_key=NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo";
        String stringUrl2 = "http://www.google.com/search?q=mkyong";
        String urlLocalHostPost = "http://localhost:8080/fact/create";

        RestApiHttpRequestsDemo demo = new RestApiHttpRequestsDemo();

        String response1 = demo.sendGetRequest(stringUrl1);
        System.out.println("response1 = { " + response1 + " }");

//        String response2 = demo.sendPostRequest();
//        System.out.println("response2 = { " + response2 + " }");

        String response3 = demo.sendPostRequest(urlLocalHostPost);
        System.out.println("response3 = { " + response3 + " }");
    }
}
