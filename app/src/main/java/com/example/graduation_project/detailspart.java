package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class detailspart extends AppCompatActivity {
TextView partname,partnumber,price,username,desc,model,note;
String userReqPart,useremail,usernumber,usercity;
String partowneremail,partownernumber;
String path;
String id;
Button forguest,signup,login;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailspart);
        partname=findViewById(R.id.partname);
        partnumber=findViewById(R.id.partnumber);
        price=findViewById(R.id.price);
        username=findViewById(R.id.username);
        desc=findViewById(R.id.desc);
        model=findViewById(R.id.model);
        forguest=findViewById(R.id.forguest);
        note=findViewById(R.id.note);
        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);
        queue = Volley.newRequestQueue(this);

        FillTextFromIntent();
if(userReqPart.equals("guest")){
    forguest.setVisibility(View.INVISIBLE);
}
if(!userReqPart.equals("guest")){
    note.setVisibility(View.INVISIBLE);
    signup.setVisibility(View.INVISIBLE);
    login.setVisibility(View.INVISIBLE);
}

    }

    public void FillTextFromIntent() {
        Intent intent=getIntent();
        partnumber.setText(intent.getStringExtra("partnumber"));
        partname.setText(intent.getStringExtra("partname"));
        price.setText(intent.getStringExtra("price"));
        username.setText(intent.getStringExtra("partowner"));
        desc.setText(intent.getStringExtra("description"));
        model.setText(intent.getStringExtra("model"));
        userReqPart=intent.getStringExtra("username");
        path=intent.getStringExtra("path");
        id=intent.getStringExtra("id");
    }

    public void resPartOnClick(View view) {
        getuserdata();
        getpartownerdata();


        String url = "http://10.0.2.2:84/graduation_project/add_part_order.php";
        RequestQueue queue = Volley.newRequestQueue(detailspart.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(detailspart.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(detailspart.this,
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
                params.put("idFromOrg",id);
                params.put("partowner", username.getText().toString());
                params.put("username", userReqPart);
                params.put("partname", partname.getText().toString());
                params.put("partnumber", partnumber.getText().toString());
                params.put("useremail", useremail);
                params.put("price", price.getText().toString());
                String path2=path.substring(46,path.length());
                params.put("path",path2);
                params.put("emailpartowner", partowneremail);
                params.put("status", "inProgress");
                params.put("description", desc.getText().toString());
                params.put("model", model.getText().toString());
                params.put("partownernumber", partownernumber);
                params.put("usernumber", usernumber);
                params.put("distance", "20");
                params.put("usercity", usercity);


                return params;
            }
        };
        queue.add(request);







    }

    private void getpartownerdata() {
        String url = "http://10.0.2.2:84/graduation_project/get_all_mec_by_username2.php?username=" + username.getText().toString();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        partowneremail=obj.getString("email");
                        partownernumber=obj.getString("phonenumber");



                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(detailspart.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);




    }




    private void getuserdata() {
        String url = "http://10.0.2.2:84/graduation_project/get_all_mec_by_username2.php?username=" + userReqPart;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                         useremail=obj.getString("email");
                         usernumber=obj.getString("phonenumber");
                          usercity=obj.getString("city");


                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(detailspart.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }
    public void user(View view) {
    }

    public void signup(View view) {
        Intent intent=new Intent(this,signup.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent=new Intent(this,login.class);
        startActivity(intent);
    }
//    public void cart(View view) {
//        Intent intent = new Intent(this ,cart.class);
//        intent.putExtra("username",store);
//        startActivity(intent);
//    }
//    public void home(View view) {
//        Intent intent = new Intent(this ,homepagestore.class);
//        intent.putExtra("username",store);
//        startActivity(intent);
//    }


}