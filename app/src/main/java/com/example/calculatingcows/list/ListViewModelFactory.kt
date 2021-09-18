package com.example.calculatingcows.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calculatingcows.Filter
import com.example.calculatingcows.data.CowDatabaseDao

class ListViewModelFactory(
    private val cowDatabaseDao: CowDatabaseDao,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(cowDatabaseDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}