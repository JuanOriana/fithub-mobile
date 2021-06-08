package com.example.fithub_mobile.routine;

import java.util.Objects;

public class RoutineCardData {
    private  Integer id;
    private String title;
    private String desc;
    private Integer rating;

    public RoutineCardData(Integer id, String title, String desc, Integer rating) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.rating = rating;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutineCardData that = (RoutineCardData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}