package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class updatepartinfo extends AppCompatActivity {
    EditText family,partname,partnumber,model,year,desc,price;
String id;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepartinfo);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        family=findViewById(R.id.family);
        partname=findViewById(R.id.partname);
        partnumber=findViewById(R.id.partnumber);
        model=findViewById(R.id.model);
        year=findViewById(R.id.year);
        desc=findViewById(R.id.desc);
        price=findViewById(R.id.price);
        queue = Volley.newRequestQueue(this);
        filltext();



    }

    private void filltext() {
        String url = "http://10.0.2.2:84/graduation_project/getpartbyid.php?id=" + id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> part=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        price.setText(obj.getString("price"));
                        family.setText(obj.getString("family"));
                        partname.setText(obj.getString("partname"));
                        partnumber.setText(obj.getString("partnumber"));
                        model.setText(obj.getString("model"));
                        year.setText(obj.getString("year"));
                        desc.setText(obj.getString("description"));



                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(updatepartinfo.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }

    public void update(View view) {
        String url = "http://10.0.2.2:84/graduation_project/updatepartinfo.php?id=" + id+"&&family="+family.getText().toString()
                +"&&partnumber="+partnumber.getText().toString()+"&&partname="+partname.getText().toString()+"&&model="+model.getText().toString()
                +"&&year="+year.getText().toString()+"&&description="+desc.getText().toString()+"&&price="+price.getText().toString();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);




                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(updatepartinfo.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }
}