package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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
TextView textView;
CheckBox check;
String uname;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private static final  String NAME = "NAME"  ;
    private static final String PASS="PASS";
    private  static final String FLAG ="FLAG";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        textView = findViewById(R.id.textView);
        queue = Volley.newRequestQueue(this);



        check=findViewById(R.id.check);
        prefs= PreferenceManager.getDefaultSharedPreferences(this );
        editor=prefs.edit();
        boolean flag=prefs.getBoolean(FLAG,false);
        if(flag){
            String name=prefs.getString(NAME,"");
            String pass=prefs.getString(PASS,"");
            username.getEditText().setText(name);
            password.getEditText().setText(pass);
            check.setChecked(true);
        }


        textView.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(login.this,confpass.class);
                startActivity(intent);
            }
        });
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }


    public void login(View view) {
       String user=username.getEditText().getText().toString();

       String pass=password.getEditText().getText().toString();
        if(check.isChecked()){
            editor.putString(NAME,user);
            editor.putString(PASS,pass);
            editor.putBoolean(FLAG,true);
            editor.commit();

        }


        chicklogin(user,pass);

    }
    public void openmainpage(String user,String type){

        if(type.equals("user")){
        Intent intent = new Intent(this ,homepageuser.class);
         intent.putExtra("username",user);
        startActivity(intent);}
        else {
            Intent intent = new Intent(this ,homepagestore.class);
            intent.putExtra("username",user);
            startActivity(intent);

        }
    }

    public void checkuserorstor(String user){
        String url = "http://10.0.2.2:84/graduation_project/gettype.php";
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


                    openmainpage(user,jsonObject.getString("type"));


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
//            request.retryPolicy = object : RetryPolicy {
//                override fun getCurrentTimeout(): Int {
//                    return 20000
//                }
//
//                override fun getCurrentRetryCount(): Int {
//                    return 20000
//                }
//
//                @Throws(VolleyError::class)
//                override fun retry(error: VolleyError) {
//
//                }
//            }
        }) {
            @Override
            public String getBodyContentType() {

                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();


                params.put("username", user);




                return params;
            }
        };

        queue.add(request);



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
                        checkuserorstor(user);
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