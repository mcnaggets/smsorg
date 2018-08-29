package io.m.smsorg.sms

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.m.smsorg.R
import io.m.smsorg.data
import io.m.smsorg.text

internal class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    inner class ViewHolder(
            view: View,
            val date: TextView = view.text(R.id.expense_date),
            val amount: TextView = view.text(R.id.expense_amount),
            val currency: TextView = view.text(R.id.expense_currency),
            val message: TextView = view.text(R.id.expense_message)
    ) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.expense_info, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = data[position]
        holder.apply {
            date.text = expense.date.toString()
            message.text = expense.message
            amount.text = expense.amount.toString()
            currency.text = expense.currency.currencyCode

            val color = ContextCompat.getColor(itemView.context,
                    if (expense.sign == "-") R.color.colorLightCoral
                    else R.color.colorLimeGreen
            )
            amount.setTextColor(color)
            currency.setTextColor(color)
        }
    }

    override fun getItemCount() = data.size
}