package com.example.UCESAPI.model;

public enum PollType {
    CAREER_POLL(0),
    SUBJECT_POLL(1);
    private final int value;

    PollType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
