package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class cart extends AppCompatActivity implements MyAdapter.OnClickl {
ListView meclist,trucklist;
String store;
    private RequestQueue queue;
    String []arr;
    String phone;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<ModelImage> imageList;
    ModelImage modelImage;
    LinearLayoutManager linearLayoutManager;
    MyAdapter.OnClickl listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        meclist=findViewById(R.id.meclist);
        trucklist=findViewById(R.id.trucklist);
        Intent intent=getIntent();
        store= intent.getStringExtra("username");
        queue = Volley.newRequestQueue(this);
        fillmec();
        filltruck();
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageList = new ArrayList<>();
        myAdapter = new MyAdapter(this,imageList,this);
        recyclerView.setAdapter(myAdapter);

        fetchpart();

    }

    public void fetchpart() {

        String url =" http://10.0.2.2:84/graduation_project/sel_by_username.php?username="+store;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);


                        String id = obj.getString("id");
                        String imageurl = obj.getString("path");
                        String partname = obj.getString("partname");
                        String username=obj.getString("username");
                        String model=obj.getString("model");
                        String description=obj.getString("description");
                        String price=obj.getString("price");
                        String partnumber=obj.getString("partnumber");
                        String status=obj.getString("status");

                        String url = "http://10.0.2.2:84/graduation_project/uploads/"+imageurl;

                        modelImage = new ModelImage(id,url,username,partnumber,model,description,price,partname);

                        imageList.add(modelImage);
                        myAdapter.notifyDataSetChanged();



                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(cart.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void filltruck() {

        String url = "http://10.0.2.2:84/graduation_project/truck_req_by_user.php?username="+store;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> orders = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        String s=obj.getString("truckname")+"  "+obj.getString("truckphone");


                        orders.add(s);

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                arr = new String[orders.size()];
                arr = orders.toArray(arr);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        cart.this, android.R.layout.simple_list_item_1,
                        arr);
                trucklist.setAdapter(adapter);

            }
////////////////////////////////////////////////////////////////////////////





            ///////////////////////////////////////////////////////////////
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(cart.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);



    }

    public void fillmec() {

        String url = "http://10.0.2.2:84/graduation_project/mec_req_by_user.php?username="+store;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> orders = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                      String s=obj.getString("mecname")+"  "+obj.getString("mecphone");


                        orders.add(s);

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                arr = new String[orders.size()];
                arr = orders.toArray(arr);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        cart.this, android.R.layout.simple_list_item_1,
                        arr);
                meclist.setAdapter(adapter);

            }
////////////////////////////////////////////////////////////////////////////





            ///////////////////////////////////////////////////////////////
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(cart.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }
    public void onClick( int position) {
        Intent intent =new Intent(getApplicationContext(),partResResult.class);
        intent.putExtra("path",imageList.get(position).getImageurl());
        intent.putExtra("partname",imageList.get(position).getPartname());
        intent.putExtra("partnumber",imageList.get(position).getPartnumber());
        intent.putExtra("price",imageList.get(position).getPrice());
        intent.putExtra("description",imageList.get(position).getDescription());
        intent.putExtra("partowner",imageList.get(position).getUsername());
        intent.putExtra("id",imageList.get(position).getId());
        intent.putExtra("model",imageList.get(position).getModel());
        intent.putExtra("username",store);
        startActivity(intent);
    }

    }
