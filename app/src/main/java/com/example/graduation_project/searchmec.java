package com.example.graduation_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class searchmec extends AppCompatActivity {
Spinner spinner,spinner2;
String username,type;
String []mecname;
    String []city;
    String []mecname2;
    String []mecname3;
    private RequestQueue queue;
    AutoCompleteTextView mecnames;
    ArrayAdapter<String> adapter2;
    String mecnamess;
    ListView listt;
    String lat,log;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmec);
        getCurrentLocation();
        Intent intent=getIntent();


        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        username=intent.getStringExtra("username");
        type=intent.getStringExtra("type");
        spinner=findViewById(R.id.spinner);
        spinner2=findViewById(R.id.spinner2);
     //   listt=findViewById(R.id.listt);
        queue = Volley.newRequestQueue(this);
        mecnames=findViewById(R.id.mecnames);
        fillspinner();
        spinner2.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        String item = (String) parent.getItemAtPosition(pos);
                        mecnames.setText(item);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        mecnames.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                String   familyselected =(String) parent.getItemAtPosition(position);
               mecnamess=familyselected;

            }
        });





    }

    private void fillspinner() {
        String url = "http://10.0.2.2:84/graduation_project/get_all_mec.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> mec = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        String s=obj.getString("username");
                        mec.add(s);
                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                mecname = new String[mec.size()];
                mecname = mec.toArray(mecname);


                getcity();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(searchmec.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });



        queue.add(request);


    }

    private void getcity() {
        String url = "http://10.0.2.2:84/graduation_project/get_all_actor.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> City = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                       for(int j=0;j<mecname.length;j++){
                           if(obj.getString("username").equals(mecname[j])){
                               City.add(obj.getString("city"));
                           }
                       }
                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                ArrayList<String> citynew=new ArrayList<>();
                for(int i=0;i<City.size();i++){
                    if(!citynew.contains(City.get(i))){
                        citynew.add(City.get(i));
                    }
                }
                city = new String[citynew.size()];
                city = citynew.toArray(city);

                ArrayAdapter<String> adapter= new ArrayAdapter<>(searchmec.this , android.R.layout.simple_spinner_item,city);
                spinner.setAdapter(adapter);





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(searchmec.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(request);



    }


    public void fillspinner(View view) {
        String c=spinner.getSelectedItem().toString();
        String url = "http://10.0.2.2:84/graduation_project/get_all_actor_by_city.php?city=" + c;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
ArrayList<String>user=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                             user.add(obj.getString("username"));


                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                mecname2 = new String[user.size()];
                mecname2 = user.toArray(mecname2);

             getmecname();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(searchmec.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);




    }

    private void getmecname() {
        String url = "http://10.0.2.2:84/graduation_project/get_all_mec.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> mecc = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        for(int j=0;j<mecname2.length;j++){
                            if(mecname2[j].equals(obj.getString("username"))){
                                mecc.add(mecname2[j]);
                            }
                        }

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                mecname3 = new String[mecc.size()];
                mecname3 = mecc.toArray(mecname3);
                ArrayAdapter<String> adapter= new ArrayAdapter<>(searchmec.this , android.R.layout.simple_spinner_item,mecname3);
                spinner2.setAdapter(adapter);

                adapter2 = new ArrayAdapter<String>(searchmec.this,android.R.layout.select_dialog_item, mecname3);

                mecnames.setThreshold(1);
                mecnames.setAdapter(adapter2);





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(searchmec.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });



        queue.add(request);


    }

    public void mecdet(View view) {

        Intent intent = new Intent(this ,mecdetails.class);
        intent.putExtra("username",mecnamess);
        intent.putExtra("latitude",lat);
        intent.putExtra("longitude",log);
        intent.putExtra("userreq",username);
        intent.putExtra("type",type);

        startActivity(intent);
    }
    /////////////////////////////////////////
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }

    private void getCurrentLocation () {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(searchmec.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(searchmec.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(searchmec.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() > 0) {

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();
                                        lat=String.valueOf(latitude);
                                        log=String.valueOf(longitude);
//                                        username.getEditText().setText(lat+"  "+log);
//                                        System.out.println("///////////////////////////////////////");
//                                        System.out.println(lat);
//                                        System.out.println(log);


                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void turnOnGPS () {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(searchmec.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(searchmec.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled () {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }
}