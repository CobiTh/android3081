package com.example.android3081;

public class Event {

    private String eventType;
    private int id;

    public Event(String type) {
        eventType = type;
        id = Counter.getCounter().getIdCountAndIncrement();
    }

    public String getEventType() {
        return eventType;
    }

    public int getId() {
        return id;
    }
}