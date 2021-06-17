package com.example.fithub_mobile.ui.excercise;

import java.util.Objects;

public class ExerciseData {
    private  Integer id;
    private String title;
    private String desc;
    private Integer reps;
    private Integer secs;
    private String img;

    public ExerciseData(Integer id, String title, String desc, Integer reps, Integer secs, String img) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.reps = reps;
        this.secs = secs;
        this.img = img;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getSecs() {
        return secs;
    }

    public void setSecs(Integer secs) {
        this.secs = secs;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseData that = (ExerciseData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

