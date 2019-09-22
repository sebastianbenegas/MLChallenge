package com.example.mlchallenge.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {

    public SearchResponse() {
        this.results = new ArrayList<>();
    }

    @SerializedName("site_id")
    @Expose
    private String siteId;

    @SerializedName("query")
    @Expose
    private String query;

    @SerializedName("paging")
    @Expose
    private Paging paging;

    private List<Product> results;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<Product> getResults() {
        return results;
    }

    public void setResults(List<Product> results) {
        this.results = results;
    }
}
