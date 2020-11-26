package com.example.popmovies2.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Actor {
    @SerializedName("cast")
    @Expose
    var works: List<Result>? = null

    @SerializedName("crew")
    @Expose
    var crew: List<Result>? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

}