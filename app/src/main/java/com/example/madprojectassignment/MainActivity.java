package com.example.madprojectassignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.google.android.material.color.DynamicColors;
import androidx.appcompat.app.AppCompatActivity;
import com.example.madprojectassignment.databinding.ActivityMainBinding;
import com.google.android.material.color.DynamicColorsOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define your DynamicColorsOptions here
        DynamicColorsOptions dynamicColorsOptions = new DynamicColorsOptions.Builder().build();
        DynamicColors.applyToActivityIfAvailable(this, dynamicColorsOptions);

        com.example.madprojectassignment.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button signUpButton = findViewById(R.id.signUpButton);
        Button loginButton = findViewById(R.id.loginButton);

        // Set click listener for the sign-up button
        signUpButton.setOnClickListener(v -> {
            // Start SignUpActivity
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Set click listener for the login button
        loginButton.setOnClickListener(v -> {
            // Start LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
