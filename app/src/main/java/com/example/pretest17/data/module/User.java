package com.example.pretest17.data.module;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("login")
    private String name;

    @SerializedName("avatar_url")
    private String avatar;

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
}
