package com.example.fithub_mobile.routine;

import java.util.Objects;

public class RoutineCardData {
    private  Integer id;
    private String title;
    private String desc;
    private Integer rating;
    private String userName;
    private String userImg;
    private String difficulty;

    public RoutineCardData(Integer id, String title, String desc, Integer rating,
                           String userName, String userImg, String difficulty) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.rating = rating;
        this.userName = userName;
        this.userImg = userImg;
        this.difficulty = difficulty;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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