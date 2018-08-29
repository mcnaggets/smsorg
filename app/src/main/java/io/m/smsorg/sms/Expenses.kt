package io.m.smsorg.sms

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import io.m.smsorg.*
import kotlinx.android.synthetic.main.fragment_first.*

class Expenses : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_first, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        data = read()

        smsList.apply {
            adapter = ExpenseAdapter()
            layoutManager = LinearLayoutManager(view!!.context)
        }

        smsListRefresh.setOnRefreshListener {
            refresh()
            smsList.adapter!!.notifyDataSetChanged()
            smsListRefresh.isRefreshing = false
        }

    }

}