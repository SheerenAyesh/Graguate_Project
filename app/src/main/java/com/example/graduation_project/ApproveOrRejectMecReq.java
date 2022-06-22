package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApproveOrRejectMecReq extends AppCompatActivity {
    TextView res;
    String username, id,idfromorg;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_or_reject_mec_req);
        res=findViewById(R.id.res);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        idfromorg=intent.getStringExtra("idfromorg");
        queue = Volley.newRequestQueue(this);
    }

    public void reject(View view) {
        String url = "http://10.0.2.2:84/graduation_project/Reject_mec.php?id=" + id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);



                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                //res.setText("تم حذف الطلب بنجاح");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        queue.add(request);


        res.setText("تم رفض الطلب بنجاح".trim());
    }


    public void approve(View view) {

        String url = "http://10.0.2.2:84/graduation_project/approve_mec.php?id=" + id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);



                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                //res.setText("تم حذف الطلب بنجاح");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        queue.add(request);


        res.setText("تم قبول الطلب بنجاح".trim());
        changestatus();

    }
    private void changestatus() {

        String url = "http://10.0.2.2:84/graduation_project/change_status_when_approve_mec.php?id=" + idfromorg;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);



                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                //res.setText("تم حذف الطلب بنجاح");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        queue.add(request);



    }

    public void home(View view) {
    }

    public void cart(View view) {
    }

    public void orders_mec(View view) {
    }

    public void orders_truck(View view) {
    }

    public void user(View view) {
    }
}