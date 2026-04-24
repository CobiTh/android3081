package com.example.android3081;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.TextView; // Don't forget this import

/**
 * in the future, send details to SQL database, ensure no repeats, etc etc.
 * for now, change logged in to true, save username etc. in preferences
 */
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        EditText username = findViewById(R.id.usernameInput);
        EditText userEmail = findViewById((R.id.emailInput));
        EditText userPassword = findViewById(R.id.passwordInput);
        Button signupButton = findViewById(R.id.buttonSubmit);

        signupButton.setOnClickListener(v -> {
            String emailInput = userEmail.getText().toString();
            String passInput = userPassword.getText().toString();
            SharedPreferences.Editor editor = getSharedPreferences("UserSettings",MODE_PRIVATE).edit();

            editor.putString("password",passInput);
            editor.putString("password",passInput);
            editor.putString("email",emailInput);
            editor.putBoolean("isLoggedIn",true);
            editor.apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();


        });
    }
}
