package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "asteroidsTable")
@Parcelize
data class Asteroid constructor(
    @PrimaryKey
    var id: Long,

    val codename: String,

    val closeApproachDate: String,

    val absoluteMagnitude: Double,

    val estimatedDiameter: Double,

    val relativeVelocity: Double,

    val distanceFromEarth: Double,

    val isPotentiallyHazardous: Boolean) : Parcelable