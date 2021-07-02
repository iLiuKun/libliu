package com.example.libjcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.libliu.HelloUtil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HelloUtil.getHello();
    }
}