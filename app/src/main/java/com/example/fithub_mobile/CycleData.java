package com.example.fithub_mobile;

import java.util.Objects;

public class CycleData {
    private  Integer id;
    private String title;
    private Integer reps;

    public CycleData(Integer id, String title, Integer reps) {
        this.id = id;
        this.title = title;
        this.reps = reps;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CycleData cycleData = (CycleData) o;
        return Objects.equals(id, cycleData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
