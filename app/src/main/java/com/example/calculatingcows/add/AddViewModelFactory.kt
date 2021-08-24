package com.example.calculatingcows.add

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calculatingcows.data.CowDatabaseDao
import java.lang.IllegalArgumentException

class AddViewModelFactory(
    private val cowDatabaseDao: CowDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(cowDatabaseDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}