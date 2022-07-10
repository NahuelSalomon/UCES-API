package com.example.UCESAPI.exception.model;

public enum UserType {
    ROLE_ANONYMOUS("Anonymous"),
    ROLE_STUDENT("Student"),
    ROLE_ADMINISTRATOR("Administrator");

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
