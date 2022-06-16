package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class detailspart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailspart);

        TextView txt=findViewById(R.id.txt);
        Intent intent=getIntent();
        txt.setText(intent.getStringExtra("partname"));
    }
}