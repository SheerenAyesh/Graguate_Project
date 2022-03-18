package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void searchPart(View view) {
        Intent intent = new Intent(this ,searchpart.class);
       // intent.putExtra("username",uname);
        startActivity(intent);
    }

    public void mechanic(View view) {
        Intent intent = new Intent(this ,mechanic.class);
        // intent.putExtra("username",uname);
        startActivity(intent);
    }

    public void truck(View view) {
        Intent intent = new Intent(this ,truck.class);
        // intent.putExtra("username",uname);
        startActivity(intent);
    }

    public void homepage(View view) {
        Intent intent = new Intent(this ,homePage.class);
        // intent.putExtra("username",uname);
        startActivity(intent);
    }

    public void setting(View view) {
        Intent intent = new Intent(this ,setting.class);
        // intent.putExtra("username",uname);
        startActivity(intent);
    }

    public void updateProfile(View view) {
        Intent intent = new Intent(this ,updateProfile.class);
        // intent.putExtra("username",uname);
        startActivity(intent);
    }
}