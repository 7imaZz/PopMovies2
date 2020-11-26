package com.example.popmovies2.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Cast() : Parcelable{
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null

    @SerializedName("gender")
    @Expose
    var gender: Int? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("known_for_department")
    @Expose
    var knownForDepartment: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("original_name")
    @Expose
    var originalName: String? = null

    @SerializedName("popularity")
    @Expose
    var popularity: Float? = null

    @SerializedName("profile_path")
    @Expose
    var profilePath: String? = null

    @SerializedName("cast_id")
    @Expose
    var castId: Int? = null

    @SerializedName("character")
    @Expose
    var character: String? = null

    @SerializedName("credit_id")
    @Expose
    var creditId: String? = null

    @SerializedName("order")
    @Expose
    var order: Int? = null

    constructor(parcel: Parcel) : this() {
        adult = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        gender = parcel.readValue(Int::class.java.classLoader) as? Int
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        knownForDepartment = parcel.readString()
        name = parcel.readString()
        originalName = parcel.readString()
        popularity = parcel.readValue(Float::class.java.classLoader) as? Float
        profilePath = parcel.readString()
        castId = parcel.readValue(Int::class.java.classLoader) as? Int
        character = parcel.readString()
        creditId = parcel.readString()
        order = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(adult)
        parcel.writeValue(gender)
        parcel.writeValue(id)
        parcel.writeString(knownForDepartment)
        parcel.writeString(name)
        parcel.writeString(originalName)
        parcel.writeValue(popularity)
        parcel.writeString(profilePath)
        parcel.writeValue(castId)
        parcel.writeString(character)
        parcel.writeString(creditId)
        parcel.writeValue(order)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cast> {
        override fun createFromParcel(parcel: Parcel): Cast {
            return Cast(parcel)
        }

        override fun newArray(size: Int): Array<Cast?> {
            return arrayOfNulls(size)
        }
    }

}