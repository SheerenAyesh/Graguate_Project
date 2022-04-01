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

public class mechanic extends AppCompatActivity {
String store;
ListView listmec;
    String[] arr;
private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic);
        Intent intent=getIntent();
        store=intent.getStringExtra("username");
        listmec=findViewById(R.id.listmec);
        queue = Volley.newRequestQueue(this);
    }

    public void nearset(View view) {
        getmec();


    }

    public void getmec() {

        String url = "http://10.0.2.2:84/graduation_project/get_all_mec.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> rooms = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        String s=obj.getString("username");
                        rooms.add(s);
                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                arr = new String[rooms.size()];
                arr = rooms.toArray(arr);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mechanic.this, android.R.layout.simple_list_item_1,
                        arr);
                listmec.setAdapter(adapter);


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
                listmec.setOnItemClickListener(itemClickListener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mechanic.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);



    }

    public void go_to_det(int i) {
        Intent intent = new Intent(this ,mecdetails.class);
        intent.putExtra("username",arr[i]);
        startActivity(intent);
    }

    public void mecacount(View view) {
        Intent intent = new Intent(this ,mecaccount.class);
        intent.putExtra("username",store);
        startActivity(intent);

    }
}