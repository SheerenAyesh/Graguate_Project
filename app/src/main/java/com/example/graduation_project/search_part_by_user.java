package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class search_part_by_user extends AppCompatActivity {
//Spinner spinner;
//EditText searchtype;
//Button btn;
    AutoCompleteTextView family,model,year,part;
    String []familyy;
    String []yearr;
    String[]modell;
    String []partt;
    String []selection=new String[4];

String username;
private RequestQueue queue;
    ArrayAdapter<String> adapter;
    TextView test;
    ListView typecar,modelcar,yearcar,partcar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_part_by_user);
      //  spinner=findViewById(R.id.spinner);
     //  String []s={"اختر :","البحث بواسطة اسم القطعة","البحث بواسطة رقم القطعة","البحث بواسطة الموديل"};
       // ArrayAdapter<String> adapter= new ArrayAdapter<>(this , android.R.layout.simple_spinner_item,s);
       // spinner.setAdapter(adapter);
        //searchtype=findViewById(R.id.searchtype);
        //btn=findViewById(R.id.btn);
        //searchtype.setVisibility(View.INVISIBLE);
        //btn.setVisibility(View.INVISIBLE);

        queue = Volley.newRequestQueue(this);
        family=findViewById(R.id.family);
        model=findViewById(R.id.model);
       year=findViewById(R.id.year);
       typecar=findViewById(R.id.typecar);
       modelcar=findViewById(R.id.modelcar);
        yearcar=findViewById(R.id.yearcar);
        part=findViewById(R.id.part);
        partcar=findViewById(R.id.partcar);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");

       getfamily();
       filltypecar();

        family.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                String   familyselected =(String) parent.getItemAtPosition(position);
                getmodel(selection[0]);

            }
        });

        model.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                String   modelyselected =(String) parent.getItemAtPosition(position);
                getyear(selection[0],modelyselected);

            }
        });
        year.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                String  yearselected =(String) parent.getItemAtPosition(position);
                teeest(yearselected);
                getpart(selection[0],selection[1],selection[2]);
            }
        });
        part.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                String  partselected =(String) parent.getItemAtPosition(position);
                teeest2(partselected);

            }
        });








    }

    private void teeest2(String partselected) {
        selection[3]=partselected;
    }

    private void getpart(String s, String s1, String s2) {
        selection[2]=s2;
        String url = "http://10.0.2.2:84/graduation_project/get_part.php?family="+selection[0]+
                "&&model="+selection[1]+"&&year="+selection[2];

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String>part2=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        part2.add(obj.getString("partname"));


                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                ArrayList<String> partnew=new ArrayList<>();
                for(int i=0;i<part2.size();i++){
                    if(!partnew.contains(part2.get(i))){
                        partnew.add(part2.get(i));
                    }
                }
                partt=new String[partnew.size()];
                partt=partnew.toArray(partt);

                ArrayAdapter<String> adapter5 = new ArrayAdapter<>(
                        search_part_by_user.this, android.R.layout.simple_list_item_1,
                        partt);
                partcar.setAdapter(adapter5);


                AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {
                        for(int i=0;i<partt.length;i++){
                            if(position == i){
                                selection[3]=partt[i];
                                part.setText(partt[i]);
                                break;


                            }}
                    }
                };
                partcar.setOnItemClickListener(itemClickListener);
                setautopart();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(search_part_by_user.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    private void setautopart() {
        adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, partt);

        part.setThreshold(1);
        part.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////
    private void filltypecar() {

        String url = "http://10.0.2.2:84/graduation_project/get_family.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String>family2=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        family2.add(obj.getString("family"));


                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                ArrayList<String> familynew=new ArrayList<>();
                for(int i=0;i<family2.size();i++){
                    if(!familynew.contains(family2.get(i))){
                        familynew.add(family2.get(i));
                    }
                }
                familyy=new String[familynew.size()];
                familyy=familynew.toArray(familyy);
                ArrayAdapter<String> adapterr = new ArrayAdapter<>(
                        search_part_by_user.this, android.R.layout.simple_list_item_1,
                        familyy);
                typecar.setAdapter(adapterr);


                AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {
                        for(int i=0;i<familyy.length;i++){
                            if(position == i){
                                selection[0]=familyy[i];
                                family.setText(familyy[i]);
                                break;


                            }}
                    }
                };
                typecar.setOnItemClickListener(itemClickListener);
                setauto();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(search_part_by_user.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);



    }

    private void teeest(String yearselected) {
        selection[2]=yearselected;

    }

    private void getyear(String s, String modelyselected) {
        selection[1]=modelyselected;
        String url = "http://10.0.2.2:84/graduation_project/get_year.php?family="+selection[0]+"&&model="+selection[1];

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String>year2=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        year2.add(obj.getString("year"));


                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                ArrayList<String> yearnew=new ArrayList<>();
                for(int i=0;i<year2.size();i++){
                    if(!yearnew.contains(year2.get(i))){
                        yearnew.add(year2.get(i));
                    }
                }
                yearr=new String[yearnew.size()];
                yearr=yearnew.toArray(yearr);

                ArrayAdapter<String> adapter4 = new ArrayAdapter<>(
                        search_part_by_user.this, android.R.layout.simple_list_item_1,
                        yearr);
                yearcar.setAdapter(adapter4);


                AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {
                        for(int i=0;i<yearr.length;i++){
                            if(position == i){
                                selection[2]=yearr[i];
                                year.setText(yearr[i]);
                                break;


                            }}
                    }
                };
                yearcar.setOnItemClickListener(itemClickListener);
                setautoyeaer();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(search_part_by_user.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }

    private void setautoyeaer() {
        adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, yearr);

        year.setThreshold(1);
        year.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////////////////
    public void getmodel(String familyselected)
    {   selection[0]=familyselected;


        String url = "http://10.0.2.2:84/graduation_project/get_model.php?family="+selection[0];

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String>model2=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        model2.add(obj.getString("model"));


                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                ArrayList<String> modelnew=new ArrayList<>();
                for(int i=0;i<model2.size();i++){
                    if(!modelnew.contains(model2.get(i))){
                        modelnew.add(model2.get(i));
                    }
                }
                modell=new String[modelnew.size()];
                modell=modelnew.toArray(modell);
                setautomodel();
                ArrayAdapter<String> adapter3 = new ArrayAdapter<>(
                        search_part_by_user.this, android.R.layout.simple_list_item_1,
                        modell);
                modelcar.setAdapter(adapter3);


                AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {
                        for(int i=0;i<modell.length;i++){
                            if(position == i){
                                selection[1]=modell[i];
                                model.setText(modell[i]);
                                break;


                            }}
                    }
                };
                modelcar.setOnItemClickListener(itemClickListener);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(search_part_by_user.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }

    public void getfamily() {


    }

    private void setauto() {
        adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, familyy);

        family.setThreshold(1);
        family.setAdapter(adapter);

    }
    private void setautomodel() {
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, modell);

        model.setThreshold(1);
        model.setAdapter(adapter2);
    }


    public void search(View view) {

        Intent intent=new Intent(this,spinnerresult.class);
        intent.putExtra("family",selection[0]);
        intent.putExtra("model",selection[1]);
        intent.putExtra("year",selection[2]);
        intent.putExtra("part",selection[3]);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}