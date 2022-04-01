package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class mecdetails extends AppCompatActivity {
    TextView mecname,mecphone,mecstore,mecdes,mecemail;
    private RequestQueue queue;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mecdetails);
        mecname=findViewById(R.id.mecname);
        mecphone=findViewById(R.id.mecphone);
        mecdes=findViewById(R.id.mecdes);
        mecstore=findViewById(R.id.mecstore);
        mecemail=findViewById(R.id.mecemail);
        Intent intent=getIntent();
         s=intent.getStringExtra("username");
        mecname.setText(s);
        queue = Volley.newRequestQueue(this);
        fill_text1();
        fill_text2();

    }

    public void fill_text2()
    {
        String url = "http://10.0.2.2:84/graduation_project/get_all_mec_by_username.php?username=" + s;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        mecdes.setText(obj.getString("latitude"));
                        mecstore.setText(obj.getString("mecname"));


                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mecdetails.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }

    public void fill_text1()
    {

        String url = "http://10.0.2.2:84/graduation_project/get_all_mec_by_username2.php?username=" + s;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        mecphone.setText(obj.getString("phonenumber"));
                        mecemail.setText(obj.getString("email"));


                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mecdetails.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }
}