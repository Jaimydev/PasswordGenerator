package com.dndevops.passwordgenerator.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dndevops.passwordgenerator.model.PasswordItem

@Dao
interface PasswordDao {

    @Insert
    suspend fun insertPasswordItem(PasswordItem: PasswordItem)

    @Query("SELECT * FROM PasswordItem_table ORDER BY created_date desc")
    fun getPasswordItems(): LiveData<List<PasswordItem>>

    @Update
    suspend fun updatePasswordItem(PasswordItem: PasswordItem)

    @Delete
    suspend fun deletePasswordItem(PasswordItemBacklog: PasswordItem)

    @Query("DELETE FROM PasswordItem_table")
    suspend fun deleteAllItems()
}