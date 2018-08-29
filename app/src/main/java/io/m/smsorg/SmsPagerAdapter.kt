package io.m.smsorg

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.m.smsorg.sms.Expenses
import io.m.smsorg.stat.Statistics

class SmsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Expenses()
            1 -> Statistics()
            else -> Turtle()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "All Expenses"
            1 -> "Statistics by month"
            else -> "Photo of turtle"
        }
    }
}