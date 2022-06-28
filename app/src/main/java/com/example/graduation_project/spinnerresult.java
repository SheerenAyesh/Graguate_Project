package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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

import java.util.ArrayList;
import java.util.List;

public class spinnerresult extends AppCompatActivity implements MyAdapter.OnClickl {
String username;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<ModelImage> imageList;
    ModelImage modelImage;
    LinearLayoutManager linearLayoutManager;
    MyAdapter.OnClickl listener;
    String value;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinnerresult);
        queue = Volley.newRequestQueue(this);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageList = new ArrayList<>();
        myAdapter = new MyAdapter(this,imageList,this);
        recyclerView.setAdapter(myAdapter);
        value=intent.getStringExtra("value");

//if(intent.getStringExtra("type").equals("partname"))
  //      fetchImages();
//else if(intent.getStringExtra("type").equals("partnumber"))
  //  fetchImages1();
//else if(intent.getStringExtra("type").equals("model"))
  //  fetchImages2();

        fetchImages(intent.getStringExtra("family"),intent.getStringExtra("model"),intent.getStringExtra("year")
        ,intent.getStringExtra("part"));
    }


    public void fetchImages(String family,String model,String year,String part){

        String url =" http://10.0.2.2:84/graduation_project/sel_result.php?family="+family+"&&model="+model+"&&year="+year+"&&partname="+
                part;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);


                        String id = obj.getString("id");
                        String imageurl = obj.getString("file_name");
                        String partname = obj.getString("partname");
                        String username=obj.getString("username");
                        String model=obj.getString("model");
                        String description=obj.getString("description");
                        String price=obj.getString("price");
                        String partnumber=obj.getString("partnumber");

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

                Toast.makeText(spinnerresult.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
    public void fetchImages1(){

        String url =" http://10.0.2.2:84/graduation_project/sel_by_part_num.php?partnumber="+value;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);


                        String id = obj.getString("id");
                        String imageurl = obj.getString("file_name");
                        String partname = obj.getString("partname");
                        String username=obj.getString("username");
                        String model=obj.getString("model");
                        String description=obj.getString("description");
                        String price=obj.getString("price");
                        String partnumber=obj.getString("partnumber");

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

                Toast.makeText(spinnerresult.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
    public void fetchImages2(){

        String url =" http://10.0.2.2:84/graduation_project/sel_by_model.php?model="+value;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);


                        String id = obj.getString("id");
                        String imageurl = obj.getString("file_name");
                        String partname = obj.getString("partname");
                        String username=obj.getString("username");
                        String model=obj.getString("model");
                        String description=obj.getString("description");
                        String price=obj.getString("price");
                        String partnumber=obj.getString("partnumber");

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

                Toast.makeText(spinnerresult.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
    @Override
    public void onClick( int position) {
        show(position);

    }

    private void show(int position) {
        final Dialog dialog = new Dialog(spinnerresult.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.partdet);

        final TextView partname,partnumber,price,Username,desc,model;
        final Button showdet;
        partname=dialog.findViewById(R.id.partname);
        partnumber=dialog.findViewById(R.id.partnumber);
        price=dialog.findViewById(R.id.price);
        Username=dialog.findViewById(R.id.username);
        desc=dialog.findViewById(R.id.desc);
        model=dialog.findViewById(R.id.model);
        Username.setText(imageList.get(position).getUsername());
        partname.setText(imageList.get(position).getPartname());
        partnumber.setText(imageList.get(position).getPartnumber());
        price.setText(imageList.get(position).getPrice());
        desc.setText(imageList.get(position).getDescription());
        model.setText((imageList.get(position).getModel()));
        showdet=dialog.findViewById(R.id.showdet);

        showdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),detailspart.class);
                intent.putExtra("path",imageList.get(position).getImageurl());
                intent.putExtra("partname",imageList.get(position).getPartname());
                intent.putExtra("partnumber",imageList.get(position).getPartnumber());
                intent.putExtra("price",imageList.get(position).getPrice());
                intent.putExtra("description",imageList.get(position).getDescription());
                intent.putExtra("partowner",imageList.get(position).getUsername());
                intent.putExtra("id",imageList.get(position).getId());
                intent.putExtra("model",imageList.get(position).getModel());
                intent.putExtra("username",username);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);





    }
}