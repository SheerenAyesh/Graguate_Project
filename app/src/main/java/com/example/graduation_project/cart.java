package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;

public class cart extends AppCompatActivity {
ListView meclist;
String store;
    private RequestQueue queue;
    String []arr;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        meclist=findViewById(R.id.meclist);
        Intent intent=getIntent();
        store= intent.getStringExtra("username");
        queue = Volley.newRequestQueue(this);
        fillmec();
    }

    public void fillmec() {

        String url = "http://10.0.2.2:84/graduation_project/mec_req_by_user.php?username="+store;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> orders = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                      String s=obj.getString("mecname")+"  "+obj.getString("mecphone");


                        orders.add(s);

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                arr = new String[orders.size()];
                arr = orders.toArray(arr);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        cart.this, android.R.layout.simple_list_item_1,
                        arr);
                meclist.setAdapter(adapter);

            }
////////////////////////////////////////////////////////////////////////////





            ///////////////////////////////////////////////////////////////
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(cart.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    }
