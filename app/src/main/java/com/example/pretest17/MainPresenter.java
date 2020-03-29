package com.example.pretest17;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.pretest17.data.UsersDataSource;
import com.example.pretest17.data.module.GithubData;

public class MainPresenter implements MainContract.Presenter {

    private UsersDataSource mUsersDataSource;
    private MainContract.View mMainView;

    MainPresenter(@NonNull UsersDataSource usersDataSource, @NonNull MainContract.View view){
        mUsersDataSource = usersDataSource;
        mMainView = view;
    }

    @Override
    public void getUsersData() {
        mUsersDataSource.getUsers("tom", 1, new Response.Listener<GithubData>() {
            @Override
            public void onResponse(GithubData response) {
                Log.e("VolleyTest",response.getItems().size()+","+response.getTotalCount());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
            }
        });
    }
}
