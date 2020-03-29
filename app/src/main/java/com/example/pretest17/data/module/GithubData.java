package com.example.pretest17.data.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GithubData {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("items")
    private List<User> items = null;

    public List<User> getItems() {
        return items;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
