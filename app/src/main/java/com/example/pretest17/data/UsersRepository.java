package com.example.pretest17.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.example.pretest17.data.module.GithubData;

/**
 * You only ask the app should have remote data,
 * so I simplify the UsersLocalRepository and UsersRemoteRepository into UsersRepository.
 *
 * Otherwise, I will design the UsersRepository which UsersLocalRepository and UsersRemoteRepository were injected into.
 *
 */
public class UsersRepository implements UsersDataSource {

    private static UsersRepository mInstance;
    private static RequestQueue mRequestQueue;

    private UsersRepository(Context context){
        mRequestQueue = getRequestQueue(context);
    }

    public static synchronized UsersRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UsersRepository(context.getApplicationContext());
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = com.android.volley.toolbox.Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    @Override
    public void getUsers(@NonNull String query, int page, @NonNull Response.Listener<GithubData> onSuccessResponse, @NonNull Response.ErrorListener onErrorResponse) {
        String url = "https://api.github.com/search/users?q="+query+"m&per_page=20&page="+page;
        GsonRequest gsonRequest = new GsonRequest<>(Request.Method.GET, url, GithubData.class, null, null, onSuccessResponse, onErrorResponse);
        mRequestQueue.add(gsonRequest);
    }
}
