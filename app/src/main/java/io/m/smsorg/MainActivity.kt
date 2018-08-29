package io.m.smsorg

import android.Manifest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS), 123)

        val fragmentAdapter = SmsPagerAdapter(supportFragmentManager)
        viewpagerMain.adapter = fragmentAdapter

        tabsMain.setupWithViewPager(viewpagerMain)
    }

}
