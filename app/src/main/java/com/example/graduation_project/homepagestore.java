package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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

public class homepagestore extends AppCompatActivity {
String store;
ImageButton ordertruck,ordermec;
    private RequestQueue queue;
    int x=0,x1=0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepagestore);
        Intent intent=getIntent();
         store=intent.getStringExtra("username");
         ordermec=findViewById(R.id.ordermec);
         ordertruck=findViewById(R.id.ordertruck);
        queue = Volley.newRequestQueue(this);
         check_truck();
         check_mec();
    }

    private void check_mec() {
        String url = "http://10.0.2.2:84/graduation_project/get_all_mec_by_username.php?username=" + store;


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    x++;
                    try {
                        JSONObject obj = response.getJSONObject(i);




                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                if(x==0){
                    ordermec.setVisibility(View.INVISIBLE);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(homepagestore.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    private void check_truck() {
        String url = "http://10.0.2.2:84/graduation_project/get_all_truck_by_username.php?username=" + store;


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    x1++;
                    try {
                        JSONObject obj = response.getJSONObject(i);




                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                if(x1==0){
                    ordertruck.setVisibility(View.INVISIBLE);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(homepagestore.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    public void homestore(View view) {
    }

    public void mechanic(View view) {
        Intent intent = new Intent(this ,mechanic.class);
        intent.putExtra("username",store);
        startActivity(intent);

    }

    public void truck(View view) {
        Intent intent = new Intent(this ,truck.class);
        intent.putExtra("username",store);
        startActivity(intent);

    }

    public void searchPart(View view) {
        Intent intent = new Intent(this ,searchpart.class);
        intent.putExtra("username",store);
        startActivity(intent);
    }

    public void orders_mec(View view) {
        Intent intent = new Intent(this ,order.class);
        intent.putExtra("username",store);
        startActivity(intent);

    }
    public void orders_truck(View view) {
        Intent intent = new Intent(this ,order_truck.class);
        intent.putExtra("username",store);
        startActivity(intent);

    }

    public void home(View view) {
        Intent intent = new Intent(this ,homepagestore.class);
        intent.putExtra("username",store);
        startActivity(intent);
    }

    public void user(View view) {
    }

    public void cart(View view) {
        Intent intent = new Intent(this ,cart.class);
        intent.putExtra("username",store);
        startActivity(intent);
    }
}