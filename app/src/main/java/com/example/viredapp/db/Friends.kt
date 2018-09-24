package com.example.viredapp.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.example.viredapp.utilities.DateConverter
import com.google.gson.annotations.SerializedName
import java.util.*

@TypeConverters(DateConverter::class)
@Entity(tableName = "Friends")
data class Friends(
        @PrimaryKey(autoGenerate = true) val auto_id:Int,
        @field:SerializedName(value = "id") val id:Int,
        @field:SerializedName(value = "request_id")val request_id:String,
        @field:SerializedName(value = "friend_id")val friend_id:String,
        @field:SerializedName(value = "created_on")val created_on:Date
)