package com.example.calculatingcows.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.calculatingcows.Filter


@Dao
interface CowDatabaseDao {

    fun getAll(filter: Filter): LiveData<List<Cow>> =
        when(filter) {
            Filter.SHOW_ASC -> getAllByLast()
            Filter.SHOW_DESC -> getAllByNewest()
        }

    @Insert
    suspend fun insert(cow: Cow)

    @Update
    suspend fun update(cow: Cow)

    @Delete
    suspend fun delete(cow: Cow)

    // Note: Don't use suspend fun when returning a LiveData
    @Query("SELECT * FROM cow_table ORDER BY id ASC")
    fun getAllByLast(): LiveData<List<Cow>>

    @Query("SELECT * FROM cow_table ORDER BY id DESC")
    fun getAllByNewest(): LiveData<List<Cow>>

    @Query("DELETE FROM cow_table")
    suspend fun deleteAll()

}