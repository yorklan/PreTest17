package com.example.pretest17.data.module;

import com.google.gson.annotations.SerializedName;

public class User {

    // remote attribute
    @SerializedName("login")
    private String name;

    @SerializedName("avatar_url")
    private String avatar;

    // local attribute
    public static final int CARD_TYPE_1_1 = 0, CARD_TYPE_2_2 = 1, CARD_TYPE_3_3 = 2;
    private int cardType = -1;

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public int getCardType() {
        return cardType;
    }

    public int getSpanCount() {
        if(cardType==CARD_TYPE_1_1){
            return 1;
        }else {
            return 2;
        }
    }
}
