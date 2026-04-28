package com.example.android3081;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TripWithEvents {
    @Embedded
    public Trip trip;

    @Relation(parentColumn = "id", entityColumn = "trip_id")
    public List<Event> events;
}