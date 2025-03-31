package com.example.financetracker.data.db.converters

import androidx.room.TypeConverter
import com.example.financetracker.data.model.TransactionType

class TransactionTypeConverter {
    @TypeConverter
    fun fromType(type: TransactionType): String = type.name

    @TypeConverter
    fun toType(value: String): TransactionType = enumValueOf(value)
}