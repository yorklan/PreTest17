package com.example.pretest17;

import androidx.annotation.NonNull;

import com.example.pretest17.data.module.User;

import java.util.List;

public interface MainContract {

    interface View {

        void newUserCards(@NonNull List<User> userList, int totalCount);

        void updateUserCards(@NonNull List<User> userList);

        void forceUserCardsEnd();

        void showError(int stringId);

        void showNoResult(int stringId);

        void showAlert(int stringId);

    }

    interface Presenter {

        void newSearchUsers(@NonNull String query);

        void continueSearchUsers();
    }
}
