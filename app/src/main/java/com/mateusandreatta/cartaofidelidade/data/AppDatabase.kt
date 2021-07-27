package com.mateusandreatta.cartaofidelidade.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [FidelityCard::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun fidelityDao() : FidelityCardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fidelitycard_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}