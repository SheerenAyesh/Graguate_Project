package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class homepageuser extends AppCompatActivity {
String user="",type;
TextView welcome1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepageuser);
        Intent intent=getIntent();
        user=intent.getStringExtra("username");
        welcome1=findViewById(R.id.welcome1);
        welcome1.setText("اهلا بك "+user+" في صفحة المستخدم");
        type=intent.getStringExtra("type");


    }

    public void truck(View view) {
        Intent intent = new Intent(this ,truck.class);
        intent.putExtra("username",user);
        intent.putExtra("type","user");
        startActivity(intent);
    }

    public void mechanic(View view) {
        Intent intent = new Intent(this ,mechanic.class);
        intent.putExtra("username",user);
        intent.putExtra("type","user");
        startActivity(intent);
    }

    public void searchPart(View view) {
        Intent intent = new Intent(this ,searchpart.class);
        intent.putExtra("username",user);
        intent.putExtra("type","user");
        startActivity(intent);
    }

    public void home(View view) {

            Intent intent = new Intent(this ,homepageuser.class);
            intent.putExtra("username",user);
            intent.putExtra("type",type);
            startActivity(intent);

    }

    public void cart(View view) {
        Intent intent = new Intent(this ,cart.class);
        intent.putExtra("username",user);
        intent.putExtra("type",type);
        startActivity(intent);
    }

    public void user(View view) {
        Intent intent = new Intent(this ,information.class);
        intent.putExtra("username",user);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}