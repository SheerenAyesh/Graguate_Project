package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class homepagestore extends AppCompatActivity {
String store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepagestore);
        Intent intent=getIntent();
         store=intent.getStringExtra("username");
    }

    public void homestore(View view) {
    }

    public void mechanic(View view) {
        Intent intent = new Intent(this ,mechanic.class);
        intent.putExtra("username",store);
        startActivity(intent);

    }

    public void truck(View view) {
        Intent intent = new Intent(this ,truck.class);
        intent.putExtra("username",store);
        startActivity(intent);

    }

    public void searchPart(View view) {
        Intent intent = new Intent(this ,searchpart.class);
        intent.putExtra("username",store);
        startActivity(intent);
    }

    public void orders(View view) {
    }

    public void home(View view) {
    }

    public void user(View view) {
    }
}