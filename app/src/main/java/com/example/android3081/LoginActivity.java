package com.example.android3081;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
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
import android.widget.Toast;

/**
 * in the future, this should confirm with the dastabase that this exists
 * for now, check with stored params :)
 */
public class LoginActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText userEmail = findViewById((R.id.emailInput));
        EditText userPassword = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.buttonSubmit);

        //this is very scuffed and technically makes no sense but that's ok!!
        loginButton.setOnClickListener(v -> {
            String email = userEmail.getText().toString();
            String password = userPassword.getText().toString();

            SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
            String storedEmail = prefs.getString("email", "");
            String storedPass = prefs.getString("password","");
            if(email.equals(storedEmail) && password.equals(storedPass)){
                SharedPreferences.Editor editor = getSharedPreferences("UserSettings",MODE_PRIVATE).edit();
                editor.putBoolean("isLoggedIn",true);
                editor.apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            else if (!email.equals(storedEmail)) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
