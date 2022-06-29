package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class order_truck extends AppCompatActivity {
    ListView orderlist;
    String store,type;
    private RequestQueue queue;
    String[] arr;
    ArrayList<String> id=new ArrayList<>();
    ArrayList<String> idfromorg=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_truck);
        orderlist=findViewById(R.id.orderlist);
        Intent intent=getIntent();
        store= intent.getStringExtra("username");
        type=intent.getStringExtra("type");
        queue = Volley.newRequestQueue(this);

        filllist();
    }
    public void orders(View view) {
        Intent intent = new Intent(this ,order.class);
        intent.putExtra("username",store);
        intent.putExtra("type",type);
        startActivity(intent);

    }

    public void home(View view) {
        if(type.equals("store")){
            Intent intent = new Intent(this ,homepagestore.class);
            intent.putExtra("username",store);
            intent.putExtra("type",type);
            startActivity(intent);}
        else {
            Intent intent = new Intent(this ,homepageuser.class);
            intent.putExtra("username",store);
            intent.putExtra("type",type);
            startActivity(intent);}

    }

    public void user(View view) {
        Intent intent = new Intent(this ,information.class);
        intent.putExtra("username",store);
        intent.putExtra("type",type);
        startActivity(intent);}



    public void filllist() {
        String url = "http://10.0.2.2:84/graduation_project/show_to_truck_his_orders.php?truckname="+store;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> orders = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        String s= "اسم المستخدم" + "  " + obj.getString("username")+"  \n "+ "رقم الهاتف" + "  " + obj.getString("phonenumber")+" \n  " + "المدينة" + "  " + obj.getString("city")+"  \n "+ "ايميل المستخدم" + "  " + obj.getString("email")+"   \n " + "المسافة بينكما " + "  " + obj.getString("distance");
                        System.out.println(s);
                        orders.add(s);
                        id.add(obj.getString("id"));
                        idfromorg.add(obj.getString("idFromOrg"));

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                arr = new String[orders.size()];
                arr = orders.toArray(arr);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        order_truck.this, android.R.layout.simple_list_item_1,
                        arr);
                orderlist.setAdapter(adapter);
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
                orderlist.setOnItemClickListener(itemClickListener);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(order_truck.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    private void go_to_det(int i) {
        Intent intent = new Intent(this ,ApproveOrRejecttruckReq.class);
        intent.putExtra("id",id.get(i));
        intent.putExtra("idfromorg",idfromorg.get(i));
        startActivity(intent);
    }
}