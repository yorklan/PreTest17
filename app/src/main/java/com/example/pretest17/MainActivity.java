package com.example.pretest17;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pretest17.data.UsersRepository;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private MainContract.Presenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainPresenter = new MainPresenter(UsersRepository.getInstance(this), this);
        mMainPresenter.getUsersData();
    }
}
