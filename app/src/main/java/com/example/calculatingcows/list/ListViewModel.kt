package com.example.calculatingcows.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.calculatingcows.Filter
import com.example.calculatingcows.data.CowDatabaseDao
import kotlinx.coroutines.launch

class ListViewModel(
    private val databaseDao: CowDatabaseDao,
    application: Application
) : AndroidViewModel(application) {


    private val _eventNavigateToAdd = MutableLiveData<Boolean>()
    val eventNavigateToAdd: LiveData<Boolean>
        get() = _eventNavigateToAdd

    private val _preference = MutableLiveData<Filter>()
    val preference: LiveData<Filter>
    get() = _preference

    val cows = Transformations.switchMap(_preference){
        databaseDao.getAll(it)
    }

    init {
            if (_preference.value == null) {
                _preference.value = Filter.SHOW_ASC
            }
    }


    fun onNavigate() {
        _eventNavigateToAdd.value = true
    }

    fun onNavigateDone() {
        _eventNavigateToAdd.value = false
    }

    fun deleteAll() {
        viewModelScope.launch {
            databaseDao.deleteAll()
        }
    }

    fun updateFilter(filter: Filter) {
        _preference.value = filter
    }
}
