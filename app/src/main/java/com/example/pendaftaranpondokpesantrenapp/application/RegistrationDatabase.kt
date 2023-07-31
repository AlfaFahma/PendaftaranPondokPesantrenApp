package com.example.pendaftaranpondokpesantrenapp.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pendaftaranpondokpesantrenapp.dao.RegistrationDao
import com.example.pendaftaranpondokpesantrenapp.model.Registration

@Database(entities = [Registration::class], version = 1, exportSchema = false)
abstract class RegistrationDatabase: RoomDatabase() {
    abstract fun registrationDao(): RegistrationDao

    companion object{
        private var INSTANCE: RegistrationDatabase? = null

        private val migration1To2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE registration_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE registration_table ADD COLUMN longitude Double DEFAULT 0.0")
            }

        }

        fun getDatabase(context: Context): RegistrationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RegistrationDatabase::class.java,
                    "registration_database"
                )
                    .addMigrations(migration1To2)
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

}