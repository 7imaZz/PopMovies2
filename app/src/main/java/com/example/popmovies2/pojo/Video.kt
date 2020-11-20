package com.example.popmovies2.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Video {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("results")
    @Expose
    var results: List<VideoResult>? = null

}