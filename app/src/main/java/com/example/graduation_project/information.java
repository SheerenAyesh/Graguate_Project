package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class information extends AppCompatActivity {
String username="",type;
TextView welcoming,ifuserdonthavepart,addpart;
ListView listpart;
    private RequestQueue queue;

   ArrayList<String> partid=new ArrayList<>();
   Button displaypart;
   String []arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Intent intent =getIntent();
        queue = Volley.newRequestQueue(this);
        username=intent.getStringExtra("username");
        type=intent.getStringExtra("type");
        welcoming=findViewById(R.id.welcoming);
        ifuserdonthavepart=findViewById(R.id.ifuserdonthavepart);
        displaypart=findViewById(R.id.displaypart);
        ifuserdonthavepart.setVisibility(View.INVISIBLE);
        listpart=findViewById(R.id.listpart);
        addpart=findViewById(R.id.addpart);
        addpart.setVisibility(View.INVISIBLE);
        welcoming.setText("اهلا بك "+username+" في صفحة المعلومات الشخصيه");

        addpart.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(information.this,searchpart.class);
                intent.putExtra("username",username);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
        addpart.setPaintFlags(addpart.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
if(type.equals("user"))
    displaypart.setVisibility(View.INVISIBLE);

    }

    public void updateinfo(View view) {
        Intent intent = new Intent(this ,personal.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void displaypart(View view) {

        String url = "http://10.0.2.2:84/graduation_project/get_all_part_by_username.php?username=" + username;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                       ArrayList<String>part=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                       part.add(obj.getString("partname")+" "+obj.getString("partnumber"));
                       partid.add(obj.getString("id"));


                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                arr = new String[part.size()];
                arr = part.toArray(arr);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        information.this, android.R.layout.simple_list_item_1,
                        arr);
                listpart.setAdapter(adapter);


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
                listpart.setOnItemClickListener(itemClickListener);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(information.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    private void go_to_det(int i) {
        final Dialog dialog = new Dialog(information.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.dialog_delete_part);
        final Button deletepart,updatepart;
        deletepart=dialog.findViewById(R.id.deletepart);
        updatepart=dialog.findViewById(R.id.updatepart);
        deletepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               delete(i);
                dialog.dismiss();
            }
        });
        updatepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               update(i);
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


    }

    private void update(int i) {
        Intent intent=new Intent(this,updatepartinfo.class);
        intent.putExtra("id",partid.get(i));
        startActivity(intent);
    }

    private void delete(int i) {
        String url = "http://10.0.2.2:84/graduation_project/delete_part.php?id=" + partid.get(i);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);


                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(information.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }

    public void logout(View view) {
        Intent intent = new Intent(this ,login.class);

        startActivity(intent);
    }
}