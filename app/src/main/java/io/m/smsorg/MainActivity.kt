package io.m.smsorg

import android.Manifest.permission.READ_SMS
import android.database.Cursor
import android.os.Bundle
import android.provider.Telephony
import android.provider.Telephony.TextBasedSmsColumns.ADDRESS
import android.provider.Telephony.TextBasedSmsColumns.BODY
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var data: MutableList<Expense>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf(READ_SMS), 123)
        data = read()

        smsList.apply {
            adapter = SmsAdapter(data)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        swiperefresh.setOnRefreshListener {
            data.clear()
            data.addAll(read())
            smsList.adapter!!.notifyDataSetChanged()
            swiperefresh.isRefreshing = false
        }
    }

    private fun read() = contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(BODY),
            "$ADDRESS = ?", arrayOf(BEL_VEB),
            Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
    )!!.use { cursor ->
        generateSequence { if (cursor.moveToNext()) cursor else null }
                .mapNotNull {
                    Regex("Karta .+ $DATE (.+) ([+-])$DOUBLE $CURRENCY(.+)OK\\. Dostupno $DOUBLE $CURRENCY").find(it[BODY])
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

    private operator fun Cursor.get(columnName: String) = getString(getColumnIndex(columnName))

}
