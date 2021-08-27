package com.example.calculatingcows.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.calculatingcows.Filter
import com.example.calculatingcows.data.Cow
import com.example.calculatingcows.data.CowDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        viewModelScope.launch {
            if (_preference.value == null) {
                _preference.value = Filter.SHOW_ASC
            }
            teste(_preference.value!!)
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
        viewModelScope.launch {
            getCowsWithFilter()
        }
    }

    private fun getCowsWithFilter() {
        _preference.value?.let {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    teste(_preference.value!!)
                }
            }
        }
    }
    private suspend fun teste(filter: Filter) = withContext(Dispatchers.IO) {
        val dados = databaseDao.getAll(filter)
        cows.postValue(dados)
    }
}
