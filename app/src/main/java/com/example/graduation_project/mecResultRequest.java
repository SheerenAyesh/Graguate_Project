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

public class mecResultRequest extends AppCompatActivity {
    TextView result,resultdelete;
    String userReqmec,id,idfromorg;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mec_result_request);

        result=findViewById(R.id.result);
        resultdelete=findViewById(R.id.result_delete);
        queue = Volley.newRequestQueue(this);
        Intent intent=getIntent();
        userReqmec=intent.getStringExtra("username");
        idfromorg=intent.getStringExtra("idFromOrg");

        id=intent.getStringExtra("id");
        getstatus();
    }
    public void getstatus() {
        String url =" http://10.0.2.2:84/graduation_project/sel_by_id_mec_status.php?id="+id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);


                        String status=obj.getString("status");
                        if(status.equals("Aproved")){
                            result.setText("لقد تمت الموافقة من قبل الميكانيكي وسيتم التواصل معك في اقرب وقت");
                        }else if(status.equals("Rejected")){
                            result.setText("لقد تم الرفض من قبل الميكانيكي ");
                        }else{
                            result.setText("لم يتم الموافقة من قبل الميكانيكي بعد ...بامكانك الانتظار او حذف الطلب");

                        }






                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mecResultRequest.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }


    public void delete(View view) {
        String url =" http://10.0.2.2:84/graduation_project/delete_request_mec.php?id="+id+"&&idFromOrg="+idfromorg;

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
        resultdelete.setText("تم حذف الطلب بنجاح");
        queue.add(request);



    }
}