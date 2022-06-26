package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class signup extends AppCompatActivity {
    private TextInputLayout firstname,lastname,password,email,username,phonenumber,street,city,qu;
    private RadioButton jawwal, palpay, cash,user,store,male,female;
    private RadioGroup payment,type,gender;
    private String user1 , store1 ,jawwal1, palpay1, cash1,male1,female1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        password=findViewById(R.id.password);
        qu=findViewById(R.id.qu);
        email=findViewById(R.id.email);
        username=findViewById(R.id.username);
        phonenumber=findViewById(R.id.phonenumber);
        gender=findViewById(R.id.gender);
        street=findViewById(R.id.street);
        city=findViewById(R.id.city);
        jawwal=findViewById(R.id.jawwal);
        palpay=findViewById(R.id.palpay);
        cash=findViewById(R.id.cash);
        user=findViewById(R.id.user);
        store=findViewById(R.id.store);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);


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
    public void onRadioButtonClicked2(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.jawwal:
                if (checked)
                    jawwal1 = "jawwal";
                    break;
            case R.id.palpay:
                if (checked)
                    palpay1 = "palpay";
                    break;
            case R.id.cash:
                if (checked)
                    cash1 = "cash";
                    break;
        }
    }
    public void onRadioButtonClickedgender(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.male:
                if (checked)
                    male1 = "male";
                break;
            case R.id.female:
                if (checked)
                    female1 = "female";
                break;
        }
    }
    public void create(View view) {
        String uname=username.getEditText().getText().toString();
        String fname=firstname.getEditText().getText().toString();
        String lname=lastname.getEditText().getText().toString();
        String pass=password.getEditText().getText().toString();
        String mail=email.getEditText().getText().toString();
        String phone=phonenumber.getEditText().getText().toString();
        String strt=street.getEditText().getText().toString();
        String cty=city.getEditText().getText().toString();

        checkusername(uname,fname,lname,pass,mail,phone,cty,strt,store1,user1,jawwal1,palpay1,cash1,male1,female1);
    }
    public void checkusername(String uname,String fname,String lname,String pass,String mail,String phone,String cty,String strt,String store1,String user1,String jawwal1,String palpay1,String cash1,String male1,String female1){
        String url = "http://10.0.2.2:84/graduation_project/checkusername.php";
        RequestQueue queue = Volley.newRequestQueue(signup.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    if(jsonObject.getString("message").equals("true")) {
                        signup(uname,fname,lname,pass,mail,phone,cty,strt,store1,user1,jawwal1,palpay1,cash1,male1,female1);
                    }
                    else
                        Toast.makeText(signup.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(signup.this,
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
                params.put("username", uname);
                return params;
            }
        };
        queue.add(request);
    }
    /////////////////////////////////
   public void go_to_another_page(String uname){
        if(user1!=null){
            Intent intent = new Intent(this ,homepageuser.class);
             intent.putExtra("username",uname);
            startActivity(intent);
        }
        else if(store1!=null){
            Intent intent = new Intent(this ,homepagestore.class);
            intent.putExtra("username",uname);
            startActivity(intent);
        }
   }
    ///////////////////////////////
    public void signup(String uname,String fname,String lname,String pass,String mail,String phone,String cty,String strt,String store1,String user1,String jawwal1,String palpay1,String cash1,String male1,String female1){
        String url = "http://10.0.2.2:84/graduation_project/signup.php";
        RequestQueue queue = Volley.newRequestQueue(signup.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(signup.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    go_to_another_page(uname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(signup.this,
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
                params.put("username", uname);
                params.put("password", pass);
               if(user1!= null)
                   params.put("type", user1);
               else
                   if(store1 !=null)
                       params.put("type", store1);
                params.put("email", mail);
                params.put("phonenumber", phone);
                params.put("city", cty);
                if(male1!= null)
                    params.put("gender", male1);
                else
                if(female1 !=null)
                    params.put("gender", female1);

                params.put("street", strt);

                if(jawwal1!= null)
                    params.put("payment", jawwal1);
                else
                if(palpay1 !=null)
                    params.put("payment", palpay1);
                else
                if(cash1 !=null)
                    params.put("payment", cash1);

                params.put("firstname", fname);
                params.put("lastname", lname);
                params.put("security", qu.getEditText().getText().toString());

                return params;
            }
        };
        queue.add(request);
    }
}
