package com.example.popmovies2.pojo

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class Result() : Parcelable{
    var vote_count: Int? = null
    var poster_path: String? = null
    @PrimaryKey
    var id: Int? = null
    var adult: Boolean? = null
    var original_language: String? = null
    var original_title: String? = null
    var title: String? = null
    var vote_average: Float? = null
    var overview: String? = null
    var release_date: String? = null
    var job: String? = null

    constructor(parcel: Parcel) : this() {
        vote_count = parcel.readValue(Int::class.java.classLoader) as? Int
        poster_path = parcel.readString()
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        adult = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        original_language = parcel.readString()
        original_title = parcel.readString()
        title = parcel.readString()
        vote_average = parcel.readValue(Float::class.java.classLoader) as? Float
        overview = parcel.readString()
        release_date = parcel.readString()
        job = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(vote_count)
        parcel.writeString(poster_path)
        parcel.writeValue(id)
        parcel.writeValue(adult)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(title)
        parcel.writeValue(vote_average)
        parcel.writeString(overview)
        parcel.writeString(release_date)
        parcel.writeString(job)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Result> {
        override fun createFromParcel(parcel: Parcel): Result {
            return Result(parcel)
        }

        override fun newArray(size: Int): Array<Result?> {
            return arrayOfNulls(size)
        }


    }


}