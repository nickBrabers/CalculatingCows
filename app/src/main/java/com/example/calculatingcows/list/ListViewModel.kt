package com.example.calculatingcows.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.calculatingcows.Filter
import com.example.calculatingcows.data.Cow
import com.example.calculatingcows.data.CowDatabaseDao
import kotlinx.coroutines.launch

class ListViewModel(
    private val databaseDao: CowDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

/*
    private val _cows = MutableLiveData<List<Cow>>()
    val cows: LiveData<List<Cow>>
        get() = _cows
*/

    val cows = MutableLiveData<LiveData<List<Cow>>>()

    private val _eventNavigateToAdd = MutableLiveData<Boolean>()
    val eventNavigateToAdd: LiveData<Boolean>
        get() = _eventNavigateToAdd

    private val _preference = MutableLiveData<Filter>()

    init {
        if (_preference.value == null) {
            _preference.value = Filter.SHOW_DESC
        }
        cows.value = getCowsFromDatabase(_preference.value!!)
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

    private fun getCowsFromDatabase(filter: Filter): LiveData<List<Cow>> {

        viewModelScope.launch {
            cows.value = when (filter) {
                Filter.SHOW_DESC -> databaseDao.getAllByNewest()
                else -> databaseDao.getAll()
            }
            Log.i(TAG, "we got into the database's calls $filter")
        }
        return cows.value!!
    }

    fun updateFilter(filter: Filter) {
        _preference.value = filter
        getCowsWithFilter()
        Log.i(TAG, "Update Filter was Called ${_preference.value}")
    }

    private fun getCowsWithFilter() {
        _preference.value?.let {
            getCowsFromDatabase(it)
        }
        Log.i(TAG, "getCowsWithFilter was called! ${_preference.value}")
    }
}

private const val TAG = "Testing Filters"