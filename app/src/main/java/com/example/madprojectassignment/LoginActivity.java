package com.example.madprojectassignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editTextUsername, editTextPassword;

    Button buttonLogin, buttonForgotPassword;

    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
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

        setContentView(R.layout.activity_login);

        // Retrieve the TextInputLayouts
        TextInputLayout layoutEditTextUsername = findViewById(R.id.username);
        TextInputLayout layoutEditTextPassword = findViewById(R.id.password);

        // Get the TextInputEditTexts from the TextInputLayouts
        editTextUsername = (TextInputEditText) layoutEditTextUsername.getEditText();
        editTextPassword = (TextInputEditText) layoutEditTextPassword.getEditText();

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

        buttonLogin = findViewById(R.id.loginButton);
        buttonForgotPassword = findViewById(R.id.forgotPassButton);
        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(v -> {
            // Reset errors on every submit attempt
            layoutEditTextUsername.setError(null);
            layoutEditTextPassword.setError(null);

            String username, password;
            username = editTextUsername.getText() != null ? editTextUsername.getText().toString() : "";
            password = editTextPassword.getText() != null ? editTextPassword.getText().toString() : "";

            boolean isValid = true;

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
                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user based on the specific exception
                                if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                    // This means the email does not exist in the system
                                    layoutEditTextUsername.setErrorEnabled(true);
                                    layoutEditTextUsername.setError("No user found with this email. Please sign up first.");
                                } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    // This can mean the password is wrong or the email format is invalid, generally used for wrong password
                                    layoutEditTextPassword.setErrorEnabled(true);
                                    layoutEditTextPassword.setError("The password is incorrect.");
                                    layoutEditTextUsername.setErrorEnabled(true);
                                    layoutEditTextUsername.setError("The email format is invalid.");
                                }  else {
                                    // General authentication failure message
                                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        buttonForgotPassword.setOnClickListener(v -> Toast.makeText(LoginActivity.this, "Not Implemented Yet.", Toast.LENGTH_SHORT).show());
    }
}
