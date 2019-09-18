package com.xinh.share.extension

import android.text.Editable
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


fun AppCompatEditText.onTextChanged(): Observable<String> {
    val subject = PublishSubject.create<String>()
    addTextChangedListener(object : SimpleTextChangeListener {
        override fun afterTextChanged(text: Editable) {
            subject.onNext(text.toString())
        }
    })
    return subject
}

fun AppCompatEditText.onTextChanged(onTextChanged: (String) -> Unit = {}) {
    addTextChangedListener(object : SimpleTextChangeListener {
        override fun afterTextChanged(text: Editable) {
            onTextChanged(text.toString())
        }
    })
}

fun AppCompatEditText.getString(): String {
    return this.text.toString()
}

fun AppCompatEditText.onActionSearch(): Observable<String> {
    val subject = PublishSubject.create<String>()
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            hideSoftKeyboard(this)
            subject.onNext(getString())
        }
        return@setOnEditorActionListener true
    }
    return subject
}

fun AppCompatEditText.onActionDone(): Observable<String> {
    val subject = PublishSubject.create<String>()
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideSoftKeyboard(this)
            subject.onNext(getString())
        }
        return@setOnEditorActionListener true
    }
    return subject
}

fun AppCompatEditText.onFocusChangeListener(onFocusChangeListener: (View, Boolean) -> Unit) {
    setOnFocusChangeListener { v, hasFocus -> onFocusChangeListener.invoke(v, hasFocus) }
}