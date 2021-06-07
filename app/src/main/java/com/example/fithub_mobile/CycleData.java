package com.example.fithub_mobile;

import java.util.Objects;

public class CycleData {
    private  Integer id;
    private String title;
    private String desc;

    public CycleData(Integer id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
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
