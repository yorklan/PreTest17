package com.example.pretest17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pretest17.data.UsersRepository;
import com.example.pretest17.data.module.User;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private MainContract.Presenter mMainPresenter;
    private UserCardAdapter mUserCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainPresenter = new MainPresenter(UsersRepository.getInstance(this), this);
        buildSearchView();
        buildRecyclerView();
    }

    private void buildSearchView() {
        SearchView searchView = findViewById(R.id.search_main);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMainPresenter.getUsersData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMainPresenter.getUsersData(newText);
                return false;
            }
        });
        searchView.setSubmitButtonEnabled(false);
        searchView.setIconified(false);
        searchView.clearFocus();
    }

    private void buildRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_main);

        // build & set Adapter
        mUserCardAdapter = new UserCardAdapter();
        recyclerView.setAdapter(mUserCardAdapter);

        // build & set LayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mUserCardAdapter.getSpanCount(position);
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void showUserCards(@NonNull List<User> userList, boolean isUserListEnd) {
        
    }

    @Override
    public void showError(@NonNull String hint) {

    }

    @Override
    public void showNoReuslt(@NonNull String hint) {

    }
}
