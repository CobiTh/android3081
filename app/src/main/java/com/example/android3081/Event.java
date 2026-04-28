package com.example.android3081;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "events",
        foreignKeys = @ForeignKey(
                entity = Trip.class,
                parentColumns = "id",
                childColumns = "trip_id",
                onDelete = ForeignKey.CASCADE))
public class Event implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "trip_id")
    private int tripId;

    @ColumnInfo(name = "event_type")
    private String eventType;

    @ColumnInfo(name = "event_name")
    private String name;

    @ColumnInfo(name = "review")
    private String review;

    @ColumnInfo(name = "stars")
    private float stars;

//    public Event(int tripId, String eventType) {
//        this.tripId = tripId;
//        this.eventType = eventType;
//    }

    public Event(int tripId, String name, String review, float stars) {
        this.tripId = tripId;
        this.eventType = eventType;
        this.name = name;
        this.review = review;
        this.stars = stars;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTripId() { return tripId; }
    public void setTripId(int tripId) { this.tripId = tripId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
    public float getStars() { return stars; }
    public void setStars(float stars) { this.stars = stars; }
}