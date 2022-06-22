package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addpart extends AppCompatActivity {
String username1;
TextView welcome;
TextView username,phonenumber,city,alarm;
EditText desc,price,partname,model,partnumber,family;
private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpart);
        queue = Volley.newRequestQueue(this);
        username=findViewById(R.id.username);
        phonenumber=findViewById(R.id.phonenumber);
        city=findViewById(R.id.city);
        desc=findViewById(R.id.desc);
        price=findViewById(R.id.price);
        partname=findViewById(R.id.partname);
        model=findViewById(R.id.model);
        partnumber=findViewById(R.id.partnumber);
        family=findViewById(R.id.family);
        Intent intent=getIntent();
        username1=intent.getStringExtra("username");
        welcome=findViewById(R.id.welcome);
        welcome.setText("اهلا "+username1+" في صفحة اضافة القطعة");
        username.setText(username1);
        alarm=findViewById(R.id.alarm);

        filltext();




    }

    public void inserttodatabase() {
        String url = "http://10.0.2.2:84/graduation_project/insertpart.php";
        RequestQueue queue = Volley.newRequestQueue(addpart.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(addpart.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(addpart.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {

                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username1);
                params.put("latitude", "12");
                params.put("longitude", "12");
                params.put("partname", partname.getText().toString());
                params.put("model", model.getText().toString());
                params.put("description", desc.getText().toString());
                params.put("price", price.getText().toString());


                return params;
            }
        };
        queue.add(request);


    }

    public void filltext() {
        String url = "http://10.0.2.2:84/graduation_project/get_info_foraddpart.php?username=" + username1;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        phonenumber.setText(obj.getString("phonenumber"));
                        city.setText(obj.getString("city"));


                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(addpart.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);




    }


    public void addpartonclick(View view) {
        inserttodatabase();
    }

    public void addimage(View view) {

        Intent intent = new Intent(this ,sheeren_add_image.class);
        if(!(partnumber.getText().toString().isEmpty()||partname.getText().toString().isEmpty()
        ||model.getText().toString().isEmpty()||desc.getText().toString().isEmpty()||price.getText().toString().isEmpty()||family.getText().toString().isEmpty())
                ){
        intent.putExtra("username",username1);
        intent.putExtra("partnumber",partnumber.getText().toString());
        intent.putExtra("latitude","12");
        intent.putExtra("longitude","12");
        intent.putExtra("partname",partname.getText().toString());
        intent.putExtra("model",model.getText().toString());
        intent.putExtra("description",desc.getText().toString());
        intent.putExtra("price",price.getText().toString());
        intent.putExtra("family",family.getText().toString());

        startActivity(intent);}
        else{
            alarm.setText("الرجاء ادخال جميع المعلومات المطلوبة !!");
        }

    }
}