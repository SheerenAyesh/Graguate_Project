package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
TextInputLayout username,password;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        queue = Volley.newRequestQueue(this);




    }

    public void login(View view) {
       String user=username.getEditText().getText().toString();
       String pass=password.getEditText().getText().toString();


        chicklogin(user,pass);

    }
    public void openmainpage(String user){
        Intent intent = new Intent(this ,MainActivity.class);
         intent.putExtra("username",user);
        startActivity(intent);
    }

    public void chicklogin(String user, String pass) {

        String url = "http://10.0.2.2:84/graduation_project/login.php";
        RequestQueue queue = Volley.newRequestQueue(login.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(login.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    if(jsonObject.getString("message").equals("true")){
                        openmainpage(user);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(login.this,
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


                params.put("username", user);
                params.put("password", pass);



                return params;
            }
        };

        queue.add(request);


    }

}