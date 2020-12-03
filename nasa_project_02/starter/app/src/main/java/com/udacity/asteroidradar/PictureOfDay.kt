package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "pictureTable")
@Parcelize
data class PictureOfDay(
	@PrimaryKey(autoGenerate = true)
	var id: Long = 0L,
	@Json(name = "media_type")
	val mediaType: String,
	val title: String,
	val url: String): Parcelable