package com.example.pretest17;


import androidx.annotation.NonNull;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.pretest17.data.UsersDataSource;
import com.example.pretest17.data.module.GithubData;
import com.example.pretest17.data.module.User;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private UsersDataSource mUsersDataSource;
    private MainContract.View mMainView;

    private boolean isUpdateLock;
    private int pageIndex;
    private String lastQuery;

    MainPresenter(@NonNull UsersDataSource usersDataSource, @NonNull MainContract.View view){
        mUsersDataSource = usersDataSource;
        mMainView = view;
        initSearchParams();
    }

    private void initSearchParams() {
        isUpdateLock = false;
        pageIndex = 1;
        lastQuery = "";
    }

    @Override
    public void newSearchUsers(@NonNull String query) {
        initSearchParams();
        if(query.isEmpty()){
            mMainView.showNoResult(R.string.no_result_empty);
        }else {
            lastQuery = query;
            getNewUserData(query);
        }
    }

    private void getNewUserData(@NonNull String query){
        mUsersDataSource.getUsers(query, pageIndex, new Response.Listener<GithubData>() {
            @Override
            public void onResponse(GithubData response) {
                List<User> userList = response.getItems();
                if(userList == null || userList.isEmpty()){
                    mMainView.showNoResult(R.string.no_result_query);
                }else {
                    mMainView.newUserCards(response.getItems(), response.getTotalCount());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NetworkError){
                    mMainView.showError(R.string.error_network);
                }else if(error.networkResponse!=null && error.networkResponse.statusCode==403){
                    mMainView.showError(R.string.error_limited);
                }else {
                    mMainView.showNoResult(R.string.error_server);
                }
            }
        });
    }

    @Override
    public void updateSearchUsers(boolean isSearch) {
        if(isSearch && !isUpdateLock){
            getUpdateUserData();
        }
    }

    private void getUpdateUserData(){
        isUpdateLock = true;
        mUsersDataSource.getUsers(lastQuery, ++pageIndex, new Response.Listener<GithubData>() {
            @Override
            public void onResponse(GithubData response) {
                isUpdateLock = false;
                if(response.getItems() == null || response.getItems().isEmpty()){
                    mMainView.forceUserCardsEnd();
                }else {
                    mMainView.updateUserCards(response.getItems());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isUpdateLock = false;
                if(error instanceof NetworkError){
                    mMainView.showAlert(R.string.error_network);
                }else {
                    NetworkResponse networkResponse = error.networkResponse;
                    if(networkResponse!=null){
                        if(networkResponse.statusCode==403){
                            mMainView.showAlert(R.string.error_limited);
                        }else if(networkResponse.statusCode==422){
                            mMainView.forceUserCardsEnd();
                        }else {
                            mMainView.showAlert(R.string.error_server);
                        }
                    }else {
                        mMainView.showAlert(R.string.error_server);
                    }
                }
            }
        });
    }

}
