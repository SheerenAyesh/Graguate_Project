package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class confpass extends AppCompatActivity {
EditText username,conf,newpass,confnewpass;
TextView errorusername,errorcode;
    private RequestQueue queue;
    int forusername=0;
    String security="";
    LinearLayout change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confpass);
        username=findViewById(R.id.username);
        conf=findViewById(R.id.conf);
        newpass=findViewById(R.id.newpass);
       confnewpass=findViewById(R.id.confnewpass);
        errorusername=findViewById(R.id.errorusername);
        errorcode=findViewById(R.id.errorcode);
        queue = Volley.newRequestQueue(this);
        change=findViewById(R.id.change);
        change.setVisibility(View.INVISIBLE);


    }

    public void changepass(View view) {
              if(newpass.getText().toString().equals(confnewpass.getText().toString())) {
                  String url = "http://10.0.2.2:84/graduation_project/updatepassword.php?username=" +
                          username.getText().toString() + "&&password=" + newpass.getText().toString();

                  JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                          null, new Response.Listener<JSONArray>() {
                      @Override
                      public void onResponse(JSONArray response) {

                          for (int i = 0; i < response.length(); i++) {
                              try {
                                  JSONObject obj = response.getJSONObject(i);

                                  if (obj.getString("message").equals("done")){

                                  }



                              } catch (JSONException exception) {
                                  Log.d("Error", exception.toString());
                              }
                          }


                      }
                  }, new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {

                          Toast.makeText(confpass.this, error.toString(),
                                  Toast.LENGTH_SHORT).show();
                      }
                  });
                  go_to_login();
                  queue.add(request);

              }
              else{
                  Toast.makeText(confpass.this,"تأكد من تطابق كلمات المرور",Toast.LENGTH_LONG).show();
              }
    }

    private void go_to_login() {
        Intent intent=new Intent(this,login.class);
        startActivity(intent);
    }

    public void confusername(View view) {
        String url = "http://10.0.2.2:84/graduation_project/usernameforconfpass.php?username=" + username.getText().toString();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        security=obj.getString("security");
                        ++forusername;



                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                if(forusername>0){

                }
                else{
                    errorusername.setText("اسم المستخدم غير موجود ...يرجى التحقق");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(confpass.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    private void ensure() {



    }

    public void confcode(View view) {

        if(security.equals(conf.getText().toString())){
            change.setVisibility(View.VISIBLE);
        }
        else{
            errorcode.setText("الاجابة خاطئه , حاول مرة اخرى");

        }
    }
}