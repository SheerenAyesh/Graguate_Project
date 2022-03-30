package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class mechanic extends AppCompatActivity {
String store;
ListView listmec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic);
        Intent intent=getIntent();
        store=intent.getStringExtra("username");
        listmec=findViewById(R.id.listmec);
    }

    public void nearset(View view) {

    }

    public void mecacount(View view) {
        Intent intent = new Intent(this ,mecaccount.class);
        intent.putExtra("username",store);
        startActivity(intent);

    }
}