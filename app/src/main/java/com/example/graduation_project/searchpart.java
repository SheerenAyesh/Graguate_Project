package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class searchpart extends AppCompatActivity implements MyAdapter.OnClickl {
    String username,type;
    Button searchpart;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<ModelImage> imageList;
    ModelImage modelImage;
    LinearLayoutManager linearLayoutManager;
    MyAdapter.OnClickl listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpart);
        searchpart=findViewById(R.id.seachpart);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        type=intent.getStringExtra("type");
        if(type.equals("user")){
            searchpart.setVisibility(View.INVISIBLE);
        }
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageList = new ArrayList<>();
        myAdapter = new MyAdapter(this,imageList,this);
        recyclerView.setAdapter(myAdapter);

        fetchImages();

    }

    public void addpart(View view) {

        Intent intent = new Intent(this ,addpart.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void search(View view) {

        startActivity(new Intent(this, search_part_by_user.class));

    }

    public void home(View view) {
        if (!type.equals("user")) {
            Intent intent = new Intent(this, homepagestore.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, homepageuser.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }


    }

    public void user(View view) {
    }




    public void fetchImages(){

        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.2.2:84/graduation_project/fetchImages.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(succes.equals("1")){

                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String imageurl = object.getString("file_name");
                                    String partname = object.getString("partname");


                                    String url = "http://10.0.2.2:84/graduation_project/uploads/"+imageurl;

                                    modelImage = new ModelImage(id,url,partname);
                                    imageList.add(modelImage);
                                    myAdapter.notifyDataSetChanged();

                                }





                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(searchpart.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }


    @Override
    public void onClick( int position) {
        Intent intent =new Intent(getApplicationContext(),detailspart.class);
        intent.putExtra("partname",imageList.get(position).getPartname());
        startActivity(intent);
    }
}