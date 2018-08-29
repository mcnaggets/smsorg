package io.m.smsorg.data

import java.time.LocalDate
import java.util.*

data class Expense(
        val date: LocalDate,
        val message: String,
        val amount: Double,
        val currency: Currency,
        val remaining: Double,
        val type: String,
        val sign: String
)