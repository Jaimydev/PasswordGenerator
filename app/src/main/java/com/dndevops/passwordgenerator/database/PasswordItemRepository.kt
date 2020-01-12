package com.dndevops.passwordgenerator.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.dndevops.passwordgenerator.model.PasswordItem

class PasswordItemRepository(context: Context) {

    private val passwordDao: PasswordDao

    init {
        val database = PasswordRoomDatabase.getDatabase(context)
        passwordDao = database!!.PasswordItemDao()
    }

    fun getPasswordItems(): LiveData<List<PasswordItem>> {
        return passwordDao.getPasswordItems()
    }

    suspend fun updatePasswordItem(PasswordItem: PasswordItem) {
        passwordDao.updatePasswordItem(PasswordItem)
    }

    suspend fun deletePasswordItem(PasswordItem: PasswordItem) {
        passwordDao.deletePasswordItem(PasswordItem)
    }

    suspend fun deleteAllItems() {
        passwordDao.deleteAllItems()
    }

    suspend fun insertPasswordItem(PasswordItem: PasswordItem) {
        passwordDao.insertPasswordItem(PasswordItem)
    }
}