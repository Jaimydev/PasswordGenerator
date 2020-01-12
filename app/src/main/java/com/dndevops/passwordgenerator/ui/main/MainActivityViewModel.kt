package com.dndevops.passwordgenerator.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dndevops.passwordgenerator.database.PasswordItemRepository
import com.dndevops.passwordgenerator.model.PasswordItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val passwordItemRepository = PasswordItemRepository(application.applicationContext)

    val passwordItems = passwordItemRepository.getPasswordItems()

    fun insertPasswordItemBacklogItem(PasswordItem: PasswordItem) {
        CoroutineScope(Dispatchers.IO).launch {
            passwordItemRepository.insertPasswordItem(PasswordItem)
        }
    }

    fun deletePasswordItem(PasswordItem: PasswordItem) {
        CoroutineScope(Dispatchers.IO).launch {
            passwordItemRepository.deletePasswordItem(PasswordItem)
        }
    }

    fun deleteAllPasswordItems() {
        CoroutineScope(Dispatchers.IO).launch {
            passwordItemRepository.deleteAllItems()
        }
    }
}