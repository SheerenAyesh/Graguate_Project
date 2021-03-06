package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class truckdetails extends AppCompatActivity {
    TextView truckname, truckphone, truckdes, truckemail, truckweight,note;
    private RequestQueue queue;
    String s, lat, log, storereq,idFromOrg;
    String username, city, email, phonenumber, dec, type;
    Button senddata;
LinearLayout guest;
    double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truckdetails);
        truckemail = findViewById(R.id.truckemail);
        truckname = findViewById(R.id.truckname);
        truckdes = findViewById(R.id.truckdes);
        truckphone = findViewById(R.id.truckphone);
        truckweight = findViewById(R.id.truckweight);
        senddata=findViewById(R.id.senddata);
        note=findViewById(R.id.note);
        guest=findViewById(R.id.guest);
        Intent intent = getIntent();
        s = intent.getStringExtra("username");
        lat = intent.getStringExtra("latitude");
        log = intent.getStringExtra("longitude");
        storereq = intent.getStringExtra("userreq");
        type = intent.getStringExtra("type");

        truckname.setText(s);
        queue = Volley.newRequestQueue(this);
        fill_text1();
        fill_text2();
if(storereq.equals("guest")){
senddata.setVisibility(View.INVISIBLE);
}
        if(!storereq.equals("guest")){
           guest.setVisibility(View.INVISIBLE);
           note.setVisibility(View.INVISIBLE);
        }
    }


    public void fill_text2() {
        String url = "http://10.0.2.2:84/graduation_project/get_all_truck_by_username.php?username=" + s;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
//                        mecdes.setText(obj.getString("latitude"));
                        String x, y;
                        x = obj.getString("latitude");
                        y = obj.getString("longitude");
                        double log1, log2, lat1, lat2;
                        if(log!=null&&lat!=null) {
                            log1 = Math.toRadians(Double.parseDouble(log));
                            log2 = Math.toRadians(Double.parseDouble(y));
                            lat1 = Math.toRadians(Double.parseDouble(lat));
                            lat2 = Math.toRadians(Double.parseDouble(x));
                            double dlon = log2 - log1;
                            double dlat = lat2 - lat1;
                            double a = Math.pow(Math.sin(dlat / 2), 2)
                                    + Math.cos(lat1) * Math.cos(lat2)
                                    * Math.pow(Math.sin(dlon / 2), 2);

                            double c = 2 * Math.asin(Math.sqrt(a));

                            double r = 6371;

                        result = c * r;
                        truckdes.setText(String.valueOf(result));
                        truckweight.setText(obj.getString("truckweight"));}
                        else{
                            log1 = Math.toRadians(Double.parseDouble("18.5"));
                            log2 = Math.toRadians(Double.parseDouble(y));
                            lat1 = Math.toRadians(Double.parseDouble("17.5"));
                            lat2 = Math.toRadians(Double.parseDouble(x));
                            double dlon = log2 - log1;
                            double dlat = lat2 - lat1;
                            double a = Math.pow(Math.sin(dlat / 2), 2)
                                    + Math.cos(lat1) * Math.cos(lat2)
                                    * Math.pow(Math.sin(dlon / 2), 2);

                            double c = 2 * Math.asin(Math.sqrt(a));

                            double r = 6371;

                            result = c * r;
                            truckdes.setText(String.valueOf(result));
                            truckweight.setText(obj.getString("truckweight"));
                            idFromOrg=obj.getString("id");
                        }

                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(truckdetails.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }

    public void fill_text1() {

        String url = "http://10.0.2.2:84/graduation_project/get_all_truck_by_username2.php?username=" + s;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        truckphone.setText(obj.getString("phonenumber"));
                        truckemail.setText(obj.getString("email"));


                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(truckdetails.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }


    public void senddata(View view) {

        String url = "http://10.0.2.2:84/graduation_project/orderrequest.php?username=" + storereq;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        username = obj.getString("username");
                        city = obj.getString("city");
                        phonenumber = obj.getString("phonenumber");
                        email = obj.getString("email");


                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                go_to_order_page();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(truckdetails.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }


    public void go_to_order_page() {
        dec = String.valueOf(result);
        String url = "http://10.0.2.2:84/graduation_project/send_request_to_truck.php";
        RequestQueue queue = Volley.newRequestQueue(truckdetails.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(truckdetails.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(truckdetails.this,
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
                params.put("idFromOrg", idFromOrg);
                params.put("truckname", s);
                params.put("username", username);

                params.put("phonenumber", phonenumber);


                params.put("email", email);

                params.put("distance", dec);
                params.put("city", city);
                params.put("truckphone", truckphone.getText().toString());
                params.put("status","InProgress");


                return params;
            }
        };
        queue.add(request);


    }


    public void home(View view) {
        if (!type.equals("user")) {
            Intent intent = new Intent(this, homepagestore.class);
            intent.putExtra("username", storereq);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, homepageuser.class);
            intent.putExtra("username", storereq);
            startActivity(intent);
        }
    }





    ///////////////////////////////////////////////////////////////////////////////
        public void user (View view){
        }

    public void orders_mec(View view) {
        Intent intent = new Intent(this ,order.class);
        intent.putExtra("username",s);
        startActivity(intent);

    }
    public void orders_truck(View view) {
        Intent intent = new Intent(this ,order_truck.class);
        intent.putExtra("username",s);
        startActivity(intent);

    }
    public void cart(View view) {
        Intent intent = new Intent(this ,cart.class);
        intent.putExtra("username",s);
        startActivity(intent);
    }

    public void signup(View view) {
        Intent intent = new Intent(this ,signup.class);

        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(this ,login.class);
                startActivity(intent);
    }
}
