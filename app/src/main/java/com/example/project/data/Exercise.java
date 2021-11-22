package com.example.project.data;

import java.util.ArrayList;

public class Exercise {

    ArrayList<ExerciseInfo> ExerciseInfo;
    int id;
    String name;

    public Exercise(ArrayList<com.example.project.data.ExerciseInfo> exerciseInfo, String name) {
        this.name = name;
        ExerciseInfo = exerciseInfo;
    }

    public Exercise() {
    }

    public ArrayList<com.example.project.data.ExerciseInfo> getExerciseInfo() {
        return ExerciseInfo;
    }

    public void setExerciseInfo(ArrayList<com.example.project.data.ExerciseInfo> exerciseInfo) {
        ExerciseInfo = exerciseInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
