package com.example.android3081;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {

    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);

    @Query("SELECT * FROM events WHERE trip_id = :tripId")
    List<Event> getEventsForTrip(int tripId);

    @Query("SELECT * FROM events WHERE id = :eventId")
    Event getEventById(int eventId);

    @Query("DELETE FROM events WHERE trip_id = :tripId")
    void deleteAllEventsForTrip(int tripId);
}