package com.example.textprogressbarexample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.textprogressbar.TextProgressBar;


public class MainActivity extends AppCompatActivity {

    TextProgressBar light;
    TextProgressBar dark;
    Button toggleLight;
    Button toggleDark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        light = findViewById(R.id.textlight);
        dark = findViewById(R.id.textdark);
        toggleLight = findViewById(R.id.toggleLight);
        toggleDark = findViewById(R.id.toggleDark);

        toggleLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Light","b " + light.inProgress());
                light.setProgress(!light.inProgress());
                Log.e("Light","a " + light.inProgress());
            }
        });
        toggleDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Dark","b " + light.inProgress());
                dark.setProgress(!dark.inProgress());
                Log.e("Dark","a " + light.inProgress());
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "HANDLER",Toast.LENGTH_LONG).show();
                Log.e("Light","b " + light.inProgress());
                light.setProgress(!light.inProgress());
                Log.e("Light","a " + light.inProgress());
                Log.e("Dark","b " + light.inProgress());
                dark.setProgress(!dark.inProgress());
                Log.e("Dark","a " + light.inProgress());
            }
        }, 100000);
    }
}
