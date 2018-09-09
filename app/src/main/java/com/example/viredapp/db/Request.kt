package com.example.viredapp.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.example.viredapp.utilities.DateConverter
import com.google.gson.annotations.SerializedName
import java.util.*

@TypeConverters(DateConverter::class)
@Entity(tableName = "Request")
data class Request(
        @PrimaryKey
        public val id:Int,
        @field:SerializedName(value = "user_id")val user_id:Int,
        @field:SerializedName(value = "requested_id")val requested_id:Int,
        @field:SerializedName(value = "sent_on")val sent_on:Date
)