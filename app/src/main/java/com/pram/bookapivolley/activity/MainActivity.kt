package com.pram.bookapivolley.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pram.bookapivolley.R
import com.pram.bookapivolley.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initInstances()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit()
        }
    }

    private fun initInstances() {}
}