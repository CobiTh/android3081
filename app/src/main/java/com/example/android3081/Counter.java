package com.example.android3081;

public class Counter {
    private int idCount;
    private static Counter counter;

    private Counter() {
        idCount = 0;
    }

    public static Counter getCounter() {
        if (counter == null) {
            return new Counter();
        }
        else {
            return counter;
        }
    }

    public int getIdCountAndIncrement() {
        return idCount++;
    }
}
