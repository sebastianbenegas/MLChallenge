package com.example.mlchallenge.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paging {

    @SerializedName("total")
    @Expose
    private Integer total;

    @SerializedName("offset")
    @Expose
    private Integer offset;

    @SerializedName("limit")
    @Expose
    private Integer limit;

    @SerializedName("primary_results")
    @Expose
    private Integer primaryResults;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPrimaryResults() {
        return primaryResults;
    }

    public void setPrimaryResults(Integer primaryResults) {
        this.primaryResults = primaryResults;
    }
}
