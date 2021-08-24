package com.example.calculatingcows.list

import android.app.Application
import androidx.lifecycle.*
import com.example.calculatingcows.Filter
import com.example.calculatingcows.convert
import com.example.calculatingcows.data.Cow
import com.example.calculatingcows.data.CowDatabaseDao
import kotlinx.coroutines.launch

class ListViewModel(
    private val databaseDao: CowDatabaseDao,
    application: Application
) : AndroidViewModel(application) {


    private val _cows = MutableLiveData<List<Cow>>()
    val cows: LiveData<List<Cow>>
        get() = _cows

    private val _eventNavigateToAdd = MutableLiveData<Boolean>()
    val eventNavigateToAdd: LiveData<Boolean>
        get() = _eventNavigateToAdd

    private val _preference = MutableLiveData<Filter>()

    init {
        if (_preference.value == null) {
            _preference.value = Filter.SHOW_ASC
        }
        getCowsFromDatabase(_preference.value!!)
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

    private fun getCowsFromDatabase(filter: Filter) {
        viewModelScope.launch {
            when (filter) {
                Filter.SHOW_DESC -> _cows.value = databaseDao.getAllByNewest()
                else -> _cows.value = databaseDao.getAll()
            }
        }
    }

    fun updateFilter(filter: Filter) {
        _preference.value = filter
        getCowsWithFilter()
    }

    private fun getCowsWithFilter() {
        _preference.value?.let {
            getCowsFromDatabase(it)
        }
    }
}