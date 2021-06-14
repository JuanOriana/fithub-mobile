
package com.example.fithub_mobile.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullCycleExercise implements Comparable<FullCycleExercise> {

    @SerializedName("exercise")
    @Expose
    private FullExercise exercise;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("repetitions")
    @Expose
    private int repetitions;
    @SerializedName("metadata")
    @Expose
    private Object metadata;

    private String img;

    private FullCycle cycle;

    /**
     * No args constructor for use in serialization
     *
     */
    public FullCycleExercise() {
    }

    /**
     *
     * @param duration
     * @param metadata
     * @param exercise
     * @param repetitions
     * @param order
     */
    public FullCycleExercise(FullExercise exercise, int order, int duration, int repetitions, Object metadata) {
        super();
        this.exercise = exercise;
        this.order = order;
        this.duration = duration;
        this.repetitions = repetitions;
        this.metadata = metadata;
    }

    public FullExercise getExercise() {
        return exercise;
    }

    public void setExercise(FullExercise exercise) {
        this.exercise = exercise;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public FullCycle getCycle() {
        return cycle;
    }

    public void setCycle(FullCycle cycle) {
        this.cycle = cycle;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int compareTo(FullCycleExercise o) {
        if (this.cycle != null && o.cycle != null){
            int c= this.cycle.getOrder() - o.cycle.getOrder();
            if (c!=0)
                return c;
        }
        return this.getOrder() - o.getOrder();
    }
}