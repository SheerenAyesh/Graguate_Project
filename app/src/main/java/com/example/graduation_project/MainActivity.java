package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    String username;
    private SharedPreferences prefs;
   TextView textView;
    LinearLayout login_and_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_and_reg=findViewById(R.id.login_and_reg);
        textView = findViewById(R.id.textView);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        if(username!=null){
      login_and_reg.setVisibility(View.GONE);
        }

        textView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,signup.class);
                startActivity(intent);
            }
        });
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void searchPart(View view) {
        Intent intent = new Intent(this ,searchpart.class);
       // intent.putExtra("username",uname);
        intent.putExtra("type","guest");
        intent.putExtra("username","guest");
        startActivity(intent);
    }

    public void mechanic(View view) {
        Intent intent = new Intent(this ,mechanic.class);
        // intent.putExtra("username",uname);
        intent.putExtra("type","guest");
        intent.putExtra("username","guest");
        startActivity(intent);
    }

    public void truck(View view) {
        Intent intent = new Intent(this ,truck.class);
        // intent.putExtra("username",uname);
        intent.putExtra("type","guest");
        intent.putExtra("username","guest");
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(this ,login.class);
        // intent.putExtra("username",uname);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this ,signup.class);
        startActivity(intent);
    }

    public void question(View view) {
        Intent intent = new Intent(this ,help.class);
        startActivity(intent);
    }


//    public void homepage(View view) {
//        Intent intent = new Intent(this ,homePage.class);
//        // intent.putExtra("username",uname);
//        startActivity(intent);
//    }
//
//    public void setting(View view) {
//        Intent intent = new Intent(this ,setting.class);
//        // intent.putExtra("username",uname);
//        startActivity(intent);
//    }
//
//    public void updateProfile(View view) {
//        Intent intent = new Intent(this ,updateProfile.class);
//        // intent.putExtra("username",uname);
//        startActivity(intent);
//    }
}