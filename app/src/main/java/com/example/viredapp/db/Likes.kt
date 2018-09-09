package com.example.viredapp.db

import android.arch.persistence.room.*
import com.example.viredapp.utilities.DateConverter
import com.google.gson.annotations.SerializedName
import java.util.*

@TypeConverters(DateConverter::class)
@Entity(tableName = "Likes")
data class Likes(
        @PrimaryKey @field:SerializedName(value = "id")val id:Long,
        @field:SerializedName(value = "media_id")val media_id:Int,
        @field:SerializedName(value = "user_like_id")val user_like_id:Int,
        @field:SerializedName(value = "liked_on")val liked_on:Date
)