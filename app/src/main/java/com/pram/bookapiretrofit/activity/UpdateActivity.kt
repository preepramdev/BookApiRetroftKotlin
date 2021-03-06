package com.pram.bookapiretrofit.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pram.bookapiretrofit.R
import com.pram.bookapiretrofit.fragment.UpdateFragment
import com.pram.bookapiretrofit.model.Book

class UpdateActivity : AppCompatActivity() {

    private var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        initInstances()

        if (intent != null) {
            book = intent.getParcelableExtra("book")
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentContainer, UpdateFragment.newInstance(book))
                    .commit()
        }
    }

    private fun initInstances() {}
}