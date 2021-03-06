package com.example.fithub_mobile.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class FullRoutine {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("averageRating")
    @Expose
    private int averageRating;
    @SerializedName("isPublic")
    @Expose
    private boolean isPublic;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("user")
    @Expose
    private PublicUser publicUser;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("metadata")
    @Expose
    private Object metadata;

    private boolean isFavourite = false;

    /**
     * No args constructor for use in serialization
     *
     */
    public FullRoutine() {
    }

    /**
     *
     * @param date
     * @param difficulty
     * @param metadata
     * @param averageRating
     * @param name
     * @param isPublic
     * @param id
     * @param detail
     * @param category
     * @param publicUser
     */
    public FullRoutine(int id, String name, String detail, long date, int averageRating, boolean isPublic, String difficulty, PublicUser publicUser, Category category, Object metadata) {
        super();
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.date = date;
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.publicUser = publicUser;
        this.category = category;
        this.metadata = metadata;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public PublicUser getUser() {
        return publicUser;
    }

    public void setUser(PublicUser publicUser) {
        this.publicUser = publicUser;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullRoutine that = (FullRoutine) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
