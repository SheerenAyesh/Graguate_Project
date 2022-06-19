package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class partrequest extends AppCompatActivity {
TextView ifhavepartreq,ifhavepart;
ListView listreq;
String arr[];
String partnum[];
String username;
boolean check=false;
boolean check2=false;
    ArrayList<String> id=new ArrayList<>();
            ArrayList<String>id_from_org=new ArrayList<>();

private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partrequest);
       ifhavepartreq=findViewById(R.id.ifhavepartreq);
        ifhavepart=findViewById(R.id.ifhavepart);
        listreq=findViewById(R.id.listreq);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        queue = Volley.newRequestQueue(this);
        ifuserhavepart();




    }

    public void ifuserhavepartreq() {
        String url = "http://10.0.2.2:84/graduation_project/check_if_user_have_partreq.php?partowner=" + username;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        check2=true;


                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                if(!check2){
                    ifhavepartreq.setText("لا يوجد لديك اي طلبات ");
                }
                else {
                    ifhavepartreq.setText("الطلبات التي لديك : ");
                   filllist();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(partrequest.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void ifuserhavepart() {

        String url = "http://10.0.2.2:84/graduation_project/checkifhavepart.php?username="+ username;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                       check=true;


                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                if(!check){
                    ifhavepart.setText("لم تقم بعرض اي قطعة ");
                }
                else{
                    ifhavepart.setText("اهلا بك في صفحة عرض الطلبات");
                    ifuserhavepartreq();

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(partrequest.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void filllist() {

        String url = "http://10.0.2.2:84/graduation_project/check_if_user_have_partreq.php?partowner=" + username;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                ArrayList<String>list=new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
String s="الاسم الشخص الذي طلب القطعة :"+obj.getString("username")+"\n"+"رقم هاتفه :"+obj.getString("usernumber")+"\n"+"المدينة المتواجد بها :"+obj.getString("usercity")+"\n"+" اسم القطعة :"+obj.getString("partname")+" \n "+"رقم القطعة :"+obj.getString("partnumber");
                       id.add(obj.getString("id"));
                       list.add(s);
id_from_org.add(obj.getString("idFromOrg"));

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                arr = new String[list.size()];
                arr = list.toArray(arr);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        partrequest.this, android.R.layout.simple_list_item_1,
                        arr);
                listreq.setAdapter(adapter);
                AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {
                        for(int i=0;i<arr.length;i++){
                            if(position == i){
                                go_to_det(i);


                            }}
                    }
                };
                listreq.setOnItemClickListener(itemClickListener);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(partrequest.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }
    public void go_to_det(int i){
        Intent intent = new Intent(this ,ApproveOrRejectPartRequest.class);
        intent.putExtra("id",id.get(i));
        intent.putExtra("idfromorg",id_from_org.get(i));
        startActivity(intent);

    }
}