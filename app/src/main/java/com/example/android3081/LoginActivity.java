package com.example.android3081;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText userEmail = findViewById(R.id.emailInput);
        EditText userPassword = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.buttonSubmit);

        loginButton.setOnClickListener(v -> {
            String email = userEmail.getText().toString();
            String password = userPassword.getText().toString();

            SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
            String storedEmail = prefs.getString("email", "");
            String storedPass = prefs.getString("password", "");

            if (email.equals(storedEmail) && password.equals(storedPass)) {

                new Thread(() -> {
                    AppDatabase db = AppDatabase.getInstance(this);
                    User user = db.userDao().getUserByEmail(email);

                    runOnUiThread(() -> {
                        if (user != null) {
                            prefs.edit()
                                    .putBoolean("isLoggedIn", true)
                                    .putInt("userId", user.getId())
                                    .apply();

                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();

            } else if (!email.equals(storedEmail)) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}