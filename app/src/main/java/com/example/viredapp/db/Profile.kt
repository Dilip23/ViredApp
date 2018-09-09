package com.example.viredapp.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.math.BigInteger


@Entity(tableName = "Profile")
data class Profile(
        @PrimaryKey(autoGenerate = true) @field:SerializedName(value = "id")val id:Long,
        @field:SerializedName(value = "username")val username:String,
        @field:SerializedName(value = "email")val email:String,
        @field:SerializedName(value = "location")val location:String,
        @field:SerializedName(value = "friends_count")val friends_count:String,
        @field:SerializedName(value = "profile_pic")val profile_pic:String

)