package com.example.android3081;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

public class EventFragment extends DialogFragment {

    public static EventFragment newInstance(Trip trip) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putSerializable("trip", trip);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        if(getArguments() == null) {
            dismiss();
            return view;
        }
        Trip trip = (Trip) getArguments().getSerializable("trip");

        TextInputEditText eventName = view.findViewById(R.id.editTextEventName);
        TextInputEditText reviewText = view.findViewById(R.id.editTextReview);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        Button btnSubmit = view.findViewById(R.id.btnSubmitReview);

        btnSubmit.setOnClickListener(v -> {
            String name = eventName.getText().toString();
            String review = reviewText.getText().toString();
            float stars = ratingBar.getRating();

            if (name.isEmpty()) return;

            Event newEvent = new Event(trip.getId(), name, review, stars);

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(requireContext());
                db.eventDao().insert(newEvent);

                requireActivity().runOnUiThread(() -> dismiss());
            }).start();
        });

        return view;
    }
}