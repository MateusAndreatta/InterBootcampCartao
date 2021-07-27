package com.mateusandreatta.cartaofidelidade.util

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.mateusandreatta.cartaofidelidade.ui.AddActivity


class TextWatcherCardPreview constructor(view: View) : TextWatcher {
    private val view: View
    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        (view as TextView).text = charSequence.toString()
    }
    override fun afterTextChanged(editable: Editable) {}

    init {
        this.view = view
    }
}