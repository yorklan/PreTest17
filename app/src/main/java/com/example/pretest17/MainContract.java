package com.example.pretest17;

import androidx.annotation.NonNull;

import com.example.pretest17.data.module.User;

import java.util.List;

public interface MainContract {

    interface View {

        void showUserCards(@NonNull List<User> userList, boolean isUserListEnd);

        void showError(@NonNull String hint);

        void showNoReuslt(@NonNull String hint);

    }

    interface Presenter {

        void getUsersData(@NonNull String query);
    }
}
