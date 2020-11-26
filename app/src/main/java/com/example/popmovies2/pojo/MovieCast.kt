package com.example.popmovies2.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class MovieCast {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("cast")
    @Expose
    var cast: List<Cast>? = null

    @SerializedName("crew")
    @Expose
    var crew: List<Crew>? = null

}