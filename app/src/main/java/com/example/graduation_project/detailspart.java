package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class detailspart extends AppCompatActivity {
TextView partname,partnumber,price,username,desc,model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailspart);
        partname=findViewById(R.id.partname);
        partnumber=findViewById(R.id.partnumber);
        price=findViewById(R.id.price);
        username=findViewById(R.id.username);
        desc=findViewById(R.id.desc);
        model=findViewById(R.id.model);

        FillTextFromIntent();


    }

    public void FillTextFromIntent() {
        Intent intent=getIntent();
        partnumber.setText(intent.getStringExtra("partnumber"));
        partname.setText(intent.getStringExtra("partname"));
        price.setText(intent.getStringExtra("price"));
        username.setText(intent.getStringExtra("username"));
        desc.setText(intent.getStringExtra("description"));
        model.setText(intent.getStringExtra("model"));
    }
}