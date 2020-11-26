package com.example.popmovies2.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Crew() : Parcelable{
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
    var profilePath: Any? = null

    @SerializedName("credit_id")
    @Expose
    var creditId: String? = null

    @SerializedName("department")
    @Expose
    var department: String? = null

    @SerializedName("job")
    @Expose
    var job: String? = null

    constructor(parcel: Parcel) : this() {
        adult = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        gender = parcel.readValue(Int::class.java.classLoader) as? Int
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        knownForDepartment = parcel.readString()
        name = parcel.readString()
        originalName = parcel.readString()
        popularity = parcel.readValue(Float::class.java.classLoader) as? Float
        creditId = parcel.readString()
        department = parcel.readString()
        job = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(adult)
        parcel.writeValue(gender)
        parcel.writeValue(id)
        parcel.writeString(knownForDepartment)
        parcel.writeString(name)
        parcel.writeString(originalName)
        parcel.writeValue(popularity)
        parcel.writeString(creditId)
        parcel.writeString(department)
        parcel.writeString(job)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Crew> {
        override fun createFromParcel(parcel: Parcel): Crew {
            return Crew(parcel)
        }

        override fun newArray(size: Int): Array<Crew?> {
            return arrayOfNulls(size)
        }
    }

}