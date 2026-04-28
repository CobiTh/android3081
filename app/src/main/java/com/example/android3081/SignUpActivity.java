package com.example.android3081;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        EditText username = findViewById(R.id.usernameInput);
        EditText userEmail = findViewById(R.id.emailInput);
        EditText userPassword = findViewById(R.id.passwordInput);
        Button signupButton = findViewById(R.id.buttonSubmit);

        signupButton.setOnClickListener(v -> {
            String usernameInput = username.getText().toString();
            String emailInput = userEmail.getText().toString();
            String passInput = userPassword.getText().toString();

            if (usernameInput.isEmpty() || emailInput.isEmpty() || passInput.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save user to Room, then store their ID in prefs
            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(this);
                long newUserId = db.userDao().insert(new User(usernameInput, emailInput));

                runOnUiThread(() -> {
                    getSharedPreferences("UserSettings", MODE_PRIVATE).edit()
                            .putString("username", usernameInput)
                            .putString("email", emailInput)
                            .putString("password", passInput)
                            .putBoolean("isLoggedIn", true)
                            .putInt("userId", (int) newUserId) // store Room ID for later
                            .apply();

                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                });
            }).start();
        });
    }
}