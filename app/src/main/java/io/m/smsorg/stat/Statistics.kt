package io.m.smsorg.stat

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import io.m.smsorg.R
import io.m.smsorg.refresh
import kotlinx.android.synthetic.main.fragment_second.*

class Statistics : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_second, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        statList.apply {
            adapter = StatisticAdapter()
            layoutManager = LinearLayoutManager(view!!.context)
        }

        statListRefresh.setOnRefreshListener {
            refresh()
            statList.adapter!!.notifyDataSetChanged()
            statListRefresh.isRefreshing = false
        }
    }


}