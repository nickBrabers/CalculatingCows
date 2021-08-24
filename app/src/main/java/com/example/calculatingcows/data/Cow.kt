package com.example.calculatingcows.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calculatingcows.convert



@Entity(tableName = "cow_table")
data class Cow(


    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "cow_number")
    val number: Int,

    @ColumnInfo(name = "cow_age")
    val age: Int,

    @ColumnInfo(name = "cow_birth_date")
    val birthDate: String = convert(age)
)
