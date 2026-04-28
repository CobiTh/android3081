package com.example.android3081;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TripDao {

    @Insert
    long insert(Trip trip); // returns the new trip's id, useful for then adding events to it

    @Update
    void update(Trip trip);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * FROM trips")
    List<Trip> getAllTrips();

    @Query("SELECT * FROM trips WHERE user_id = :userId")
    List<Trip> getTripsForUser(int userId);
    @Query("SELECT * FROM trips WHERE id = :tripId")
    Trip getTripById(int tripId);

    @Transaction
    @Query("SELECT * FROM trips")
    List<TripWithEvents> getAllTripsWithEvents();

    @Transaction
    @Query("SELECT * FROM trips WHERE id = :tripId")
    TripWithEvents getTripWithEvents(int tripId);
}