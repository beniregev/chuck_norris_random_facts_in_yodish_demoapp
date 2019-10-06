package com.chuck_norris_random_facts_in_yodish.service;

import com.chuck_norris_random_facts_in_yodish.controller.FactServiceResponseEntity;
import com.chuck_norris_random_facts_in_yodish.model.Fact;
import com.chuck_norris_random_facts_in_yodish.repository.FactRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FactService {

    private BufferedReader in;
    private HttpURLConnection conn = null;
    private StringBuffer result;
    private StringBuffer response;

    @Autowired
    private FactRepository factRepository;

    public List<Fact> getAll() {
        return factRepository.findAll();
    }

    public Fact create(String text, LocalDate createdOn) {
        Fact fact = new Fact(text, createdOn);
        return factRepository.save(fact);
    }

    public Fact deleteById(final String factId) {
        Optional<Fact> optional = factRepository.findById(factId);
        if (optional.isPresent()) {
            Fact fact = optional.get();
            factRepository.deleteById(factId);
            return fact;
        }
        return null;
    }

    public FactServiceResponseEntity createYodishTextFromRandomFact() {
        //  region variables declaration
        final String stringChuckNorrisFactsUrl = "https://api.chucknorris.io/jokes/random";
        final String postTranslateToYodishUrl = "http://api.funtranslations.com/translate/yoda.json";
        final String postFactCreateUrl = "http://localhost:8080/fact/create";
        //  endregion

        //  region send GET request: Chuck Norris Random Fact
        String responseJson1 = this.sendGetRequest(stringChuckNorrisFactsUrl);
        System.out.println("GET request: Chuck Norris Random Fact -- response JSON #1 = " + responseJson1);
        JSONObject jsonObjResponse = this.getJsonObject(responseJson1);
        int httpStatusCode;
        if (responseJson1.indexOf("\"error\":") > -1) {
            //  Get Request Error Handling
            return handleRequestError(jsonObjResponse);
        }

        /*  GET request returns the following JSON string:
            {
                "categories": [],
                "created_at": "<fact-creation-datetime-in-yyyy-mm-dd hh:MM:ss.ffffff-datetime-format>",
                "icon_url": "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
                "id": "<string-containing-generated-id>",
                "updated_at": "<fact-updated-datetime-in-yyyy-mm-dd hh:MM:ss.ffffff-datetime-format>",
                "url": "https://api.chucknorris.io/jokes/<the-id-that-is-in-the-JSON>",
                "value": "<fact-text>"
            }
         */
        String factToTranslate = jsonObjResponse.getString("value");
        System.out.println("Chuck Norris Fact To Translate: " + factToTranslate);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        //  endregion

        //  region Send POST request to translate random fact text to Yodish
        String postTranslateToYodishParams = "text=" + factToTranslate;
        String responseJson2 = this.sendPostRequest(postTranslateToYodishUrl, postTranslateToYodishParams);
        System.out.println("POST request to translate random fact text to Yodish -- response JSON #2 = " + responseJson2);

        jsonObjResponse = this.getJsonObject(responseJson2);
        if (responseJson2.indexOf("\"error\":") > -1) {
            //  Post Request Error Handling
            return handleRequestError(jsonObjResponse);
        }

        /*  POST Request to translate given text to Yodish returns the following JSON string:
            {
                "success": { "total": number-of-objects-in-contents },
                "contents": {
                    "translated": "<the-translated-text>",
                    "text": "<the-original-text-to-translate>",
                    "translation": "yoda"
                }
            }
         */
        String translated = jsonObjResponse.getJSONObject("contents").getString("translated");
        System.out.println("Text translated to Yodish: " + translated);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        //  endregion

        //  region Send POST request to add the translated fact to MongoDB
        //translated = "After years of rigorous and physically demanding testing,Proved that chuck norris was stronger than chuck norris,  it was.";
        String postFactCreateParams = "text=" + translated;
        String responseJson3 = this.sendPostRequest(postFactCreateUrl, postFactCreateParams);
        System.out.println("POST request: create new resource is MongoDB -- response JSON #3 = " + responseJson3);

        jsonObjResponse = this.getJsonObject(responseJson3);
        if (responseJson3.indexOf("\"error\":") > -1) {
            return handleRequestError(jsonObjResponse);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        //  endregion

        //  region prepare return response that is not an error
        /*  Post Request returns the following JSON string:
            {
                "id":"<fact-id>",
                "text":"<fact-text>",
                "createdOn":"<fact-creation-date in yyyy-mm-dd date format>"
            }
         */
        String createdOn = jsonObjResponse.getString("createdOn");
        Fact fact = new Fact(jsonObjResponse.getString("text"), LocalDate.parse(createdOn.subSequence(0, createdOn.length())));
        fact.setId(jsonObjResponse.getString("id"));
        System.out.println("New Fact = " + fact);

        List<Fact> facts = new ArrayList<>();
        facts.add(fact);
        //  endregion

        return new FactServiceResponseEntity(HttpStatus.CREATED,"Created", facts);
    }

    private String sendGetRequest(final String stringUrl) {
        result = new StringBuffer();

        try {
            URL url = new URL(stringUrl);
            conn = (HttpURLConnection) url.openConnection();

            // optional default is GET
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
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

    /**
     * EXAMPLE:  Full POST request {@link URL} is "http://example.com/index.php?param1=value1&param2=value2&param3=value3"
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
            //conn.setRequestProperty( "User-Agent", RestApiHttpRequestsDemo.USER_AGENT);
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
        System.out.println("FactService.sendPostRequest(aURL, aUrlParameters) -- response: " + response.toString());
        return response.toString();
    }

    private JSONObject getJsonObject(final String jsonString) {
        return new JSONObject(jsonString);
    }

    /**
     *
     * @param objJsonError
     * @return
     */
    private FactServiceResponseEntity handleRequestError(final JSONObject objJsonError) {
        /*  Create an Error JSON string and return it as part of ResponseEntity
            {
                "error": {
                    "code": 429,
                    "message": "Too Many Requests: Rate limit of 5 requests per hour exceeded. Please wait for 3 seconds."
                }
            }
         */
        int httpStatusCode = objJsonError.getJSONObject("error").getInt("code");
        String HttpStatusText = objJsonError.getJSONObject("error").getString("message");
        StringBuffer jsonObjectError = new StringBuffer("{ \"error\": { \"code\": ")
                .append(httpStatusCode)
                .append(", \"message\": \"")
                .append(HttpStatusText)
                .append("\" }  }");
        System.out.println("An error occurred. Get request returned HTTP Status code " + httpStatusCode +
                ", message: " + HttpStatusText + "\n Error JSON Object: " + jsonObjectError.toString());

        return new FactServiceResponseEntity(HttpStatus.valueOf(httpStatusCode), jsonObjectError.toString());
    }
}
