package com.example.project.data;

public class ExerciseInfo {

    String name;
    String reps;
    String sets;

    public ExerciseInfo(String name, String reps, String sets) {
        this.name = name;
        this.reps = reps;
        this.sets = sets;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }
}

