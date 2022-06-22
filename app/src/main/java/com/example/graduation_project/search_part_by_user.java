package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class search_part_by_user extends AppCompatActivity {
//Spinner spinner;
//EditText searchtype;
//Button btn;
    AutoCompleteTextView family;
    String []model;
String username;
private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_part_by_user);
      //  spinner=findViewById(R.id.spinner);
     //  String []s={"اختر :","البحث بواسطة اسم القطعة","البحث بواسطة رقم القطعة","البحث بواسطة الموديل"};
       // ArrayAdapter<String> adapter= new ArrayAdapter<>(this , android.R.layout.simple_spinner_item,s);
       // spinner.setAdapter(adapter);
        //searchtype=findViewById(R.id.searchtype);
        //btn=findViewById(R.id.btn);
        //searchtype.setVisibility(View.INVISIBLE);
        //btn.setVisibility(View.INVISIBLE);
        queue = Volley.newRequestQueue(this);
        family=findViewById(R.id.family);


        Intent intent=getIntent();
        username=intent.getStringExtra("username");

       getmodel();



    }

    public void getmodel() {

        String url = "http://10.0.2.2:84/graduation_project/get_model.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String>model2=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        model2.add(obj.getString("model"));


                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                model=new String[model2.size()];
                model=model2.toArray(model);
                setauto();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(search_part_by_user.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }

    private void setauto() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, model);

        family.setThreshold(1);
        family.setAdapter(adapter);
    }


}