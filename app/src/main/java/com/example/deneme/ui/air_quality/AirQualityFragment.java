package com.example.deneme.ui.air_quality;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.deneme.JSONParser;
import com.example.deneme.R;
import com.example.deneme.getData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class AirQualityFragment extends Fragment implements LocationListener {
    TextView txtBoylam,txtSehir,txtEnlem,txtHavaSonuc;
    Button btnHavaKalite,btnKonumKaydet;
    LinearLayout llSonuc;
    LocationManager locationManager;
    String provider;
    View root;
    String city = "konya";
    private GetData gd;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    private AirQualityViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        slideshowViewModel =
//                ViewModelProviders.of(this).get(SlideshowViewModel.class);
         root = inflater.inflate(R.layout.fragment_air_quality, container, false);
        // final TextView textView = root.findViewById(R.id.text_slideshow);
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                //    textView.setText(s);
//            }
//        });
        init();
        llSonuc.setVisibility(View.INVISIBLE);

        btnHavaKalite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);                //Toast.makeText(getActivity(), "disable provider", Toast.LENGTH_SHORT).show();
                //servis tanımlaması
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                provider = locationManager.getBestProvider(criteria, false);
               // provider = locationManager.GPS_PROVIDER(5000,10,locationListener);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                //bilinen en iyi konum
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    onLocationChanged(location);
                } else {
//                    txtEnlem.setText("not available");
//                    txtBoylam.setText("not available");
                }
                llSonuc.setVisibility(View.VISIBLE);
               // gd = new getData();
                //txtHavaSonuc.setText(gd.getAirQualityIndex("mersin").toString());
                gd = new GetData();
                gd.execute();

            }
        });
        btnKonumKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //konum kayıt
                ArrayList<Double> loc =new ArrayList<>();
                loc.add(lat);
                loc.add(log);

                SharedPreferences shared_preferences = getActivity().getSharedPreferences("Location",MODE_PRIVATE);
                SharedPreferences.Editor editor = shared_preferences.edit();
                Gson gson = new Gson();
                String jsonSet = gson.toJson(loc);
                editor.putString("keyLoc", jsonSet);
                editor.commit();
                Toast.makeText(getActivity(), "Konum kaydedildi !", Toast.LENGTH_SHORT).show();
            }
        });


        return root;

    }
    public class GetData extends AsyncTask<Void, Void, String> {
        String ResponseMessage = "";
        String json;
        String adres;
        String port;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adres = "floating-journey-82816.herokuapp.com/airqualityindex";
            //
            //message = "airqualityindex/"+city;
            message = "konya";
        }

        @Override
        protected String doInBackground(Void... params) {
            String ResponseCode="-1";
            JSONParser jParser = new JSONParser();
            String FinalURL = "https://"+adres+"/"+message;
            Log.i("***", FinalURL);
            try {
                json = jParser.getJSONFromUrl(FinalURL).getJSONObject("result").getString("aqi");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ResponseCode;
        }

        @Override
        protected void onPostExecute(String result) {
          txtHavaSonuc.setText("Bulundugunuz şehrin hava kalitesi : " +json.toString()+"AQ(iyi)");
        }
    }

    private void init() {

        btnHavaKalite = root.findViewById(R.id.btnHavaKalite);
        btnKonumKaydet = root.findViewById(R.id.btnKonumKaydet);
        txtSehir = root.findViewById(R.id.txtSehir);
        llSonuc = root.findViewById(R.id.llSonuc);
        txtHavaSonuc = root.findViewById(R.id.txtHavaSonuc);

    }


    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(this);
    }
    double log;
    double lat;
    @Override
    public void onLocationChanged(Location location) {
         lat = location.getLatitude();
         log = location.getLongitude();

        //get city name
        String cityName=null;
        Geocoder gcd =new Geocoder(getActivity(), Locale.getDefault());
        List<Address> adres;
        try{
            adres=gcd.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            if (adres.size()>0)
            {
              String country =adres.get(0).getCountryName();
               city =adres.get(0).getAdminArea();
              String district =adres.get(0).getSubAdminArea();
                //cityName="Turkey"+" "+"Konya"+"/"+"Karatay"+"\n"+"("+ " 37,871540"+" - "+ "32.498914"+")";
                cityName = country +"/"+district+"/"+"\n"+"("+lat+")"+"-"+log+")";


                // getAirQuality(lat,log);
                // city referans alınacak

            }


        }
        catch (IOException e){
            e.printStackTrace();
        }
        txtSehir.setText(String.valueOf(cityName));




    }

    private void getAirQuality(double lat, double location) {
    }

    public void getLocation(View view) {


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        onLocationChanged(location);


    }
    //konum bilgisi veren sensörün durumu değiştiğinde
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getActivity(),"enable provider",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(),"disable provider",Toast.LENGTH_SHORT).show();

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}
