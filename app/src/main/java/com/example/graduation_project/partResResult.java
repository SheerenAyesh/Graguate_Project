package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class partResResult extends AppCompatActivity {
    TextView partname,partnumber,price,username,desc,model,result,resultdelete;
    String userReqPart,id,path,idfromorg;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_res_result);
        partname=findViewById(R.id.partname);
        partnumber=findViewById(R.id.partnumber);
        price=findViewById(R.id.price);
        username=findViewById(R.id.username);
        desc=findViewById(R.id.desc);
        model=findViewById(R.id.model);
        result=findViewById(R.id.result);
        resultdelete=findViewById(R.id.result_delete);
        queue = Volley.newRequestQueue(this);

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
        idfromorg=intent.getStringExtra("idFromOrg");

        getstatus();
    }

    public void getstatus() {
        String url =" http://10.0.2.2:84/graduation_project/sel_by_id.php?id="+id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);


                       String status=obj.getString("status");
                       if(status.equals("Aproved")){
                         result.setText("?????? ?????? ???????????????? ???? ?????? ???????? ???????????? ?????? ?????????? ???? ?????????? ?????????????? ?????? ???? ???????? ??????");
                       }else{
                           result.setText("???? ?????? ???????????????? ???? ?????? ???????? ???????????? ?????? ...?????????????? ???????????????? ???? ?????? ??????????");
                       }






                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(partResResult.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void delete(View view) {

        String url =" http://10.0.2.2:84/graduation_project/delete_request_part.php?id="+id+"&&idFromOrg="+idfromorg;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);


                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        resultdelete.setText("???? ?????? ?????????? ??????????");
        queue.add(request);



    }
}