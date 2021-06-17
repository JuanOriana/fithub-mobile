package com.example.fithub_mobile.ui.execution;

import com.example.fithub_mobile.backend.models.FullCycleExercise;

import java.util.ArrayList;

public class ExerciseQueueRealState
{
    // static variable single_instance of type Singleton
    private static ExerciseQueueRealState single_instance = null;

    private ArrayList<FullCycleExercise> exercises = new ArrayList<>();
    private ArrayList<FullCycleExercise> doneExercises = new ArrayList<>();
    private FullCycleExercise currentExercise = null;
    int cycleCount = 0;

    public ArrayList<FullCycleExercise> getExercises() {
        return exercises;
    }

    public ArrayList<FullCycleExercise> getDoneExercises() {
        return doneExercises;
    }

    public FullCycleExercise getCurrentExercise() {
        return currentExercise;
    }

    public double ratio(){
        int nextLen = exercises.size();
        int doneLen = doneExercises.size();
        if (doneLen == 0)
            return 0;
        return doneLen*1.0/(nextLen+doneLen);
    }


    public void setNewRoutine(int id){
        return;
    }

    public void setNewRoutine(ArrayList<FullCycleExercise> exercises){
        this.exercises = new ArrayList<>(exercises);
        this.doneExercises = new ArrayList<>();
        currentExercise = null;
        cycleCount = 0;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public void setCycleCount(int cycleCount) {
        this.cycleCount = cycleCount;
    }

    public void addCycle() {
        this.cycleCount = cycleCount+1;
    }

    public int setNextExercise(){
        if (exercises.size() == 0) {
            return -1;
        }
        if (currentExercise != null){
            doneExercises.add(currentExercise);
        }
        currentExercise = exercises.remove(0);
        return 0;
    }

    public int setPrevExercise(){
        if (doneExercises.size() == 0)
            return -1;
        exercises.add(0,currentExercise);
        currentExercise = doneExercises.remove(doneExercises.size()-1);
        return 0;
    }

    // static method to create instance of Singleton class
    public static ExerciseQueueRealState getInstance()
    {
        if (single_instance == null)
            single_instance = new ExerciseQueueRealState();

        return single_instance;
    }
}