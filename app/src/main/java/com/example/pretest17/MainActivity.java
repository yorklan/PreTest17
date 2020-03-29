package com.example.pretest17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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
                mUserCardAdapter.setStatusLoading();
                mMainPresenter.newSearchUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mUserCardAdapter.setStatusLoading();
                mMainPresenter.newSearchUsers(newText);
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
        mUserCardAdapter = new UserCardAdapter(getString(R.string.no_result_empty));
        recyclerView.setAdapter(mUserCardAdapter);
        mUserCardAdapter.setOnItemClickListener(new UserCardAdapter.OnItemClickListener() {
            @Override
            public void onBtnRetryClick() {
                Log.e("test","1");
            }
        });

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
    public void newUserCards(@NonNull List<User> userList, int totalCount) {
        mUserCardAdapter.newStatusCards(userList, totalCount);
    }

    @Override
    public void updateUserCards(@NonNull List<User> userList) {
        mUserCardAdapter.updateStatusCards(userList);
    }

    @Override
    public void forceUserCardsEnd() {
        mUserCardAdapter.forceStatusCardsEnd();
    }

    @Override
    public void showError(int stringId) {
        mUserCardAdapter.setStatusError(getString(stringId));
    }

    @Override
    public void showNoResult(int stringId) {
        mUserCardAdapter.setStatusNoResult(getString(stringId));
    }

    @Override
    public void showAlert(int stringId) {
        // FIXME
    }
}
