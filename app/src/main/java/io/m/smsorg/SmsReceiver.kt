package io.m.smsorg

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log


class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        (intent.extras?.get("pdus") as Array<*>).map {
            SmsMessage.createFromPdu(it as ByteArray, "3gpp")
        }.filter {
            it.displayOriginatingAddress == BEL_VEB
        }.forEach {
            Log.i("Message", it.messageBody)
        }
    }
}
