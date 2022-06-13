package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class searchpart extends AppCompatActivity {
    String username,type;
    Button searchpart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpart);
        searchpart=findViewById(R.id.seachpart);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        type=intent.getStringExtra("type");
        if(type.equals("user")){
            searchpart.setVisibility(View.INVISIBLE);
        }

    }

    public void addpart(View view) {

        Intent intent = new Intent(this ,addpart.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void search(View view) {

        startActivity(new Intent(this, search_part_by_user.class));

    }

    public void home(View view) {
        if (!type.equals("user")) {
            Intent intent = new Intent(this, homepagestore.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, homepageuser.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }


    }

    public void user(View view) {
    }
}