package com.example.project.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class UserWorkout {

    ArrayList<Exercise> exercises;
//    String WorkoutId;
//    String UserId;
//    Date created_at;
//    String Username;

    public UserWorkout() {
    }

    public UserWorkout(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

//    public String getWorkoutId() {
//        return WorkoutId;
//    }

    public String generateWorkoutId()
    {
        String uniqueId = UUID.randomUUID().toString();
        return uniqueId;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    //    public void setWorkoutId(String workoutId) {
//        WorkoutId = workoutId;
//    }
//
//    public String getUserId() {
//        return UserId;
//    }
//
//    public void setUserId(String userId) {
//        UserId = userId;
//    }
//
//    public Date getCreated_at() {
//        return created_at;
//    }
//
//    public void setCreated_at(Date created_at) {
//        this.created_at = created_at;
//    }
//
//    public String getUsername() {
//        return Username;
//    }
//
//    public void setUsername(String username) {
//        Username = username;
//    }
}
