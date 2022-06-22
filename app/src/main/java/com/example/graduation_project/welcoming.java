package com.example.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class welcoming extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcoming);



        Thread th=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();


                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        th.start();
    }
}