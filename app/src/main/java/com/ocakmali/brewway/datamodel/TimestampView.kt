package com.ocakmali.brewway.datamodel

import android.os.Parcel
import android.os.Parcelable
import com.ocakmali.domain.model.RecipeTimestamp

fun TimestampView.toTimestamp(id: Int = this.id) = RecipeTimestamp(time, note, recipeId, id)

fun RecipeTimestamp.toView() = TimestampView(time, note, recipeId, id)

data class TimestampView(val time: Long,
                         val note: String,
                         val recipeId: Int = 0,
                         val id: Int = 0) : Parcelable {

    private constructor(parcel: Parcel) : this(
            time = parcel.readLong(),
            note = parcel.readString()!!,
            recipeId = parcel.readInt(),
            id = parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(time)
        parcel.writeString(note)
        parcel.writeInt(recipeId)
        parcel.writeInt(id)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField val CREATOR  = object : Parcelable.Creator<TimestampView> {
            override fun createFromParcel(parcel: Parcel) = TimestampView(parcel)

            override fun newArray(size: Int) = arrayOfNulls<TimestampView>(size)
        }
    }
}
