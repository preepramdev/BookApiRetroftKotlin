package com.pram.bookapivolley.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pram.bookapivolley.R
import com.pram.bookapivolley.api.callback.RetrofitCallback
import com.pram.bookapivolley.apiController
import com.pram.bookapivolley.dialog.OneButtonDialogFragment
import com.pram.bookapivolley.dialog.TwoButtonDialogFragment
import com.pram.bookapivolley.manager.Contextor
import com.pram.bookapivolley.model.Book
import kotlinx.android.synthetic.main.fragment_update.view.*
import retrofit2.Call
import retrofit2.Response

/**
 * Created by nuuneoi on 11/16/2014.
 */
class UpdateFragment : Fragment(), OneButtonDialogFragment.OnDialogListener, TwoButtonDialogFragment.OnDialogListener {
    private var mContext: Context? = null
    private var mRootView: View? = null
    private var mBook: Book? = null

    override fun onOneButtonClick() {
        requireActivity().finish()
    }

    override fun onTwoButtonPositiveClick() {
        requireActivity().finish()
    }

    override fun onTwoButtonNegativeClick() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
        savedInstanceState?.let { onRestoreInstanceState(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_update, container, false)
        initInstances(rootView, savedInstanceState)
        return rootView
    }

    private fun init(savedInstanceState: Bundle?) {
        mContext = Contextor.instance!!.context
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        mRootView = rootView

        if (arguments != null) {
            mBook = arguments!!.getParcelable("book")
        }

        if (mBook != null) {
            val id = mBook!!.id.toString()
            val title = mBook!!.title
            val author = mBook!!.author
            val pages = mBook!!.pages

            mRootView!!.tvBookId!!.text = id
            mRootView!!.edtBookTitle!!.setText(title)
            mRootView!!.edtBookAuthor!!.setText(author)
            mRootView!!.edtBookPages!!.setText(pages)
        }
        mRootView!!.btnUpdate!!.setOnClickListener {
            updateBook()
        }
        mRootView!!.btnCancel!!.setOnClickListener {
            callTwoButtonDialog("Leave?", "Ok", "Cancel")
        }
    }

    private fun updateBook() {
        val title = mRootView!!.edtBookTitle!!.text.toString()
        val author = mRootView!!.edtBookAuthor!!.text.toString()
        val pages = mRootView!!.edtBookPages!!.text.toString()

        mBook!!.title = title
        mBook!!.author = author
        mBook!!.pages = pages

        apiController.updatePutBook(mBook!!, object : RetrofitCallback<Book?> {
            override fun onResponse(call: Call<Book?>?, response: Response<Book?>?) {
                Log.e(TAG, "onResponse: $response")
                callOneButtonDialog("Updated", "Ok")
            }

            override fun onFailure(call: Call<Book?>?, t: Throwable?) {
                Log.e(TAG, "onErrorResponse: " + t!!.message)
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

    private fun onRestoreInstanceState(savedInstanceState: Bundle) { // Restore Instance State here
    }

    companion object {
        private const val TAG = "UpdateFragment"
        fun newInstance(book: Book?): UpdateFragment {
            val fragment = UpdateFragment()
            val args = Bundle()
            args.putParcelable("book", book)
            fragment.arguments = args
            return fragment
        }
    }
}