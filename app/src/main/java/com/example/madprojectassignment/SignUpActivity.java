package com.example.madprojectassignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText editTextName, editTextUsername, editTextPassword;

    Button buttonSignUp;

    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
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

        setContentView(R.layout.activity_signup);

        // Retrieve the TextInputLayouts
        TextInputLayout layoutEditTextName = findViewById(R.id.name);
        TextInputLayout layoutEditTextUsername = findViewById(R.id.username);
        TextInputLayout layoutEditTextPassword = findViewById(R.id.password);

        // Get the TextInputEditTexts from the TextInputLayouts
        editTextName = (TextInputEditText) layoutEditTextName.getEditText();
        editTextUsername = (TextInputEditText) layoutEditTextUsername.getEditText();
        editTextPassword = (TextInputEditText) layoutEditTextPassword.getEditText();


        // Inside your onCreate() method after initializing your views
        // Name TextWatcher
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutEditTextName.setError(null); // Clear error when user starts typing
                layoutEditTextName.setErrorEnabled(false); // Optionally, disable the error display
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nothing needed here
            }
        });

        // Username TextWatcher
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutEditTextUsername.setError(null); // Clear error when user starts typing
                layoutEditTextUsername.setErrorEnabled(false); // Optionally, disable the error display
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nothing needed here
            }
        });

        // Password TextWatcher
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutEditTextPassword.setError(null); // Clear error when user starts typing
                layoutEditTextPassword.setErrorEnabled(false); // Optionally, disable the error display
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nothing needed here
            }
        });


        buttonSignUp = findViewById(R.id.signUpButton);
        mAuth = FirebaseAuth.getInstance();

        buttonSignUp.setOnClickListener(v -> {
            // Reset errors on every submit attempt
            layoutEditTextName.setError(null);
            layoutEditTextUsername.setError(null);
            layoutEditTextPassword.setError(null);

            String name, username, password;
            name = editTextName.getText() != null ? editTextName.getText().toString() : "";
            username = editTextUsername.getText() != null ? editTextUsername.getText().toString() : "";
            password = editTextPassword.getText() != null ? editTextPassword.getText().toString() : "";

            boolean isValid = true;

            if (name.isEmpty()) {
                layoutEditTextName.setErrorEnabled(true);
                layoutEditTextName.setError("Name cannot be empty");
                isValid = false;
            }
            if (username.isEmpty()) {
                layoutEditTextUsername.setErrorEnabled(true);
                layoutEditTextUsername.setError("Email cannot be empty");
                isValid = false;
            }
            if (password.isEmpty()) {
                layoutEditTextPassword.setErrorEnabled(true);
                layoutEditTextPassword.setError("Password cannot be empty");
                isValid = false;
            }

            if (isValid) {
                mAuth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SignUpActivity.this, "Account Created.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                    layoutEditTextPassword.setErrorEnabled(true);
                                    layoutEditTextPassword.setError("Password is too weak. It must have at least 6 characters.");
                                } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    layoutEditTextUsername.setErrorEnabled(true);
                                    layoutEditTextUsername.setError("Invalid email format.");
                                } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    layoutEditTextUsername.setErrorEnabled(true);
                                    layoutEditTextUsername.setError("An account already exists with this email.");
                                } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                    layoutEditTextUsername.setErrorEnabled(true);
                                    layoutEditTextUsername.setError("No user found with this email or user has been disabled.");
                                }
                                String errorMessage = "Authentication failed.";
                                Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
