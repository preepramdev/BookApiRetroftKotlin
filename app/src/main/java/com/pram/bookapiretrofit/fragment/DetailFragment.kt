package com.pram.bookapiretrofit.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pram.bookapiretrofit.R
import com.pram.bookapiretrofit.activity.UpdateActivity
import com.pram.bookapiretrofit.api.callback.RetrofitCallback
import com.pram.bookapiretrofit.apiController
import com.pram.bookapiretrofit.dialog.OneButtonDialogFragment
import com.pram.bookapiretrofit.dialog.TwoButtonDialogFragment
import com.pram.bookapiretrofit.manager.Contextor
import com.pram.bookapiretrofit.model.Book
import kotlinx.android.synthetic.main.fragment_detail.view.*
import retrofit2.Call
import retrofit2.Response

/**
 * Created by nuuneoi on 11/16/2014.
 */
class DetailFragment : Fragment(), OneButtonDialogFragment.OnDialogListener, TwoButtonDialogFragment.OnDialogListener {
    private var mContext: Context? = null
    private var mRootView: View? = null
    private var mBook: Book? = null
    private var mBookId: Int? = null

    override fun onOneButtonClick() {
        requireActivity().finish()
    }

    override fun onTwoButtonPositiveClick() {
        removeBook()
    }

    override fun onTwoButtonNegativeClick() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
        savedInstanceState?.let { onRestoreInstanceState(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_detail, container, false)
        initInstances(rootView, savedInstanceState)
        return rootView
    }

    private fun init(savedInstanceState: Bundle?) {
        mContext = Contextor.instance!!.context
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        mRootView = rootView

        if (arguments != null) {
            mBookId = arguments!!.getInt("bookId")
        }

        mRootView!!.btnUpdate!!.apply {mRootView!!.btnUpdate!!
            mRootView!!.btnUpdate!!.setOnClickListener{
                val intent = Intent(mContext, UpdateActivity::class.java)
                intent.putExtra("book", mBook)
                startActivity(intent)
            }
        }

        mRootView!!.btnRemove!!.apply { mRootView!!.btnRemove!!
            mRootView!!.btnRemove!!.setOnClickListener{
                callTwoButtonDialog("Remove?", "Ok", "Cancel")
            }
        }

        //        fetchApi();
    }

    private fun fetchApi() {
        apiController.getBook(mBookId!!, object : RetrofitCallback<Book?> {
            override fun onResponse(call: Call<Book?>?, response: Response<Book?>?) {
                if (response!!.body() != null) {
                    Log.e("onSuccess", response.body().toString())
                    mBook = response.body()
                    if (mBook != null) {
                        val id: String = mBook!!.id.toString()
                        val title: String = mBook!!.title
                        val author: String = mBook!!.author
                        val page: String = mBook!!.pages

                        mRootView!!.tvBookId!!.text = id
                        mRootView!!.tvBookTitle!!.text = title
                        mRootView!!.tvBookAuthor!!.text = author
                        mRootView!!.tvBookPages!!.text = page
                    }
                } else {
                    Log.e("onEmptyResponse", "Returned empty response")
                }
            }

            override fun onFailure(call: Call<Book?>?, t: Throwable?) {
                Log.e("onFailure", t!!.message)
            }

        })
    }

    private fun removeBook() {
        apiController.removeBook(mBookId!!, object : RetrofitCallback<Void?> {
            override fun onResponse(call: Call<Void?>?, response: Response<Void?>?) {
                if (response!!.isSuccessful) {
                    Log.e("onEmptyResponse", "Returned empty response")
                    callOneButtonDialog("Removed", "Ok")
                }
            }

            override fun onFailure(call: Call<Void?>?, t: Throwable?) {
                Log.e("onFailure", t!!.message)
            }

        })
    }

    private fun callOneButtonDialog(message: String, submit: String) {
        val fragment = OneButtonDialogFragment.Builder()
                .setMessage(message)
                .setSubmit(submit)
                .build()
        fragment.show(childFragmentManager, "OneButtonDialogFragment")
    }

    private fun callTwoButtonDialog(message: String, positive: String, negative: String) {
        val fragment = TwoButtonDialogFragment.Builder()
                .setMessage(message)
                .setPositive(positive)
                .setNegative(negative)
                .build()
        fragment.show(childFragmentManager, "TwoButtonDialogFragment")
    }

    override fun onResume() {
        super.onResume()
        fetchApi()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance State here
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) { // Restore Instance State here
    }

    companion object {
        private const val TAG = "DetailFragment"
        fun newInstance(bookId: Int): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putInt("bookId", bookId)
            fragment.arguments = args
            return fragment
        }
    }
}