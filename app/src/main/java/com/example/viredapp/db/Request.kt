package com.example.viredapp.db

import android.arch.persistence.room.*
import com.example.viredapp.utilities.DateConverter
import com.google.gson.annotations.SerializedName
import java.util.*

@TypeConverters(DateConverter::class)
@Entity(tableName = "Request")
data class Request(
        @PrimaryKey(autoGenerate = true) val auto_id:Int,
        @field:SerializedName(value = "id")val id:Int,
        @field:SerializedName(value = "user_id")val user_id:Int,
        @field:SerializedName(value = "requested_id")val requested_id:Int,
        @field:SerializedName(value = "sent_on")val sent_on:Date
        
        /*
        * TODO:Update Entity to use spam functionality
        * */
//        @ColumnInfo(name = "spam_id")
//        val spam_id:Int

)