package io.m.smsorg

import android.provider.Telephony
import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView
import io.m.smsorg.data.Expense
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val BEL_VEB = "BelVEB24.BY"

const val DATE = "(\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}:\\d{2})"
const val DOUBLE = "([0-9]+(?:\\.[0-9]+)?)"
const val CURRENCY = "(\\w{3})"

lateinit var data: MutableList<Expense>

internal fun Fragment.refresh() {
    data.clear()
    data.addAll(read())
}

internal fun Fragment.read() = view!!.context.contentResolver.query(
        Telephony.Sms.Inbox.CONTENT_URI,
        arrayOf(Telephony.TextBasedSmsColumns.BODY),
        "${Telephony.TextBasedSmsColumns.ADDRESS} = ?", arrayOf(BEL_VEB),
        Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
)!!.use { cursor ->
    generateSequence { if (cursor.moveToNext()) cursor else null }
            .mapNotNull {
                Regex("Karta .+ $DATE (.+) ([+-])$DOUBLE $CURRENCY(.+)OK\\. Dostupno $DOUBLE $CURRENCY").find(it.getString(0))
            }
            .map { it.groupValues }
            .map {
                Expense(
                        date = LocalDateTime.parse(it[1], DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")).toLocalDate(),
                        type = it[2],
                        sign = it[3],
                        amount = it[4].toDouble(),
                        currency = Currency.getInstance(it[5]),
                        message = it[6].trim(),
                        remaining = it[7].toDouble()
                )
            }.toMutableList()
}

internal fun View.text(id: Int) = this.findViewById(id) as TextView