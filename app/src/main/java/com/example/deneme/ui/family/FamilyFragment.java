package com.example.deneme.ui.family;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.deneme.JSONParser;
import com.example.deneme.R;
import com.example.deneme.model.User.CustomAdapter;
import com.example.deneme.model.User.Family;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FamilyFragment extends Fragment {
    private ArrayList<Family> families;
    private ListView listAile;
    private CustomAdapter listViewAdapter;
    Button btnPersonAdd;
    View root;
    int REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_family, container, false);

        init();
        initializeAdapter();
        try {
            fillArrayList(families);
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnPersonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FamilySaveFragment familySaveFragment = new FamilySaveFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, familySaveFragment)
                        .commit();
            }
        });

        return root;
    }

    private void initializeAdapter() {
        families = new ArrayList<Family>();
        listViewAdapter = new CustomAdapter(getActivity(),families);
        listAile.setAdapter(listViewAdapter);
    }

    private void fillArrayList(ArrayList<Family> _familyList) throws Exception {
        GetData gd;

        List<Family> familyList= getSharedPref();
        if(familyList!=null) {
            for (Family family : familyList) {
                gd = new GetData();
                family.setIndex(gd.execute(family.getCity().toString()).get());

                // family.setIndex("gd.execute(family.getCity().toString()).get())");

                _familyList.add(family);


            }
        }


    }
    /*
    public static String getdata(String cityName)
    {
        String json = null;
        String ResponseCode="-1";
        JSONParser jParser = new JSONParser();
        String FinalURL = "http://10.0.2.2:4242/airqualityindex/"+cityName;
        Log.i("***", FinalURL);
        try {
            json = jParser.getJSONFromUrl(FinalURL).getJSONObject("result").getString("aqi");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }*/
    public class GetData extends AsyncTask<String, Void, String> {
        String ResponseMessage = "";
        String json;
        String host;
        String port;
        String message;

        @Override
        protected String doInBackground(String... strings) {
            String ResponseCode="-1";
            JSONParser jParser = new JSONParser();
            String FinalURL = "http://10.0.2.2:4242/airqualityindex/"+strings[0];
            Log.i("***", FinalURL);
            try {
                json = jParser.getJSONFromUrl(FinalURL).getJSONObject("result").getString("aqi");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            host = "10.0.2.2";
            port = "4242";
            //
            //message = "airqualityindex/"+city;
            message = "airqualityindex/konya";
        }



        @Override
        protected void onPostExecute(String result) {
            return;
        }
    }


    private List<Family> getSharedPref() {

        SharedPreferences shared_preferences = getActivity().getSharedPreferences("Family", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = shared_preferences.getString("key", "");
        List<Family> familyGet = gson.fromJson(json, new TypeToken<ArrayList<Family>>() {
        }.getType());
        return familyGet;

    }


    private void init() {
        btnPersonAdd = root.findViewById(R.id.btnPersonAdd);
        listAile = root.findViewById(R.id.listAile);
    }

}


