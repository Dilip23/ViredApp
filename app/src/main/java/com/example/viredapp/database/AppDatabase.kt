package com.example.viredapp.database

import android.arch.persistence.room.*
import android.arch.persistence.room.migration.Migration
import android.content.Context
import android.view.ViewDebug
import com.example.viredapp.db.*
import com.example.viredapp.utilities.DateConverter
@TypeConverters(DateConverter::class)
@Database(entities = [feed::class,
                Friends::class,
                Profile::class,
                Request::class,
                Likes::class],version = 14,
        exportSchema = false)

public abstract class AppDatabase: RoomDatabase() {
    abstract fun feedDao():FeedDao
    abstract fun requestDao():RequestDao
    abstract fun friendsDao():FriendsDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null



        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }



        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"AppDB.db")
                        .fallbackToDestructiveMigration()
                        .build()
        }
    }

