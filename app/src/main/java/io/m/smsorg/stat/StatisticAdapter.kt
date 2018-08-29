package io.m.smsorg.stat

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.m.smsorg.R
import io.m.smsorg.data
import io.m.smsorg.text
import kotlin.math.roundToInt

internal class StatisticAdapter() : RecyclerView.Adapter<StatisticAdapter.ViewHolder>() {

    inner class ViewHolder(
            view: View,
            val statText: TextView = view.text(R.id.statText)
    ) : RecyclerView.ViewHolder(view)

    private val byMonth by lazy { data.groupBy { it.date.withDayOfMonth(1) } }
    private val indexed by lazy { byMonth.keys.withIndex().map { it.index to it.value }.toMap() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.stat_info, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val date = indexed[position]!!
            var stat = "<h2>${date.month.name} ${date.year}</h2>"
            byMonth[date]!!.groupBy { it.currency }.forEach { (currency, currencyData) ->
                val sums: Map<String, Double> = currencyData.groupingBy { it.sign }
                        .fold(0.0) { acc, expense -> acc + expense.amount }
                val plus = sums["+"]?.roundToInt() ?: 0
                val minus = sums["-"]?.roundToInt() ?: 0
                val total = plus - minus
                stat += """<li>$currency
                    |<font color="#32CD32">+$plus</font>
                    |<font color="#F08080">-$minus</font>
                    | = <b>$total</b>
                    |</li>""".trimMargin()
            }
            statText.text = Html.fromHtml(stat, Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM)
        }
    }

    override fun getItemCount() = byMonth.size

}