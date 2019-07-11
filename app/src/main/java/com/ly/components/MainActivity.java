package com.ly.components;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ly.components.java.utils.LLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LLog.Companion.setDebug(true);
    }
}
