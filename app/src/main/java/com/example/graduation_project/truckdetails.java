package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class truckdetails extends AppCompatActivity {
    TextView truckname,truckphone,truckdes,truckemail;
    private RequestQueue queue;
    String s,lat,log,storereq;
    String username,city,email,phonenumber,dec;
    double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truckdetails);
        truckemail=findViewById(R.id.truckemail);
        truckname=findViewById(R.id.truckname);
        truckdes=findViewById(R.id.truckdes);
        truckphone=findViewById(R.id.truckphone);
        Intent intent=getIntent();
        s=intent.getStringExtra("username");
        lat=intent.getStringExtra("latitude");
        log=intent.getStringExtra("longitude");
        storereq=intent.getStringExtra("userreq");
        truckname.setText(s);
        queue = Volley.newRequestQueue(this);
        fill_text1();
        fill_text2();

    }


    public void fill_text2()
    {
        String url = "http://10.0.2.2:84/graduation_project/get_all_truck_by_username.php?username=" + s;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
//                        mecdes.setText(obj.getString("latitude"));
                        String x, y;
                        x= obj.getString("latitude");
                        y= obj.getString("longitude");
                        double log1 ,log2,lat1,lat2;
                        log1 = Math.toRadians(Double.parseDouble(log));
                        log2 = Math.toRadians(Double.parseDouble(y));
                        lat1 = Math.toRadians(Double.parseDouble(lat));
                        lat2 = Math.toRadians(Double.parseDouble(x));
                        double dlon = log2 - log1;
                        double dlat = lat2 - lat1;
                        double a = Math.pow(Math.sin(dlat / 2), 2)
                                + Math.cos(lat1) * Math.cos(lat2)
                                * Math.pow(Math.sin(dlon / 2),2);

                        double c = 2 * Math.asin(Math.sqrt(a));

                        double r = 6371;
                        result = c*r;
                        truckdes.setText(String.valueOf(result));

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(truckdetails.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }
    public void fill_text1()
    {

        String url = "http://10.0.2.2:84/graduation_project/get_all_truck_by_username2.php?username=" + s;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        truckphone.setText(obj.getString("phonenumber"));
                        truckemail.setText(obj.getString("email"));


                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(truckdetails.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }



    public void senddata(View view) {
    }
}