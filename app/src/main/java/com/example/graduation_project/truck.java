package com.example.graduation_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
import android.widget.Button;
import android.widget.ListView;
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

public class truck extends AppCompatActivity {
String username ,lat,log;
Button button2;
String []arr;
ListView listtruck;
    private RequestQueue queue;
    Button truck;
    private LocationRequest locationRequest;
    String user="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck);
        listtruck=findViewById(R.id.listtruck);
        queue = Volley.newRequestQueue(this);
        truck=findViewById(R.id.truck);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        button2=findViewById(R.id.button2);
         user=intent.getStringExtra("type");
        if(user.equals("user")){
            truck.setVisibility(View.INVISIBLE);
        }
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCurrentLocation();
                gettruck();
            }
        });
    }

    public void gettruck(){
        String url = "http://10.0.2.2:84/graduation_project/get_all_truck.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> truckown = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        String s=obj.getString("username");

                        truckown.add(s);
                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                arr = new String[truckown.size()];
                arr = truckown.toArray(arr);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        truck.this, android.R.layout.simple_list_item_1,
                        arr);
                listtruck.setAdapter(adapter);


                AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {
                        for(int i=0;i<arr.length;i++){
                            if(position == i){
                                go_to_det(i);


                            }}
                    }
                };
                listtruck.setOnItemClickListener(itemClickListener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(truck.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);



    }
    public void go_to_det(int i) {
        Intent intent = new Intent(this ,truckdetails.class);
        intent.putExtra("username",arr[i]);
        intent.putExtra("latitude",lat);
        intent.putExtra("longitude",log);
        intent.putExtra("userreq",username);
        intent.putExtra("type",user);

        startActivity(intent);
    }


    public void nearest(View view) {


    }

    public void truuck(View view) {
        Intent intent = new Intent(this ,truckaccount.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void home(View view) {
        if(!user.equals("user")){
        Intent intent = new Intent(this ,homepagestore.class);
        intent.putExtra("username",username);
        startActivity(intent);}
        else{
            Intent intent = new Intent(this ,homepageuser.class);
            intent.putExtra("username",username);
            startActivity(intent);

        }
    }

    public void orders(View view) {
    }

    public void user(View view) {
    }



    private void getCurrentLocation () {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(truck.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(truck.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(truck.this)
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
                    Toast.makeText(truck.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(truck.this, 2);
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
    public void orders_mec(View view) {
        Intent intent = new Intent(this ,order.class);
        intent.putExtra("username",username);
        startActivity(intent);

    }
    public void orders_truck(View view) {
        Intent intent = new Intent(this ,order_truck.class);
        intent.putExtra("username",username);
        startActivity(intent);

    }


    public void cart(View view) {
        Intent intent = new Intent(this ,cart.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

}