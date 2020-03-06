package com.pram.bookapivolley.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.pram.bookapivolley.R

class DismissOneButtonDialogFragment : DialogFragment() {
    private var tvMessage: TextView? = null
    private var btnSubmit: Button? = null
    private var message: String? = null
    private var submit: String? = null

    interface OnDialogListener {
        fun onDismissOneButtonClick()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            restoreArguments(arguments)
        } else {
            restoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_one_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        setupView()
    }

    private fun bindView(view: View) {
        tvMessage = view.findViewById(R.id.tvMessage)
        btnSubmit = view.findViewById(R.id.btnSubmit)
    }

    private fun setupView() {
        tvMessage!!.text = message
        btnSubmit!!.text = submit
        btnSubmit!!.setOnClickListener { v: View? ->
            val listener = onDialogListener
            listener?.onDismissOneButtonClick()
            dismiss()
        }
    }

    private val onDialogListener: OnDialogListener?
        private get() {
            val fragment = parentFragment
            try {
                return if (fragment != null) {
                    fragment as OnDialogListener?
                } else {
                    activity as OnDialogListener?
                }
            } catch (ignored: ClassCastException) {
            }
            return null
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_MESSAGE, message)
        outState.putString(KEY_SUBMIT, submit)
    }

    private fun restoreInstanceState(bundle: Bundle) {
        message = bundle.getString(KEY_MESSAGE)
        submit = bundle.getString(KEY_SUBMIT)
    }

    private fun restoreArguments(bundle: Bundle?) {
        message = bundle!!.getString(KEY_MESSAGE)
        submit = bundle.getString(KEY_SUBMIT)
    }

    class Builder {
        private var message: String? = null
        private var submit: String? = null
        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun setSubmit(submit: String?): Builder {
            this.submit = submit
            return this
        }

        fun build(): DismissOneButtonDialogFragment {
            return newInstance(message, submit)
        }
    }

    companion object {
        private const val KEY_MESSAGE = "key_message"
        private const val KEY_SUBMIT = "key_submit"
        fun newInstance(message: String?, submit: String?): DismissOneButtonDialogFragment {
            val fragment = DismissOneButtonDialogFragment()
            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            bundle.putString(KEY_SUBMIT, submit)
            fragment.arguments = bundle
            return fragment
        }
    }
}