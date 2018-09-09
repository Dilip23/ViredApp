package com.example.viredapp.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.support.annotation.NonNull
import com.example.viredapp.utilities.DateConverter
import com.google.gson.annotations.SerializedName
import java.math.BigInteger
import java.net.URL
import java.util.*

@Entity(tableName = "feed")
data class feed(
        @PrimaryKey @field:SerializedName(value = "id") val id:Long,
        @field:SerializedName(value = "username")@NonNull val username:String,
        @field:SerializedName(value = "mUrl")@NonNull val mUrl:String,
        @field:SerializedName(value = "timeStamp")@NonNull val timeStamp:String,
        @field:SerializedName(value = "location")val location:String?,
        @field:SerializedName(value = "likes_count")@NonNull val likes_count:String
)

