package com.example.fithub_mobile.backend.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cycles {

    @SerializedName("totalCount")
    @Expose
    private int totalCount;
    @SerializedName("orderBy")
    @Expose
    private String orderBy;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("content")
    @Expose
    private List<FullCycle> content = null;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("isLastPage")
    @Expose
    private boolean isLastPage;

    /**
     * No args constructor for use in serialization
     *
     */
    public Cycles() {
    }

    /**
     *
     * @param size
     * @param isLastPage
     * @param orderBy
     * @param page
     * @param totalCount
     * @param content
     * @param direction
     */
    public Cycles(int totalCount, String orderBy, String direction, List<FullCycle> content, int size, int page, boolean isLastPage) {
        super();
        this.totalCount = totalCount;
        this.orderBy = orderBy;
        this.direction = direction;
        this.content = content;
        this.size = size;
        this.page = page;
        this.isLastPage = isLastPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<FullCycle> getContent() {
        return content;
    }

    public void setContent(List<FullCycle> content) {
        this.content = content;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

}
