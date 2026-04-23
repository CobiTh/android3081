package com.example.android3081;

import java.util.List;
import java.util.ArrayList;

public class Trip {

    private int id;
    private String tripName;
    private List<Event> events;

    public Trip(String name) {
        this.id = Counter.getCounter().getIdCountAndIncrement();
        this.tripName = name;
        this.events = new ArrayList<Event>();
    }

    public int getId() {
        return id;
    }

    public String getTripName() {
        return tripName;
    }

    public boolean addEvent(Event newEvent) {
        if (true) { //placeholder
            events.add(newEvent);
            return true;
        }
        else {
            return false;
        }
    }

    public Event makeEvent(String eventType) {
        return new Event(eventType);
    }

    public boolean removeEvent(int idToRemove) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId() == idToRemove) {
                events.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean swapEvent(int idx1, int idx2) {
        if (true) { //placeholder
            Event tempEvent = events.get(idx1);
            events.set(idx1, events.get(idx2));
            events.set(idx2, tempEvent);
            return true;
        }
        else {
            return false;
        }
    }
}