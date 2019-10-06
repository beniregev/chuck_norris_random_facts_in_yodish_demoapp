package com.chuck_norris_random_facts_in_yodish.demos;

import com.chuck_norris_random_facts_in_yodish.controller.FactServiceResponseEntity;
import com.chuck_norris_random_facts_in_yodish.model.Fact;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RestApiHttpRequestsDemo {
    public static final String USER_AGENT = "Mozilla/5.0";

    private BufferedReader in;
    private HttpURLConnection conn = null;
    private StringBuffer result;
    private StringBuffer response;

    private String sendGetRequest(final String stringUrl) {
        result = new StringBuffer();

        try {
            URL url = new URL(stringUrl);
            conn = (HttpURLConnection) url.openConnection();

            // optional default is GET
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", RestApiHttpRequestsDemo.USER_AGENT);
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            int httpStatusCode = conn.getResponseCode();

            Reader streamReader;
            if (httpStatusCode > 299) {
                streamReader = new InputStreamReader(conn.getErrorStream());
            } else {
                streamReader = new InputStreamReader(conn.getInputStream());
            }

            in = new BufferedReader( streamReader );

            response = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            conn.disconnect();

            //print result
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("HTTP Status Code: " + httpStatusCode);
            System.out.println("response = " + response.toString());

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

    /**
     * EXAMPLE:
     * Full POST request {@link URL} is "http://example.com/index.php?param1=value1&param2=value2&param3=value3"
     * @param aUrl {@link String} representing in standard {@link URL} format: Protocol://Domain[:port]/service/action[/page]"
     *          e.g. "http://example.com/index.php";
     * @param aUrlParameters {@link String} containing the query parameters seperated by &amp;.
     *          e.g. "param1=value1&param2=value2&param3=value3";
     * @return JSON {@link String} representing the resource that was added by the POST request.
     */
    private String sendPostRequest(String aUrl, String aUrlParameters) {
        byte[] postData = aUrlParameters.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;

        result = new StringBuffer();
        try {
            URL url = new URL(aUrl);
            conn = (HttpURLConnection) url.openConnection();

            //add reuqest header
            conn.setRequestMethod( "POST" );
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
//            conn.setRequestProperty( "User-Agent", RestApiHttpRequestsDemo.USER_AGENT);
            //conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            //conn.setRequestProperty("Accept-Language", "en-US");
            conn.setUseCaches( false );
            try ( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }

            int httpStatusCode = conn.getResponseCode();

            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters: " + aUrlParameters);
            System.out.println("HTTP Status Code: " + httpStatusCode);

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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.disconnect();
            } catch(NullPointerException npe) {
                npe.printStackTrace();
                result.append(npe.getMessage());
            }
        }
        return response.toString();
    }

    public static void main(String[] args) {
        RestApiHttpRequestsDemo demo = new RestApiHttpRequestsDemo();
        //  region variables declaration
        final String stringUrl1 = "https://api.nasa.gov/planetary/apod?api_key=NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo";
        final String stringChuckNorrisFactsUrl = "https://api.chucknorris.io/jokes/random";
        final String postTranslateToYodishUrl = "http://api.funtranslations.com/translate/yoda.json";
        final String postFactCreateUrl = "http://localhost:8080/fact/create";
        //  endregion

        String response1 = demo.sendGetRequest(stringUrl1);
        System.out.println("response = " + response1);

        //  region send GET request: Chuck Norris Random Fact
        String responseJson1 = demo.sendGetRequest(stringChuckNorrisFactsUrl);
        System.out.println("response JSON #1 = " + responseJson1);
        JSONObject obj = new JSONObject(responseJson1);
        if (responseJson1.indexOf("\"error\":") > -1) {
            //  Error
            int httpStatusCode = obj.getJSONObject("error").getInt("code");
            String HttpStatusText = obj.getJSONObject("error").getString("message");

            FactServiceResponseEntity response = new FactServiceResponseEntity();
            response.setStatusCode(HttpStatus.valueOf(httpStatusCode));

            System.out.println("An error occurred. Get request returned HTTP Status code " + httpStatusCode +
                    ", message: " + HttpStatusText);
            //return response;
            System.exit(0);
        }

        /*  {
                "categories": [],
                "created_at": "2016-05-01 10:51:41.584544",
                "icon_url": "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
                "id": "DZ3PBbjwTFayft0zIRurUQ",
                "updated_at": "2017-12-29 19:52:23.239841",
                "url": "https://api.chucknorris.io/jokes/DZ3PBbjwTFayft0zIRurUQ",
                "value": "The first terminator was sent back to kill Chuck Norris. Obviously that didn't work out."
            }
         */
        obj = new JSONObject(responseJson1);
        String factToTranslate = obj.getString("value");
        System.out.println("Chuck Norris Fact To Translate: " + factToTranslate);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        //  endregion

        //  region Send POST request to translate random fact text to Yodish
        String postTranslateToYodishParams = "text=" + factToTranslate;
        String responseJson2 = demo.sendPostRequest(postTranslateToYodishUrl, postTranslateToYodishParams);
        System.out.println("response JSON #2 = " + responseJson2);
        /*  {
         *      "success": { "total": 1 },
         *      "contents": {
         *          "translated": "After years of rigorous and physically demanding testing,Proved that chuck norris was stronger than chuck norris, it was. ",
         *          "text": "After years of rigorous and physically demanding testing, it was proved that Chuck Norris was stronger than Chuck Norris.",
         *          "translation": "yoda"
         *      }
         *  }
         */
        obj = new JSONObject(responseJson2);
        if (responseJson2.indexOf("\"error\":") > -1) {
            //  Error
            int httpStatusCode = obj.getJSONObject("error").getInt("code");
            String HttpStatusText = obj.getJSONObject("error").getString("message");

            FactServiceResponseEntity response = new FactServiceResponseEntity();
            response.setStatusCode(HttpStatus.valueOf(httpStatusCode));

            System.out.println("An error occured. Post request returned HTTP Status code " + httpStatusCode +
                    ", message: " + HttpStatusText);
            //return response;
            System.exit(0);
        }
        String translated = obj.getJSONObject("contents").getString("translated");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        //  endregion

        //  region Send POST request to add the translated fact to MongoDB
        //translated = "After years of rigorous and physically demanding testing,Proved that chuck norris was stronger than chuck norris,  it was.";
        String postFactCreateParams = "text=" + translated;
        String responseJson3 = demo.sendPostRequest(postFactCreateUrl, postFactCreateParams);
        System.out.println("response JSON #3 = " + responseJson3);
        if (responseJson3.indexOf("\"error\":") > -1) {
            //  Error
            int httpStatusCode = obj.getJSONObject("error").getInt("code");
            String HttpStatusText = obj.getJSONObject("error").getString("message");

            FactServiceResponseEntity response = new FactServiceResponseEntity();
            response.setStatusCode(HttpStatus.valueOf(httpStatusCode));

            System.out.println("An error occurred. Post request returned HTTP Status code " + httpStatusCode +
                    ", message: " + HttpStatusText);
            //return response;
            System.exit(0);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        //  endregion
        /*  {
         *      "id":"5d98f32e568c8a39b0509b96",
         *      "text":"On water,  jesus can walk,Chuck norris can run. ",
         *      "createdOn":"2019-10-05"
         *  }
         */
        obj = new JSONObject(responseJson3);
        String factId = obj.getString("id");
        String factText = obj.getString("text");;
        String createdOn = obj.getString("createdOn");

        Fact fact = new Fact(factText, LocalDate.parse(createdOn.subSequence(0, createdOn.length())));
        fact.setId(factId);
        System.out.println("New Fact = " + fact);

        List<Fact> facts = new ArrayList<>();
        facts.add(fact);

        Gson gson = new Gson();
        String jsonFacts = gson.toJson(facts);
        System.out.println("jsonFacts = " + jsonFacts);

        //return new FactServiceResponseEntity(HttpStatus.CREATED,"Created", facts);
    }
}
