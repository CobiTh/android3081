package com.example.android3081;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.LinearLayout;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("isLoggedIn", false);

        if(!loggedIn) {
            setContentView(R.layout.welcome_activity);

            TextView welcomeText = findViewById(R.id.Welcome);

            //creates spacer for Welcome text to be below the hole punch of device :)
            ViewCompat.setOnApplyWindowInsetsListener(welcomeText, (view, insets) -> {
                int cutoutTop = insets.getInsets(WindowInsetsCompat.Type.displayCutout()).top;
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                params.topMargin = cutoutTop + (int) (16 * getResources().getDisplayMetrics().density);
                view.setLayoutParams(params);
                return insets;
            });

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
            Button CreateTrip = findViewById(R.id.buttonSubmit);
            LinearLayout inputContainer = findViewById(R.id.inputContainer);
            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            NavigationView navView = findViewById(R.id.nav_view);
            navView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                     // probably gonna be a different button but that's ok
                } else if (id == R.id.nav_profile) {
                    // go to profile
                }
                drawerLayout.closeDrawers();
                return true;
            });
            RecyclerView tripRecyclerView = findViewById(R.id.tripRecyclerView);
            tripRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Trip> tripList = new ArrayList<>();
            TripAdapter adapter = new TripAdapter(tripList);
            tripRecyclerView.setAdapter(adapter);

            user = new User(prefs.getString("username",""),
                    prefs.getString("email",""));

            greetingText.setText("Hello " + user.getName());

            CreateTrip.setOnClickListener(v -> {
                String text = inputField.getText().toString();
                if (text.isEmpty()) return;

                Trip thisTrip = new Trip(text);
                tripList.add(thisTrip);
                adapter.notifyItemInserted(tripList.size() - 1);

                Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_up_out);

                slideOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        inputContainer.setVisibility(View.GONE);
                        tripRecyclerView.setTranslationY(1000f); // fixed value since height is 0
                        tripRecyclerView.setAlpha(0f);
                        tripRecyclerView.setVisibility(View.VISIBLE);
                        tripRecyclerView.animate()
                                .translationY(0f)
                                .alpha(1f)
                                .setDuration(400)
                                .start();
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

                inputContainer.startAnimation(slideOut);

                //set makeTrip button on the button to new content
                Button TEButton = findViewById(R.id.buttonMakeTrip);
                TEButton.setText("add event");
            });

            findViewById(R.id.buttonDrawerSignout).setOnClickListener(v -> {
                getSharedPreferences("UserSettings", MODE_PRIVATE).edit().clear().apply();
                user.clear();
                user = null;
                startActivity(new Intent(this, MainActivity.class));
                finish();
            });
        }

    }
}