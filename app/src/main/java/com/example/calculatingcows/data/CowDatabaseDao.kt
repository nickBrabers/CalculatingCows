package com.example.calculatingcows.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CowDatabaseDao {

    @Insert
    suspend fun insert(cow: Cow)

    @Update
    suspend fun update(cow: Cow)

    @Delete
    suspend fun delete(cow: Cow)


    // Note: Don't use suspend fun when returning a LiveData
    @Query("SELECT * FROM cow_table WHERE id = :id LIMIT 1")
    fun getLast(id: Int): LiveData<Cow>

    // Note: Don't use suspend fun when returning a LiveData
    @Query("SELECT * FROM cow_table ORDER BY id ASC")
    suspend fun getAll(): List<Cow>

    @Query("SELECT * FROM cow_table ORDER BY id DESC")
    suspend fun getAllByNewest(): List<Cow>

    @Query("DELETE FROM cow_table")
    suspend fun deleteAll()

}