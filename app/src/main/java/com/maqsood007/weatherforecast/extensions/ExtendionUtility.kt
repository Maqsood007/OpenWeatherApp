package com.maqsood007.weatherforecast.extensions

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import com.maqsood007.weatherforecast.R

/**
 * Created by Muhammad Maqsood on 08/04/2020.
 */


fun TextView.formatErrorLayout(msg: String?) {

    var errorMsg: String?

    if (msg == null)
        errorMsg = resources.getString(R.string.something_went_wrong)
    else errorMsg = msg

    val errorMessage =
        SpannableString(errorMsg)

    val clickHere =
        SpannableString(resources.getString(R.string.click_here))

    clickHere.setSpan(UnderlineSpan(), 0, clickHere.length, 0)

    val clickHereText: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
        }
    }

    clickHere.setSpan(clickHereText, 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    val ssb = SpannableStringBuilder()
    ssb.append(errorMessage)
    ssb.append(" ")
    ssb.append(clickHere)

    text = ssb

}