package com.example.android3081;

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
        setContentView(R.layout.activity_main);

        // Reference the TextView from your XML
        TextView greetingText = findViewById(R.id.textViewGreeting);
        EditText inputField = findViewById(R.id.editTextInput);
        Button submitButton = findViewById(R.id.buttonSubmit);

        submitButton.setOnClickListener(v -> {
            String text = inputField.getText().toString();

            Trip thisTrip = new Trip(text);
            System.out.println("New Trip ID: " + thisTrip.getId());

             greetingText.setText("Hello " + text);
        });
    }
}