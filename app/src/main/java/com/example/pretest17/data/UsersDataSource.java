package com.example.pretest17.data;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.example.pretest17.data.module.GithubData;

public interface UsersDataSource {

    /**
     * The data you ask is simple and single,
     * so I don't design my own interface to handle it.
     *
     * I just set Generic and use the interface that provided by Volley Library.
     */
    void getUsers(@NonNull String query, int page, @NonNull Response.Listener<GithubData> onSuccessResponse, @NonNull Response.ErrorListener onErrorResponse);
}
