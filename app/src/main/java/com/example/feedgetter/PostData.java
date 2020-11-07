package com.example.feedgetter;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

public class PostData {
    @SerializedName("image")
    public String image;
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;

    public int likes;
    public int comment;
    public int share;

    public PostData(String image, String name, String description, int like, int comment, int share) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.likes = like;
        this.comment = comment;
        this.share = share;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getLikes() {
        return likes;
    }

    public int getComment() {
        return comment;
    }

    public int getShare() {
        return share;
    }
}
