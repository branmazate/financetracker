package com.example.financetracker.data.db.converters

import androidx.room.TypeConverter
import com.example.financetracker.data.model.RecurrenceInterval

class RecurrenceIntervalConverter {
    @TypeConverter
    fun fromInterval(interval: RecurrenceInterval?): String? = interval?.name

    @TypeConverter
    fun toInterval(value: String?): RecurrenceInterval? {
        return value?.let{
            enumValueOf<RecurrenceInterval>(it)
        }
    }
}