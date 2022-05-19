package com.example.UCESAPI.model;

public enum UserType {
    ANONYMOUS("Anonymous"),
    STUDENT("Student"),
    ADMINISTRATOR("Administrator");

    private String description;

    UserType(String description){this.description = description;}

    public static UserType find(final String value) {
        for (UserType f : values()) {
            if (f.toString().equalsIgnoreCase(value)) {
                return f;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid UserType: %s", value));
    }

}
