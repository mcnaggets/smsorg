package io.m.smsorg

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

internal class SmsAdapter(private val data: List<Expense>) : RecyclerView.Adapter<SmsAdapter.ViewHolder>() {

    inner class ViewHolder(
            itemView: View,
            var date: TextView = itemView.findViewById(R.id.expense_date) as TextView,
            var amount: TextView = itemView.findViewById(R.id.expense_amount) as TextView,
            var currency: TextView = itemView.findViewById(R.id.expense_currency) as TextView,
            var message: TextView = itemView.findViewById(R.id.expense_message) as TextView
    ) : RecyclerView.ViewHolder(itemView)


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