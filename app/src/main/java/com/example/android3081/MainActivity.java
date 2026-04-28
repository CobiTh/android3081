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

        if (!loggedIn) {
            setContentView(R.layout.welcome_activity);

            TextView welcomeText = findViewById(R.id.Welcome);

            ViewCompat.setOnApplyWindowInsetsListener(welcomeText, (view, insets) -> {
                int cutoutTop = insets.getInsets(WindowInsetsCompat.Type.displayCutout()).top;
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                params.topMargin = cutoutTop + (int) (16 * getResources().getDisplayMetrics().density);
                view.setLayoutParams(params);
                return insets;
            });

            Button loginButton = findViewById(R.id.userLogin);
            Button signUpButton = findViewById(R.id.userSignUp);

            loginButton.setOnClickListener(v ->
                    startActivity(new Intent(this, LoginActivity.class)));
            signUpButton.setOnClickListener(v ->
                    startActivity(new Intent(this, SignUpActivity.class)));

        } else {
            setContentView(R.layout.activity_main);
            TextView tripNameText = findViewById(R.id.tripName);
            TextView greetingText = findViewById(R.id.textViewGreeting);
            EditText inputField = findViewById(R.id.editTextInput);
            Button eventButton = findViewById(R.id.buttonSubmit);
            LinearLayout inputContainer = findViewById(R.id.inputContainer);
            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            NavigationView navView = findViewById(R.id.nav_view);

            navView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    // handle home
                } else if (id == R.id.nav_profile) {
                    // handle profile
                }
                drawerLayout.closeDrawers();
                return true;
            });

            RecyclerView tripRecyclerView = findViewById(R.id.tripRecyclerView);
            tripRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Trip> tripList = new ArrayList<>();
            TripAdapter adapter = new TripAdapter(tripList);
            tripRecyclerView.setAdapter(adapter);

            // Build user from prefs
            user = new User(
                    prefs.getString("username", ""),
                    prefs.getString("email", "")
            );
            greetingText.setText("Hello " + user.getUsername());

            // Load this user's trips from Room on startup
            int userId = prefs.getInt("userId", -1);
            AppDatabase db = AppDatabase.getInstance(this);

            new Thread(() -> {
                List<Trip> savedTrips = db.tripDao().getTripsForUser(userId);
                runOnUiThread(() -> {
                    if (!savedTrips.isEmpty()) {
                        tripList.addAll(savedTrips);
                        adapter.notifyDataSetChanged();

                        // skip input screen, go straight to trip list
                        inputContainer.setVisibility(View.GONE);
                        tripRecyclerView.setVisibility(View.VISIBLE);
                        eventButton.setText("add event");
                    }
                });
            }).start();

            eventButton.setOnClickListener(v -> {
                if (eventButton.getText().equals("make trip")) {
                    String text = inputField.getText().toString();
                    if (text.isEmpty()) return;

                    new Thread(() -> {
                        long newTripId = db.tripDao().insert(new Trip(userId, text));
                        Trip savedTrip = db.tripDao().getTripById((int) newTripId);


                        runOnUiThread(() -> {
                            tripList.add(savedTrip);
                            adapter.notifyItemInserted(tripList.size() - 1);

                            Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_up_out);
                            slideOut.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {}

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    inputContainer.setVisibility(View.GONE);
                                        inputContainer.setVisibility(View.GONE);

                                        tripNameText.setText(text);
                                        tripNameText.setVisibility(View.VISIBLE);
                                        tripNameText.setAlpha(0f);
                                        tripNameText.animate()
                                                .alpha(1f)
                                                .setDuration(400)
                                                .start();

                                        tripRecyclerView.setTranslationY(1000f);
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
                            eventButton.setText("add event");
                        });
                    }).start();

                //if tripList isn't empty spawn a fragment ? maybe another way to do this because I'm thinking about
                    // making tripList an eventList with no trip we will see!
                } else {
                    if (!tripList.isEmpty()) {
                        Trip selectedTrip = tripList.get(tripList.size() - 1);
                        EventFragment fragment = EventFragment.newInstance(selectedTrip);
                        fragment.show(getSupportFragmentManager(), "event");
                    }
                }
            });

            findViewById(R.id.buttonDrawerSignout).setOnClickListener(v -> {
                getSharedPreferences("UserSettings", MODE_PRIVATE).edit().clear().apply();
                user = null;
                startActivity(new Intent(this, MainActivity.class));
                finish();
            });
        }
    }
}