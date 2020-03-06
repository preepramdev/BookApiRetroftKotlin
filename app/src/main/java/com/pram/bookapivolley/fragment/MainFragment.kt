package com.pram.bookapivolley.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pram.bookapivolley.R
import com.pram.bookapivolley.activity.AddActivity
import com.pram.bookapivolley.activity.DetailActivity
import com.pram.bookapivolley.adapter.BookAdapter
import com.pram.bookapivolley.api.callback.RetrofitCallback
import com.pram.bookapivolley.apiController
import com.pram.bookapivolley.manager.Contextor
import com.pram.bookapivolley.model.Book
import kotlinx.android.synthetic.main.fragment_main.view.*
import retrofit2.Call
import retrofit2.Response
import java.util.*

/**
 * Created by nuuneoi on 11/16/2014.
 */
class MainFragment : Fragment() {
    private var mContext: Context? = null
    private var mRootView: View? = null
    private var mModels: MutableList<Book?>? = null
    private var mAdapter: BookAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
        savedInstanceState?.let { onRestoreInstanceState(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        initInstances(rootView, savedInstanceState)
        return rootView
    }

    private fun init(savedInstanceState: Bundle?) {
        mContext = Contextor.instance!!.context
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        mRootView = rootView
        mModels = ArrayList()
        mAdapter = BookAdapter(mModels)
        mLayoutManager = LinearLayoutManager(mContext)

        mRootView!!.rvBookList!!.apply {mRootView!!.rvBookList!!
            mRootView!!.rvBookList!!.layoutManager = mLayoutManager
            mRootView!!.rvBookList!!.setHasFixedSize(true)
            mRootView!!.rvBookList!!.adapter = mAdapter
        }

        mRootView!!.fabAdd!!.apply {mRootView!!.fabAdd!!
            mRootView!!.fabAdd!!.setOnClickListener {
                val intent = Intent(mContext, AddActivity::class.java)
                startActivity(intent)
            }
        }

        mAdapter!!.setOnItemClickListener(object : BookAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.e(TAG, "onItemClick: $position")
                val intent = Intent(mContext, DetailActivity::class.java)
                intent.putExtra("bookId", mModels!![position]!!.id)
                startActivity(intent)
            }

        })

        //        fetchApi();
    }

    private fun fetchApi() {
        apiController.getBooks(object : RetrofitCallback<List<Book?>?> {
            override fun onResponse(call: Call<List<Book?>?>?, response: Response<List<Book?>?>?) {
                if (response!!.isSuccessful) {
                    if (response.body() != null) {
                        Log.e("onSuccess", response.body().toString())
                        val models = response.body() as List<Book>?
                        if (models != null && models.size > 0) {
                            mModels!!.addAll(models)
                            mAdapter!!.notifyDataSetChanged()
                        }
                    } else {
                        Log.e("onEmptyResponse", "Returned empty response")
                    }
                }
            }

            override fun onFailure(call: Call<List<Book?>?>?, t: Throwable?) {
                Log.e("onFailure", t!!.message)
            }

        })
    }

    override fun onResume() {
        super.onResume()
        mModels!!.clear()
        fetchApi()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {}

    companion object {
        private const val TAG = "MainFragment"
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}