package com.example.calculatingcows.add

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.calculatingcows.data.Cow
import com.example.calculatingcows.data.CowDatabaseDao
import kotlinx.coroutines.launch


class AddViewModel(
    private val cowDatabaseDao: CowDatabaseDao,
    application: Application
) : AndroidViewModel(application) {



    fun onSaveClick(number: Int?, age: Int?){
        viewModelScope.launch {
            val cow = Cow(number = number!!, age = age!!)
            insert(cow)
            onNavigate()
        }
    }

    private suspend fun insert(cow: Cow){
        cowDatabaseDao.insert(cow)
    }


    private val _eventSend = MutableLiveData<Boolean>()
    val eventSend: LiveData<Boolean>
        get() = _eventSend

    private fun onNavigate() {
        _eventSend.value = true
        onNavigateDone()
    }

    private fun onNavigateDone() {
        _eventSend.value = false
    }

}