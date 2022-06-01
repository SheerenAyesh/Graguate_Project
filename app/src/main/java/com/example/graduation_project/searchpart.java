package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class searchpart extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpart);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
    }

    public void addpart(View view) {

        Intent intent = new Intent(this ,addpart.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void search(View view) {

        startActivity(new Intent(this, search_part_by_user.class));

    }
}