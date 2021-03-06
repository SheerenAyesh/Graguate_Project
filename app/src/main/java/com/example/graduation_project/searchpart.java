package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
    LinearLayout forguest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpart);
        searchpart=findViewById(R.id.seachpart);
        forguest=findViewById(R.id.forguest);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        if(username.equals("guest")){//////////////////////////////////////////////////////////////////////////////here
            searchpart.setVisibility(View.INVISIBLE);
            forguest.setVisibility(View.INVISIBLE);
        }
        type=intent.getStringExtra("type");
        if(type.equals("user")){
            searchpart.setVisibility(View.INVISIBLE);
            forguest.setVisibility(View.INVISIBLE);
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
        intent.putExtra("type",type);

        startActivity(intent);
    }

    public void search(View view) {


        Intent intent=new Intent(this,search_part_by_user.class);
        intent.putExtra("username",username);
        intent.putExtra("type",type);
        startActivity(intent);

    }

    public void home(View view) {
        if (!type.equals("user")) {
            Intent intent = new Intent(this, homepagestore.class);
            intent.putExtra("username", username);
            intent.putExtra("type",type);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, homepageuser.class);
            intent.putExtra("username", username);
            intent.putExtra("type",type);
            startActivity(intent);
        }


    }

    public void user(View view) {
        Intent intent = new Intent(this, information.class);
        intent.putExtra("username", username);
        intent.putExtra("type",type);
        startActivity(intent);

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
                                    String username=object.getString("username");
                                    String model=object.getString("model");
                                    String description=object.getString("description");
                                    String price=object.getString("price");
                                    String partnumber=object.getString("partnumber");

                                    String url = "http://10.0.2.2:84/graduation_project/uploads/"+imageurl;
                                    modelImage = new ModelImage(id,url,username,partnumber,model,description,price,partname);

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
        show(position);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    private void show(int position) {
        final Dialog dialog = new Dialog(searchpart.this);
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void orders_mec(View view) {
    Intent intent = new Intent(this ,order.class);
    intent.putExtra("username",username);
    intent.putExtra("type",type);
    startActivity(intent);

}
    public void orders_truck(View view) {
        Intent intent = new Intent(this ,order_truck.class);
        intent.putExtra("username",username);
        intent.putExtra("type",type);
        startActivity(intent);

    }



    public void cart(View view) {
        Intent intent = new Intent(this ,cart.class);
        intent.putExtra("username",username);
        intent.putExtra("type",type);
        startActivity(intent);
    }


}