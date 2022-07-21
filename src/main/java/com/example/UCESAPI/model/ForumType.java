package com.example.UCESAPI.model;

public enum ForumType {

    RECOMMENDATION("Recommendation"),
    QUERY("Query");

    private String description;

    ForumType(String description) {
        this.description = description;
    }

    public static ForumType find(final String value) {
        for (ForumType f : values()) {
            if (f.toString().equalsIgnoreCase(value)) {
                return f;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid ForumType: %s", value));
    }

    public String getDescription() {
        return description;
    }
}
