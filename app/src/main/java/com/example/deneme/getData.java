package com.example.deneme;

import android.util.Log;

import org.json.JSONObject;

public class getData {

    String ResponseMessage = "";
    JSONObject json;
    String host;
    String port;
    String message;

    public JSONObject getAirQualityIndex (String CityName)
    {
        String ResponseCode="-1";
        JSONParser jParser = new JSONParser();
        String FinalURL = "http://10.0.2.2:4242/airqualityindex/"+CityName;
      //  String FinalURL = "http://10.0.2.2:8081/listusers";
        Log.i("***", FinalURL);
        json = jParser.getJSONFromUrl(FinalURL);
        return json;



    }

}
