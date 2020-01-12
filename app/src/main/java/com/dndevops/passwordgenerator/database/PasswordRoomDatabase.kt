package com.dndevops.passwordgenerator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dndevops.passwordgenerator.model.PasswordItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [PasswordItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PasswordRoomDatabase : RoomDatabase() {

    abstract fun PasswordItemDao(): PasswordDao

    companion object {
        private const val DATABASE_NAME = "PASSWORD_GENERATOR_DATABASE"

        @Volatile
        private var INSTANCE: PasswordRoomDatabase? = null


        fun getDatabase(context: Context): PasswordRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(PasswordRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            PasswordRoomDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    INSTANCE?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {
                                            database.PasswordItemDao().insertPasswordItem(PasswordItem("Mail", "PQ!#_x#", Date() ))
                                        }
                                    }
                                }
                            })
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}