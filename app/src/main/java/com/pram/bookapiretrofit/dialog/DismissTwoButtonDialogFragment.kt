package com.pram.bookapiretrofit.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.pram.bookapiretrofit.R

class DismissTwoButtonDialogFragment : DialogFragment() {
    private var tvMessage: TextView? = null
    private var btnPositive: Button? = null
    private var btnNegative: Button? = null
    private var message: String? = null
    private var positive: String? = null
    private var negative: String? = null

    interface OnDialogListener {
        fun onDismissTwoButtonPositiveClick()
        fun onDismissTwoButtonNegativeClick()
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
        return inflater.inflate(R.layout.dialog_two_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        setupView()
    }

    private fun bindView(view: View) {
        tvMessage = view.findViewById(R.id.tvMessage)
        btnPositive = view.findViewById(R.id.btnSubmit)
        btnNegative = view.findViewById(R.id.btnNegative)
    }

    private fun setupView() {
        tvMessage!!.text = message
        btnPositive!!.text = positive
        btnNegative!!.text = negative
        btnPositive!!.setOnClickListener { v: View? ->
            val listener = onDialogListener
            listener?.onDismissTwoButtonPositiveClick()
            dismiss()
        }
        btnNegative!!.setOnClickListener { v: View? ->
            val listener = onDialogListener
            listener?.onDismissTwoButtonNegativeClick()
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
        outState.putString(KEY_POSITIVE, positive)
        outState.putString(KEY_NEGATIVE, negative)
    }

    private fun restoreInstanceState(bundle: Bundle) {
        message = bundle.getString(KEY_MESSAGE)
        positive = bundle.getString(KEY_POSITIVE)
        negative = bundle.getString(KEY_NEGATIVE)
    }

    private fun restoreArguments(bundle: Bundle?) {
        message = bundle!!.getString(KEY_MESSAGE)
        positive = bundle.getString(KEY_POSITIVE)
        negative = bundle.getString(KEY_NEGATIVE)
    }

    class Builder {
        private var message: String? = null
        private var positive: String? = null
        private var negative: String? = null
        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun setPositive(positive: String?): Builder {
            this.positive = positive
            return this
        }

        fun setNegative(negative: String?): Builder {
            this.negative = negative
            return this
        }

        fun build(): DismissTwoButtonDialogFragment {
            return newInstance(message, positive, negative)
        }
    }

    companion object {
        private const val KEY_MESSAGE = "key_message"
        private const val KEY_POSITIVE = "key_positive"
        private const val KEY_NEGATIVE = "key_negative"
        fun newInstance(message: String?, positive: String?, negative: String?): DismissTwoButtonDialogFragment {
            val fragment = DismissTwoButtonDialogFragment()
            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            bundle.putString(KEY_POSITIVE, positive)
            bundle.putString(KEY_NEGATIVE, negative)
            fragment.arguments = bundle
            return fragment
        }
    }
}