package com.risingpark.risingflipnumber

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout

class CommaView(context: Context?) : LinearLayout(context) {
    init {
        LayoutInflater.from(context).inflate(R.layout.comma_layout, this)
    }
}