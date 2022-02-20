package com.example.UCESAPI.model;

public enum TypeUser {
    ANONYMOUS("Anonymous"),
    STUDENT("Student"),
    ADMINISTRATOR("Administrator");

    private String description;

    TypeUser(String description){this.description = description;}

}
