package com.example.deneme;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

    private InputStream is = null;
    private JSONObject jObj = null;
    private String json = "";

    // constructor
    public JSONParser() {

    }

    public JSONObject getJSONFromUrl(String url) {

        // Making HTTP request
        try {
            // defaultHttpClient
            URL urlObject = new java.net.URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
            conn.setRequestMethod("GET");
            is = new BufferedInputStream(conn.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8); // iso-8859-1 //
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String tmp = line + "/n";
                sb.append(tmp);
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.i("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            if (json != null) {
                jObj = new JSONObject(json);
            } else {
                jObj = null;
            }

        } catch (JSONException e) {
            //
        }

        // return JSON String
        return jObj;
    }
}