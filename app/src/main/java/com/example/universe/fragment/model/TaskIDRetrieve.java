package com.example.universe.fragment.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class TaskIDRetrieve {

    //exclude the task ID in data manipulation for later
    @Exclude
    public String taskID;

    public <T extends TaskIDRetrieve> T withId(@NonNull final String id){
        this.taskID = id;
        return (T)this;
    }
}
