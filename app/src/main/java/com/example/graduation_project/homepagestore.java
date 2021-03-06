package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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

import java.util.ArrayList;

public class homepagestore extends AppCompatActivity {
String store,type;
TextView notification ,reqPage,reqPagemec,notificationmec,reqPagetruck,notificationtruck;
ImageButton ordertruck,ordermec;
    private RequestQueue queue;
    int x=0,x1=0;
    int counter=0;
    int countermec=0,countertruck=0;
    TextView welcome;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homepagestore);
        reqPagetruck=findViewById(R.id.reqPagetruck);
        notificationtruck=findViewById(R.id.notificationtruck);
        Intent intent=getIntent();
         store=intent.getStringExtra("username");
         ordermec=findViewById(R.id.ordermec);
         ordertruck=findViewById(R.id.ordertruck);
        reqPage=findViewById(R.id.reqPage);
        reqPage.setVisibility(View.INVISIBLE);
        reqPagemec=findViewById(R.id.reqPagemec);
        reqPagemec.setVisibility(View.INVISIBLE);
        reqPagetruck.setVisibility(View.INVISIBLE);
        notificationmec=findViewById(R.id.notificationmec);
        type=intent.getStringExtra("type");

        notification=findViewById(R.id.notification);
        queue = Volley.newRequestQueue(this);
        welcome=findViewById(R.id.welcome);
        welcome.setText("???????? ???? "+store+" ???? ???????? ???????????? ");
         //check_truck();
         check_mec();
         check_notification();

        check_notificationmec();
        check_notificationtruck();
    }

    private void check_notificationtruck() {
        String url = "http://10.0.2.2:84/graduation_project/check_if_user_have_truckreq.php?truckname=" + store;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        if(obj.getString("status").equals("InProgress"))
                        ++countertruck;

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                if(countertruck>0){
                    notificationtruck.setText("???????? "+countertruck+" ???? ?????????? ??????????(?????? ????????????????) ???? ?????? ?????????????? ???? ????????????");
                    reqPagetruck.setVisibility(View.VISIBLE);
                    go_to_req_page2();

                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(homepagestore.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);



    }

    private void go_to_req_page2() {
        reqPagetruck.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(homepagestore.this ,order_truck.class);
                intent.putExtra("username",store);
                startActivity(intent);
            }
        });
        reqPagetruck.setPaintFlags(reqPagetruck.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void check_notificationmec() {
        String url = "http://10.0.2.2:84/graduation_project/check_if_user_have_mecreq.php?mecname=" + store;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        if(obj.getString("status").equals("InProgress"))
                        ++countermec;

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                if(countermec>0){
                    notificationmec.setText("???????? "+countermec+" ???? ?????????? ???????????????????? ???? ?????? ?????????????? ???? ????????????");
                    reqPagemec.setVisibility(View.VISIBLE);
                    go_to_req_page1();

                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(homepagestore.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);



    }

    public void go_to_req_page1() {
        reqPagemec.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(homepagestore.this ,order.class);
                intent.putExtra("username",store);
                startActivity(intent);
            }
        });
        reqPagemec.setPaintFlags(reqPagemec.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void check_notification() {

        String url = "http://10.0.2.2:84/graduation_project/check_if_user_have_partreq.php?partowner=" + store;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        if(obj.getString("status").equals("inProgress"))
                        ++counter;

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                if(counter>0){
                    notification.setText("???????? "+counter+" ???? ?????????? ?????????? ???? ?????? ?????????????? ???? ????????????");
                    reqPage.setVisibility(View.VISIBLE);
                    go_to_req_page();

                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(homepagestore.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);




    }

    public void go_to_req_page() {
        reqPage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(homepagestore.this ,partrequest.class);
                intent.putExtra("username",store);
                startActivity(intent);
            }
        });
        reqPage.setPaintFlags(reqPage.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void check_mec() {
        String url = "http://10.0.2.2:84/graduation_project/get_all_mec_by_username.php?username=" + store;


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        ++x;



                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
//                if(x==0){
//                    ordermec.setVisibility(View.INVISIBLE);
//                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(homepagestore.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    private void check_truck() {
        String url = "http://10.0.2.2:84/graduation_project/get_all_truck_by_username.php?username=" + store;


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    x1++;
                    try {
                        JSONObject obj = response.getJSONObject(i);




                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
//                if(x1==0){
//                    ordertruck.setVisibility(View.INVISIBLE);
//                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(homepagestore.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    public void homestore(View view) {
    }

    public void mechanic(View view) {
        Intent intent = new Intent(this ,mechanic.class);
        intent.putExtra("username",store);
        intent.putExtra("type","store");
        startActivity(intent);

    }

    public void truck(View view) {
        Intent intent = new Intent(this ,truck.class);
        intent.putExtra("username",store);
        intent.putExtra("type","store");
        startActivity(intent);

    }

    public void searchPart(View view) {
        Intent intent = new Intent(this ,searchpart.class);
        intent.putExtra("username",store);
        intent.putExtra("type","store");
        startActivity(intent);
    }

    public void orders_mec(View view) {
        Intent intent = new Intent(this ,order.class);
        intent.putExtra("username",store);
        intent.putExtra("type",type);
        startActivity(intent);

    }
    public void orders_truck(View view) {
        Intent intent = new Intent(this ,order_truck.class);
        intent.putExtra("username",store);
        intent.putExtra("type",type);
        startActivity(intent);

    }

    public void home(View view) {
        Intent intent = new Intent(this ,homepagestore.class);
        intent.putExtra("username",store);
        intent.putExtra("type",type);
        startActivity(intent);
    }

    public void user(View view) {
        Intent intent = new Intent(this ,information.class);
        intent.putExtra("username",store);
        intent.putExtra("type",type);
        startActivity(intent);
    }

    public void cart(View view) {
        Intent intent = new Intent(this ,cart.class);
        intent.putExtra("username",store);
        intent.putExtra("type",type);
        startActivity(intent);
    }




}