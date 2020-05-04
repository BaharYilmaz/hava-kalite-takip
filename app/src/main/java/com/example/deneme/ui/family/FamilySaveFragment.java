package com.example.deneme.ui.family;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.deneme.R;
import com.example.deneme.model.City.CityList;
import com.example.deneme.model.User.Family;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FamilySaveFragment extends Fragment  {
    TextView txtAdres,txtNameInvalid;
    Button btnAdd;
    EditText etAd;
    View root;
    Spinner spinner;
    String isim,sehir;

    int REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

         root = inflater.inflate(R.layout.fragment_family_save, container, false);

        init();
       CityList cityList=  readJson();
        List<String> spinnerData = new ArrayList<>();
        for(int i=0;i<cityList.getCityDetail().size();i++){
            spinnerData.add(cityList.getCityDetail().get(i).getName());}
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, spinnerData);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isim=etAd.getText().toString();
               sehir=spinner.getSelectedItem().toString();

                if(!isim.isEmpty())
                {
                    txtNameInvalid.setVisibility(View.GONE);
                    etAd.setBackgroundResource(R.drawable.custom_border);
                    //kaydetme işlemi
                    List<Family> familyList = new ArrayList<>();//
                    Family family=new Family(isim,sehir);

                    List<Family> returnList=getSharedPref();

                    if(returnList!=null){
                        familyList=returnList;
                    }
                    familyList.add(family);
                    setSharedPref(familyList);
                    setAlertDialog(getActivity());

                }
                else{
                    txtNameInvalid.setVisibility(View.VISIBLE);
                    etAd.setBackgroundResource(R.drawable.custom_border_error);
                }
            }
        });


        //
//        Places.initialize(getContext(),"AIzaSyDEINldDoFMqB9oQ67JKSY3CFHthifJbxM");
//
//        etPlace.setFocusable(false);
//        etPlace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
//
//                Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
//                        fieldList).build(getActivity());
//
//                //start activity result
//                startActivityForResult(intent,REQUEST_CODE);
//            }
//        });
        return root;
    }

    private void setAlertDialog(Context context) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        alertDialog
                .setMessage("Kişi başarıyla eklendi.")
                .setCancelable(false)
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FamilyFragment familyFragment = new FamilyFragment();
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, familyFragment)
                                .commit();
                    }

                }).show();
    }

    private void setSharedPref(List<Family> familyList) {

        SharedPreferences shared_preferences = getActivity().getSharedPreferences("Family",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared_preferences.edit();
        Gson gson = new Gson();
        String jsonSet = gson.toJson(familyList);
        editor.putString("key", jsonSet);
        editor.commit();

    }
    private List<Family> getSharedPref() {

        SharedPreferences shared_preferences = getActivity().getSharedPreferences("Family",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = shared_preferences.getString("key", "");
        List<Family> familyGet = gson.fromJson(json,new TypeToken<ArrayList<Family>>(){}.getType());
        return familyGet;

    }

    private CityList  readJson() {
        CityList cityList = new CityList();
        try {
            //Load File
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.citys)));
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null; ) {
                jsonBuilder.append(line).append("\n");
            }
            Gson gson = new Gson(); //json’u parse etmek için Gson kütüphanesini kullanıyoruz
            cityList = gson.fromJson(jsonBuilder.toString(),CityList.class);
        } catch (FileNotFoundException e) {
            Log.e("jsonFile", "file not found");
        } catch (IOException e) {
            Log.e("jsonFile", "ioerror");
        }
        return cityList;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==REQUEST_CODE&&resultCode==RESULT_OK){
//            Place place=Autocomplete.getPlaceFromIntent(data);
//            etPlace.setText(place.getAddress());
//            txtPlaceName.setText(String.format("Locality name : %s",place.getName()));
//            //txtPlaceName.setText(String.valueOf(place.getLatLng()));
//        }
//        else if(resultCode== AutocompleteActivity.RESULT_ERROR){
//            Status status =Autocomplete.getStatusFromIntent(data);
//            Toast.makeText(getActivity(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }

    private void init() {
        btnAdd = root.findViewById(R.id.btnAdd);
        etAd = root.findViewById(R.id.etAd);
        spinner = root.findViewById(R.id.spnCity);
        txtNameInvalid = root.findViewById(R.id.txtNameInvalid);

        txtNameInvalid.setVisibility(View.GONE);

    }


}


