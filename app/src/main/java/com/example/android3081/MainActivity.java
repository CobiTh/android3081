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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("isLoggedIn", false);

        if(!loggedIn) {
            setContentView(R.layout.welcome_activity);

            Button loginButton = findViewById(R.id.userLogin);
            Button signUpButton = findViewById(R.id.userSignUp);

            loginButton.setOnClickListener(v -> {
                startActivity(new Intent(this, LoginActivity.class));
            });
            signUpButton.setOnClickListener(v -> {
                        startActivity(new Intent(this, SignUpActivity.class));
            });
        } else {
            setContentView(R.layout.activity_main);

            TextView greetingText = findViewById(R.id.textViewGreeting);
            EditText inputField = findViewById(R.id.editTextInput);
            Button submitButton = findViewById(R.id.buttonSubmit);
            Button signOutButton = findViewById(R.id.buttonSignout);
            // to create a new trip
            submitButton.setOnClickListener(v -> {
                String text = inputField.getText().toString();

                Trip thisTrip = new Trip(text);
                System.out.println("New Trip ID: " + thisTrip.getId());

                greetingText.setText("Hello " + text);
            });

            //to sign out of event
            signOutButton.setOnClickListener(v -> {
                getSharedPreferences("UserSettings", MODE_PRIVATE).edit().clear().apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            });
        }

    }
}