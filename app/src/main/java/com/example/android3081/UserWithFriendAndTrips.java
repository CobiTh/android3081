package com.example.android3081;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithFriendAndTrips {

    @Embedded
    public User user;

    @Relation(parentColumn = "id", entityColumn = "user_id")
    public List<Friend> friends; // list for future, will just be one for now

    @Relation(parentColumn = "id", entityColumn = "user_id")
    public List<Trip> trips;
}