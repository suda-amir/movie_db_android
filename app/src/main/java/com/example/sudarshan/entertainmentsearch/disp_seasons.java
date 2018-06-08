package com.example.sudarshan.entertainmentsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class disp_seasons extends AppCompatActivity {

    String imdb, type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_seasons);
        Intent intent = getIntent();
        imdb = intent.getStringExtra("imdb");
        type = intent.getStringExtra("type");
    }
}
