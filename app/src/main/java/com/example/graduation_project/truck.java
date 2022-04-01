package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class truck extends AppCompatActivity {
String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    public void weight(View view) {
    }

    public void nearest(View view) {
    }

    public void truuck(View view) {
        Intent intent = new Intent(this ,truckaccount.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}