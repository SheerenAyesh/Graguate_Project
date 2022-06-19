package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class search_part_by_user extends AppCompatActivity {
Spinner spinner;
EditText searchtype;
Button btn;
String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_part_by_user);
        spinner=findViewById(R.id.spinner);
        String []s={"اختر :","البحث بواسطة اسم القطعة","البحث بواسطة رقم القطعة","البحث بواسطة الموديل"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this , android.R.layout.simple_spinner_item,s);
        spinner.setAdapter(adapter);
        searchtype=findViewById(R.id.searchtype);
        btn=findViewById(R.id.btn);
        searchtype.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.INVISIBLE);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");





    }


    public void search(View view) {
        if((spinner.getSelectedItem().equals("البحث بواسطة الموديل")
                ||spinner.getSelectedItem().equals("البحث بواسطة رقم القطعة")||spinner.getSelectedItem().equals("البحث بواسطة اسم القطعة"))){
            if(spinner.getSelectedItem().equals("البحث بواسطة الموديل")){
                Intent intent=new Intent(this,spinnerresult.class);
                intent.putExtra("username",username);
                intent.putExtra("type","model");
                intent.putExtra("value",searchtype.getText().toString());
                startActivity(intent);

            }  else if(spinner.getSelectedItem().equals("البحث بواسطة رقم القطعة")){
                Intent intent=new Intent(this,spinnerresult.class);
                intent.putExtra("username",username);
                intent.putExtra("type","partnumber");
                intent.putExtra("value",searchtype.getText().toString());startActivity(intent);

            }else if(spinner.getSelectedItem().equals("البحث بواسطة اسم القطعة")){
                Intent intent=new Intent(this,spinnerresult.class);
                intent.putExtra("username",username);
                intent.putExtra("type","partname");
                intent.putExtra("value",searchtype.getText().toString());
                startActivity(intent);

            }


        }
    }

    public void method(View view) {
        if((spinner.getSelectedItem().equals("البحث بواسطة الموديل")
                ||spinner.getSelectedItem().equals("البحث بواسطة رقم القطعة")||spinner.getSelectedItem().equals("البحث بواسطة اسم القطعة"))){
            searchtype.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);
            if(spinner.getSelectedItem().equals("البحث بواسطة الموديل"))
            searchtype.setHint("ادخل الموديل");
            else if(spinner.getSelectedItem().equals("البحث بواسطة رقم القطعة"))
                searchtype.setHint("ادخل رقم القطعة");
            else if(spinner.getSelectedItem().equals("البحث بواسطة اسم القطعة"))
                searchtype.setHint("ادخل اسم القطعة");


        }
    }
}