package com.example.deneme.ui.family;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.deneme.R;
import com.example.deneme.model.User.CustomAdapter;
import com.example.deneme.model.User.Family;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
        fillArrayList(families);

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

    private void fillArrayList(ArrayList<Family> _familyList) {
        List<Family> familyList= getSharedPref();
        for (Family family : familyList) {
            _familyList.add(family);
        }

    }


    private List<Family> getSharedPref() {

        SharedPreferences shared_preferences = getActivity().getSharedPreferences("Family File", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = shared_preferences.getString("key_family", "");
        List<Family> familyGet = gson.fromJson(json, new TypeToken<ArrayList<Family>>() {
        }.getType());
        return familyGet;

    }


    private void init() {
        btnPersonAdd = root.findViewById(R.id.btnPersonAdd);
        listAile = root.findViewById(R.id.listAile);


    }

}


