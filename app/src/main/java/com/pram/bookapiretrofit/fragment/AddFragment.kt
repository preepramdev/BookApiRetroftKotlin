package com.pram.bookapiretrofit.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pram.bookapiretrofit.R
import com.pram.bookapiretrofit.api.callback.RetrofitCallback
import com.pram.bookapiretrofit.apiController
import com.pram.bookapiretrofit.dialog.OneButtonDialogFragment
import com.pram.bookapiretrofit.dialog.TwoButtonDialogFragment
import com.pram.bookapiretrofit.manager.Contextor
import com.pram.bookapiretrofit.model.Book
import kotlinx.android.synthetic.main.fragment_add.view.*
import retrofit2.Call
import retrofit2.Response

/**
 * Created by nuuneoi on 11/16/2014.
 */
class AddFragment : Fragment(), OneButtonDialogFragment.OnDialogListener, TwoButtonDialogFragment.OnDialogListener {

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
        val rootView = inflater.inflate(R.layout.fragment_add, container, false)
        initInstances(rootView, savedInstanceState)
        return rootView
    }

    private fun init(savedInstanceState: Bundle?) {
        mContext = Contextor.instance!!.context
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        mRootView = rootView

        mRootView!!.btnAdd!!.apply { mRootView!!.btnAdd!!
            mRootView!!.btnAdd!!.setOnClickListener {
                addBook()
            }
        }

        mRootView!!.btnCancel!!.apply {mRootView!!.btnCancel!!
            mRootView!!.btnCancel!!.setOnClickListener {
                callTwoButtonDialog("Leave?", "Ok", "Cancel")
            }
        }

    }

    private fun addBook() {
        val title = mRootView!!.edtBookTitle!!.text.toString()
        val author = mRootView!!.edtBookAuthor!!.text.toString()
        val pages = mRootView!!.edtBookPages!!.text.toString()

        if (title.isEmpty() || author.isEmpty() || pages.isEmpty()) {
            Toast.makeText(mContext, "Check data", Toast.LENGTH_SHORT).show()
        } else {
            mBook = Book(title, author, pages)

            apiController.createBook(mBook!!, object : RetrofitCallback<Book?> {
                override fun onResponse(call: Call<Book?>?, response: Response<Book?>?) {
                    if (response!!.isSuccessful) {
                        if (response.body() != null) {
                            Log.e("onSuccess", response.body().toString())
                            callOneButtonDialog("Done", "Ok")
                        } else {
                            Log.e("onEmptyResponse", "Returned empty response")
                        }
                    }
                }

                override fun onFailure(call: Call<Book?>?, t: Throwable?) {
                    Log.e("onFailure", t!!.message)
                }

            })
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance State here
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) { // Restore Instance State here
    }

    companion object {
        private const val TAG = "AddFragment"
        fun newInstance(): AddFragment {
            val fragment = AddFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}