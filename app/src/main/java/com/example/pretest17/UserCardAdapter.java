package com.example.pretest17;

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

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class UserCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> mUserList = new ArrayList<>();
    private int totalCount;

    private int status;
    private final int VIEW_TYPE_CARDS = 0, VIEW_TYPE_LOADING = 1, VIEW_TYPE_NO_RESULT = 2, VIEW_TYPE_ERROR = 3;
    private String hint;

    private class UserCardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAvatar,imgPlaceHolder;
        private TextView textName;

        UserCardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_card_user_avatar);
            textName = itemView.findViewById(R.id.text_card_user_name);
            imgPlaceHolder = itemView.findViewById(R.id.img_card_user_placeholder);
        }
    }

    private class HintViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgHint;
        private TextView textHint;
        private Button btnRetry;

        HintViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHint = itemView.findViewById(R.id.img_card_hint);
            textHint = itemView.findViewById(R.id.text_card_hint);
            btnRetry = itemView.findViewById(R.id.btn_card_hint_retry);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onBtnRetryClick();
                }
            });
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

    UserCardAdapter(String hint) {
        status = VIEW_TYPE_NO_RESULT;
        this.hint = hint;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_CARDS:
                return new UserCardViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_user, parent, false));
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
        if(holder instanceof HintViewHolder){
            setHintView((HintViewHolder) holder);
        } else if(holder instanceof UserCardViewHolder){
            setUserCardView((UserCardViewHolder) holder, position);
        }
    }

    private void setHintView(HintViewHolder holder){
        holder.textHint.setText(hint);
        if (status == VIEW_TYPE_NO_RESULT) {
            holder.imgHint.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_no_search));
            holder.btnRetry.setVisibility(View.GONE);
        } else {
            holder.imgHint.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_error));
            holder.btnRetry.setVisibility(View.VISIBLE);
        }
    }

    private void setUserCardView(UserCardViewHolder holder, int position){
        User user = mUserList.get(position);
        if(user!=null){
            Glide.with(holder.itemView.getContext())
                    .load(user.getAvatar())
                    .transition(withCrossFade())
                    .centerCrop()
                    .circleCrop()
                    .into(holder.imgAvatar);
            holder.textName.setText(user.getName());
            if(user.getCardType()==User.CARD_TYPE_3_3){
                holder.imgPlaceHolder.setVisibility(View.VISIBLE);
            }else {
                holder.imgPlaceHolder.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mUserList.isEmpty()) {
            return status;
        } else if (position >= mUserList.size()) {
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

    void setStatusError(@NonNull String hint) {
        clearUserList();
        setStatus(VIEW_TYPE_ERROR, hint);
    }

    void setStatusNoResult(@NonNull String hint) {
        clearUserList();
        setStatus(VIEW_TYPE_NO_RESULT, hint);
    }

    void setStatusLoading() {
        clearUserList();
        setStatus(VIEW_TYPE_LOADING, "");
    }

    void updateStatusCards(@NonNull List<User> newUserList){
        updateUserList(newUserList, -1);
        setStatus(VIEW_TYPE_CARDS, "");
    }

    void newStatusCards(@NonNull List<User> newUserList, int totalCount) {
        clearUserList();
        updateUserList(newUserList, totalCount);
        setStatus(VIEW_TYPE_CARDS, "");
    }

    void forceStatusCardsEnd(){
        this.totalCount = mUserList.size();
    }

    private void clearUserList(){
        mUserList.clear();
    }

    private void updateUserList(@NonNull List<User> newUserList, int totalCount) {
        mUserList.addAll(newUserList);
        this.totalCount = totalCount==-1 ? this.totalCount : totalCount;
    }

    private void setStatus(int status, @NonNull String hint) {
        this.status = status;
        this.hint = hint;
        notifyDataSetChanged();
    }

    int getSpanCount(int position) {
        if (status != VIEW_TYPE_CARDS || position >= mUserList.size()) {
            return 2;
        } else {
            return mUserList.get(position).getSpanCount();
        }
    }
}
