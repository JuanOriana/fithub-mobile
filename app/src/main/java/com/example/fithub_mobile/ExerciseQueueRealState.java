package com.example.fithub_mobile;

import com.example.fithub_mobile.excercise.ExerciseData;

import java.util.ArrayList;

public class ExerciseQueueRealState
{
    // static variable single_instance of type Singleton
    private static ExerciseQueueRealState single_instance = null;

    private ArrayList<ExerciseData> exercises = new ArrayList<>();
    private ArrayList<ExerciseData> doneExercises = new ArrayList<>();
    private ExerciseData currentExercise = null;

    public ArrayList<ExerciseData> getExercises() {
        return exercises;
    }

    public ArrayList<ExerciseData> getDoneExercises() {
        return doneExercises;
    }

    public ExerciseData getCurrentExercise() {
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

    public void setNewRoutine(ArrayList<ExerciseData> exercises){
        this.exercises = new ArrayList<>(exercises);
        this.doneExercises = new ArrayList<>();
        currentExercise = null;
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