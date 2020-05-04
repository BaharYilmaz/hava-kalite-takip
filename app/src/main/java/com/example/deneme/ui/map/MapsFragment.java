package com.example.deneme.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.deneme.R;
import com.example.deneme.ui.rapor.RaporFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    MapView mMapView;
    TextView txtAdres;
    Button btnGo,btnOnayla;
    EditText etLocation;
    String adres;
    View root;
    private final static int REQUEST_lOCATION = 90;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

         root = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = null;
       //getMaps( mapFragment);



        init();

        final Button btn_MapType = root.findViewById(R.id.btn_Sat);
        btn_MapType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    btn_MapType.setText("NORMAL");
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    btn_MapType.setText("UYDU");
                }
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = etLocation.getText().toString();
                if (location != null && !location.equals("")) {
                    List<Address> adressList = null;
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        adressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = adressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Burası " + location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });
        btnOnayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // NavDirections action = new ActionOnlyNavDirections(R.id.action_mapsFragment_to_raporFragment);
            //  Navigation.findNavController(v).navigate(action);
//              fragmentInterface = (FragmentInterface) getActivity();
//               fragmentInterface.fragmentAc();
                Bundle bundle = new Bundle();
                bundle.putString("key",adres); // Put anything what you want

                RaporFragment fragment2 = new RaporFragment();
                fragment2.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment2)
                        .commit();
            }
        });
        return root;
    }

    private void init() {
        btnGo = root.findViewById(R.id.btn_Go);
        btnOnayla = root.findViewById(R.id.btnOnayla);
        txtAdres = root.findViewById(R.id.txtAdres);
        etLocation = root.findViewById(R.id.et_location);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final List<Address>[] addresses = new List[]{null};
        final Marker marker = null;
        final double[] lat = {0};
        final double[] lng = {0};
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng position) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(position));
                lat[0] = position.latitude;
                lng[0] = position.longitude;
                if (marker != null) {
                    marker.remove();
                }

                //geocoder verilen enlem ve boylamdaki adresi bir array olarak döndürmektedir
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                try {
                    addresses[0] = geocoder.getFromLocation(lat[0], lng[0], 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adres = addresses[0].get(0).getAddressLine(0);
                txtAdres.setText("Adres : " +adres);
                //Toast.makeText(getActivity(), "Adres: " + adres, Toast.LENGTH_SHORT).show();
            }
        });
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_lOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_lOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(getActivity(), "Kullanıcı konum iznini vermedi", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void getMaps( SupportMapFragment mapFragment) {
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView=view.findViewById(R.id.map);
        if(mMapView!=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }
}


