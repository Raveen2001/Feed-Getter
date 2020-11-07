package com.example.feedgetter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataApi {
    @GET("raveen/data.json#")
    Call<List<PostData>> getPosts();
}
