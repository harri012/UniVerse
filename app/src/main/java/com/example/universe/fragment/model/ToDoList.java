package com.example.universe.fragment.model;

public class ToDoList extends TaskIDRetrieve {

    private String task, due;
    private int status;

    public String getTask() {
        return task;
    }

    public String getDue() {
        return due;
    }

    public int getStatus() {
        return status;
    }
}
