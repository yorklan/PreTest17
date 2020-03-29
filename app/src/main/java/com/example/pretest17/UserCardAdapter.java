package com.example.pretest17;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pretest17.data.module.User;

import java.util.ArrayList;
import java.util.List;

public class UserCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> mUserList = new ArrayList<>();
    private boolean isUserListEnd = false;

    private int status;
    private final int VIEW_TYPE_CARDS = 0, VIEW_TYPE_LOADING = 1, VIEW_TYPE_NO_RESULT = 2, VIEW_TYPE_ERROR = 3;
    //FIXME
    private String hint = "test";

    private class UserCardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAvatar;
        private TextView textName;

        UserCardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_card_user_avatar);
            textName = itemView.findViewById(R.id.text_card_user_name);
        }
    }

    private class HintViewHolder extends RecyclerView.ViewHolder {

        HintViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageView imgHint = itemView.findViewById(R.id.img_card_hint);
            TextView textHint = itemView.findViewById(R.id.text_card_hint);
            Button btnRetry = itemView.findViewById(R.id.btn_card_hint_retry);

            textHint.setText(hint);
            if (status == VIEW_TYPE_NO_RESULT) {
                imgHint.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_no_search));
                btnRetry.setVisibility(View.GONE);
            } else {
                imgHint.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_error));
                btnRetry.setVisibility(View.VISIBLE);
            }
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageView imgLoading = itemView.findViewById(R.id.img_card_loading);
            Glide.with(itemView.getContext())
                    .load(R.mipmap.ic_loading)
                    .into(imgLoading);
        }
    }

    UserCardAdapter() {
        status = VIEW_TYPE_LOADING;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_CARDS:
                return new UserCardViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_loading, parent, false));
            case VIEW_TYPE_NO_RESULT:
            case VIEW_TYPE_ERROR:
                return new HintViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_hint, parent, false));
            case VIEW_TYPE_LOADING:
            default:
                return new LoadingViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_loading, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if (mUserList.isEmpty()) {
            return status;
        } else if (position > mUserList.size()) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_CARDS;
        }
    }

    @Override
    public int getItemCount() {
        if (mUserList.isEmpty()) {
            return 1;
        } else {
            return mUserList.size() + 1;
        }
    }

    /**
     * Public Methods
     **/
    private OnItemClickListener onItemClickListener = null;

    public interface OnItemClickListener {
        void onBtnRetryClick();
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    private void setStatus(int status, @NonNull String hint) {
        this.status = status;
        this.hint = hint;
    }

    void setStatusError(@NonNull String hint) {
        setStatus(VIEW_TYPE_ERROR, hint);
    }

    void setStatusNoResult(@NonNull String hint) {
        setStatus(VIEW_TYPE_NO_RESULT, hint);
    }

    void setStatusLoading() {
        setStatus(VIEW_TYPE_LOADING, "");
    }

    void setStatusCards(@NonNull List<User> newUserList, boolean isUserListEnd) {
        setStatus(VIEW_TYPE_CARDS, "");
        updateUserList(newUserList, isUserListEnd);
    }

    private void updateUserList(@NonNull List<User> newUserList, boolean isUserListEnd) {
        mUserList.clear();
        mUserList.addAll(newUserList);
        this.isUserListEnd = isUserListEnd;
    }

    int getSpanCount(int position) {
        if (status != VIEW_TYPE_CARDS || position > mUserList.size()) {
            return 2;
        } else {
            return mUserList.get(position).getSpanCount();
        }
    }
}
