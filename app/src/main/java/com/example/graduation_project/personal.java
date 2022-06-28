package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
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

import java.util.HashMap;
import java.util.Map;

public class personal extends AppCompatActivity {
    private RadioButton user,store;
    String uname="";
    private String user1 , store1;
    private RequestQueue queue;
    EditText email,firstname,lastname,pass,city,street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        Intent intent=getIntent();
        uname=intent.getStringExtra("username");
        queue = Volley.newRequestQueue(this);
        user=findViewById(R.id.user);
        store=findViewById(R.id.store);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        pass=findViewById(R.id.pass);
        email=findViewById(R.id.email);
        street=findViewById(R.id.street);
        city=findViewById(R.id.city);
        fillinfo(uname);
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.store:
                if (checked)
                    store1 = "store";
                break;
            case R.id.user:
                if (checked)
                    user1 = "user";
                break;

        }

    }

    public void fillinfo(String uname) {
        String url = "http://10.0.2.2:84/graduation_project/fillinfo.php?username=" + uname;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        pass.setText(obj.getString("password"));
                        firstname.setText(obj.getString("firstname"));
                        lastname.setText(obj.getString("lastname"));
                        email.setText(obj.getString("email"));
                        city.setText(obj.getString("city"));
                        street.setText(obj.getString("street"));




                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(personal.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    public void logout(View view) {
        Intent intent = new Intent(this ,login.class);

        startActivity(intent);
    }

    public void updateuser(View view) {
        String s="";
        if(store1!=null){
            s=store1;
        }else{
            s=user1;
        }
        String url = "http://10.0.2.2:84/graduation_project/updateuserinfo.php?username=" + uname+"&&password="+pass.getText().toString()
                +"&&firstname="+firstname.getText().toString()+"&&lastname="+lastname.getText().toString()+
                "&&email="+email.getText().toString()+"&&city="+city.getText().toString()+"&&street="+street.getText().toString()+
                "&&type="+s;

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



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        queue.add(request);
    }

}