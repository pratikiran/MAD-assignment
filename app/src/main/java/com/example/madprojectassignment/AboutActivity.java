package com.example.madprojectassignment;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Define your DynamicColorsOptions here
        DynamicColorsOptions dynamicColorsOptions = new DynamicColorsOptions.Builder().build();
        DynamicColors.applyToActivityIfAvailable(this, dynamicColorsOptions);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
    }
}