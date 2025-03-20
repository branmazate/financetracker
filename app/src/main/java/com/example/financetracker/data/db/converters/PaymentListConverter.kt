package com.example.financetracker.data.db.converters

import androidx.room.TypeConverter
import com.example.financetracker.data.model.Debt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PaymentListConverter {
    private val gson = Gson()
    private val type = object : TypeToken<List<Debt.Payment>>() {}.type

    @TypeConverter
    fun fromString(value: String): List<Debt.Payment>{
        return value?.let { gson.fromJson(it, type) } ?: emptyList()
    }

    @TypeConverter
    fun toString(list: List<Debt.Payment>): String {
        val type = object : TypeToken<List<Debt.Payment>>() {}.type
        return gson.toJson(list ?: emptyList<Debt.Payment>(), type)
    }
}